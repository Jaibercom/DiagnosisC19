package co.covid19.diagnosis.ui.home

import android.app.Application
import android.text.Html
import android.text.Spanned
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import co.covid19.diagnosis.R

class HomeViewModel (application: Application) : AndroidViewModel(application) {

    private val context = getApplication<Application>()

    private val _text = MutableLiveData<Spanned>().apply {
        value = Html.fromHtml(context.getString(R.string.home_message))
    }
    val text: LiveData<Spanned> = _text

//    private val _text = MutableLiveData<String>().apply {
//        value = "This is home Fragment"
//
        // get our html content
//        val htmlAsString = context.getString(co.covid19.diagnosis.R.string.home_message)
//        val htmlAsSpanned: Spanned = if (Build.VERSION.SDK_INT >= 24) {
//            Html.fromHtml(htmlAsString, FROM_HTML_MODE_LEGACY) // for 24 api and more
//        } else {
//            Html.fromHtml(htmlAsString) // or for older api
//        }

//        value = htmlAsSpanned.toString()
//
//    }
//    val text: LiveData<String> = _text
}