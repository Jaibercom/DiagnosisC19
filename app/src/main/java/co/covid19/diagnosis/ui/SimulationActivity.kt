package co.covid19.diagnosis.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.covid19.diagnosis.R
import co.covid19.diagnosis.viewmodel.SimulationViewModel
import co.covid19.diagnosis.viewmodel.MainViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.opensooq.supernova.gligar.GligarPicker
import kotlinx.android.synthetic.main.activity_simulation.*

/**
 * Activity for the Main Entry-Point.
 *
 * @author jaiber.yepes
 */
class SimulationActivity : AppCompatActivity() {

    private lateinit var viewModel: SimulationViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_simulation)

        val viewModelFactory = MainViewModelFactory(this.application)
        viewModel = ViewModelProvider(this, viewModelFactory).get(SimulationViewModel::class.java)

        observeLiveData()

        pickPhotoButton.setOnClickListener {
            runGligarPicker()
        }
        showAlertDialog()
    }


    private fun showAlertDialog() {
        MaterialAlertDialogBuilder(this, R.style.AlertDialogTheme)
            .setTitle(resources.getString(R.string.alert_date_title))
            .setMessage(resources.getString(R.string.alert_confirm_supporting_text))
            .setPositiveButton(resources.getString(R.string.ok)) { _, _ ->
                // Respond to positive button press
                runGligarPicker()
            }
            .show()
    }

    private fun runGligarPicker() {
        imageView.setImageResource(android.R.color.transparent)
        imageNameView.text = ""
        GligarPicker()
            .limit(1)
            .disableCamera(false)
            .cameraDirect(false)
            .folderPosition(viewModel.currentSelectedAlbum)
            .itemPosition(viewModel.currentSelectedItem)
            .requestCode(PICKER_REQUEST_CODE)
            .withActivity(this)
            .show()
    }

    private fun observeLiveData() {
        viewModel.bitmapLiveData.observe(this, {
            imageView.setImageBitmap(it)
        })

        viewModel.imageNameLiveData.observe(this, {
            imageNameView.text = it
        })

        viewModel.resultLiveData.observe(this, {
            resultView.text = it
        })

        viewModel.resultPercentLiveData.observe(this, {
            resultPercentView.text = it
        })

        viewModel.isProcessingLiveData.observe(this, {
            if (it == true) {
                pickPhotoButton.isEnabled = false
                progressBar.visibility = View.VISIBLE

            } else {
                pickPhotoButton.isEnabled = true
                progressBar.visibility = View.GONE
            }
        })
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode != Activity.RESULT_OK) {
            return
        }

        when (requestCode) {
            PICKER_REQUEST_CODE -> {
                val imagesList = data?.extras?.getStringArray(GligarPicker.IMAGES_RESULT)
                viewModel.currentSelectedAlbum =
                    data?.extras?.getInt(GligarPicker.CURRENT_ALBUM_POSITION_RESULT, 0) ?: 0

                viewModel.currentSelectedItem =
                    data?.extras?.getInt(GligarPicker.CURRENT_ITEM_POSITION_RESULT, 0) ?: 0

                if (!imagesList.isNullOrEmpty()) {
                    viewModel.setImagePath(imagesList[0])
                    viewModel.runModel()
                }
            }
        }
    }

    companion object {
        private const val PICKER_REQUEST_CODE = 30
    }
}
