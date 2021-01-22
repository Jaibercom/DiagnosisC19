package co.covid19.diagnosis.ui.showpdf

import android.content.Intent
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import co.covid19.diagnosis.R
import java.io.File

/**
 *
 * @author jaiber.yepes
 */
class ShowPDFFragment : Fragment() {

//    private lateinit var dashboardViewModel: ProtocolsViewModel
    val args: ShowPDFFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
//        dashboardViewModel = ViewModelProvider(this).get(ProtocolsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_showpdf, container, false)
        val textView: TextView = root.findViewById(R.id.text_show_pdf)
//        dashboardViewModel.text.observe(viewLifecycleOwner, Observer {
////            textView.text = it
//        })

        textView.text = Html.fromHtml(getString(R.string.show_pdf_message))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        val aa = args.type


        return root
    }

    private fun composePDF(filename: String) {

        val file = File(
            Environment.getExternalStorageDirectory().absolutePath.toString() + "/" + filename
        ) // Here you declare your pdf path

        val pdfViewIntent = Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(Uri.fromFile(file), "application/pdf")
            flags = Intent.FLAG_ACTIVITY_NO_HISTORY
        }

//        val intent = Intent(Intent.ACTION_SENDTO).apply {
//            data = Uri.parse("mailto:") // only email apps should handle this
//            putExtra(Intent.EXTRA_EMAIL, arrayOf("vivamed@eafit.edu.co"))
//            putExtra(Intent.EXTRA_SUBJECT, "vIvA-Med light")
//            putExtra(Intent.EXTRA_TEXT, messageText.text)
//        }

        if (pdfViewIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(pdfViewIntent )
        }
    }
}