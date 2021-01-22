package co.covid19.diagnosis.ui.contact

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import co.covid19.diagnosis.R

class ContactFragment : Fragment() {

    private lateinit var notificationsViewModel: ContactViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(ContactViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contact, container, false)
        val textView: TextView = root.findViewById(R.id.text_contact)

        notificationsViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })

        textView.text = Html.fromHtml(getString(R.string.contact_message))
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }
        return root
    }
}