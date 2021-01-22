package co.covid19.diagnosis.ui.protocols

import android.app.Application
import android.text.Html
import android.text.Spannable
import android.text.Spanned
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.covid19.diagnosis.R

/**
 *
 * @author jaiber.yepes
 */
class ProtocolsViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val _text = MutableLiveData<Spanned>().apply {
        value = Html.fromHtml(context.getString(R.string.protocols_message))
    }
    val text: LiveData<Spanned> = _text
}