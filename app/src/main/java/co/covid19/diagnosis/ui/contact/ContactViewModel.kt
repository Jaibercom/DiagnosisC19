package co.covid19.diagnosis.ui.contact

import android.app.Application
import android.text.Html
import android.text.Spanned
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.covid19.diagnosis.R

class ContactViewModel(application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val _text = MutableLiveData<Spanned>().apply {
        value = Html.fromHtml(context.getString(R.string.contact_message))
    }
    val text: LiveData<Spanned> = _text
}