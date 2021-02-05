package co.covid19.diagnosis.ui.showpdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.Build
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import co.covid19.diagnosis.R
import co.covid19.diagnosis.util.TypePDF
import com.google.android.material.floatingactionbutton.FloatingActionButton
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream

/**
 *
 * @author jaiber.yepes
 */
class ShowPDFFragment : Fragment() {

    private val args: ShowPDFFragmentArgs by navArgs()

    private lateinit var imageViewPdf: ImageView
    private lateinit var prePageButton: FloatingActionButton
    private lateinit var nextPageButton: FloatingActionButton

    private var pageIndex = 0
    private lateinit var pdfRenderer: PdfRenderer
    private var currentPage: PdfRenderer.Page? = null
    private var parcelFileDescriptor: ParcelFileDescriptor? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        dashboardViewModel = ViewModelProvider(this).get(ProtocolsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_showpdf, container, false)
        imageViewPdf = root.findViewById(R.id.pdf_image)
        prePageButton = root.findViewById(R.id.button_pre_doc)
        nextPageButton = root.findViewById(R.id.button_next_doc)

        val type: TypePDF = args.type
        Log.d(TAG, "Type PDF: $type")

        prePageButton.setOnClickListener { onPreviousDocClick() }
        nextPageButton.setOnClickListener { onNextDocClick() }

        return root
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onStart() {
        super.onStart()
        try {
            openRenderer(requireContext())
            showPage(pageIndex)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    override fun onStop() {
        try {
            closeRenderer()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        super.onStop()
    }

    private fun onPreviousDocClick() {
        showPage(currentPage?.index!! - 1)
    }

    private fun onNextDocClick() {
        showPage(currentPage?.index!! + 1)
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Throws(IOException::class)
    private fun openRenderer(context: Context) {
        // In this sample, we read a PDF from the assets directory.
        val fileName = getFileName()
        val file = File(context.filesDir, fileName)
        if (!file.exists()) {
            // Since PdfRenderer cannot handle the compressed asset file directly, we copy it into
            // the cache directory.
            val asset: InputStream = context.assets.open(fileName)
            val output = FileOutputStream(file)
            val buffer = ByteArray(1024)
            var size: Int
            while (asset.read(buffer).also { size = it } != -1) {
                output.write(buffer, 0, size)
            }
            asset.close()
            output.close()
        }
        parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
        // This is the PdfRenderer we use to render the PDF.
        parcelFileDescriptor?.let {
            pdfRenderer = PdfRenderer(it)
        }
    }

    private fun getFileName(): String {
        return when (args.type) {
            TypePDF.USER_MANUAL -> FILENAME_USER_MANUAL
            else -> FILENAME
        }
    }

    //    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Throws(IOException::class)
    private fun closeRenderer() {
        if (null != currentPage) {
            currentPage?.close()
        }
        pdfRenderer.close()
        parcelFileDescriptor?.close()
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun showPage(index: Int) {
        if (pdfRenderer.pageCount <= index) {
            return
        }
        // Make sure to close the current page before opening another one.
        if (null != currentPage) {
            currentPage?.close()
        }
        // Use `openPage` to open a specific page in PDF.
        currentPage = pdfRenderer.openPage(index)
        // Important: the destination bitmap must be ARGB (not RGB).
        val bitmap = Bitmap.createBitmap(
            currentPage?.width!!, currentPage?.height!!,
            Bitmap.Config.ARGB_8888
        )
        // Here, we render the page onto the Bitmap.
        // To render a portion of the page, use the second and third parameter. Pass nulls to get
        // the default result.
        // Pass either RENDER_MODE_FOR_DISPLAY or RENDER_MODE_FOR_PRINT for the last parameter.
        currentPage?.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
        // We are ready to show the Bitmap to user.
        imageViewPdf.setImageBitmap(bitmap)
        updateUi()
    }

    /**
     * Updates the state of 2 control buttons in response to the current page index.
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private fun updateUi() {
        val index = currentPage?.index!!
        val pageCount = pdfRenderer.pageCount
        prePageButton.isEnabled = 0 != index
        nextPageButton.isEnabled = index + 1 < pageCount
    }

    companion object {
        private const val FILENAME = "TeleorientacionRecomendaciones.pdf"
        private const val FILENAME_USER_MANUAL = "manual_usuario.pdf"
        private const val TAG = "ShowPDFFragment"
    }
}
