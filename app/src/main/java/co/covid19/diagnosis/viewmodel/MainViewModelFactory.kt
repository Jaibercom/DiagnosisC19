package co.covid19.diagnosis.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * ViewModel provider factory to instantiate [SimulationViewModel].
 * Required given MainViewModel has a non-empty constructor.
 *
 * @author jaiber.yepes
 */
class MainViewModelFactory(private val application: Application) : ViewModelProvider.Factory {

    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SimulationViewModel::class.java)) {
            return SimulationViewModel(application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
