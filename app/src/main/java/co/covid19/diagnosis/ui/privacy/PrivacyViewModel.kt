package co.covid19.diagnosis.ui.privacy

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import co.covid19.diagnosis.util.PreferenceHelper
import co.covid19.diagnosis.util.PreferenceHelper.PREFERENCE_FILE
import co.covid19.diagnosis.util.PreferenceHelper.privacy

class PrivacyViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()
    private val sharedPreferences = PreferenceHelper.customPreference(context, PREFERENCE_FILE)

    private var privacySelected = false
    private var termsSelected = false

    private val isValid = MutableLiveData(false)

    fun getIsValid() = isValid

    fun setPrivacySelected(checked: Boolean) {
        privacySelected = checked
        isValid()
    }

    fun setTermsSelected(checked: Boolean) {
        termsSelected = checked
        isValid()
    }

    private fun isValid() {
        isValid.value  = privacySelected && termsSelected
    }

    fun savePreferences() {
        sharedPreferences.privacy = true
    }
}
