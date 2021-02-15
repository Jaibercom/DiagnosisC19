package co.covid19.diagnosis.ui.contact

import android.content.Intent
import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import co.covid19.diagnosis.R
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 *
 * @author jaiber.yepes
 */
class ContactFragment : Fragment() {

    private lateinit var notificationsViewModel: ContactViewModel
    private lateinit var sendEmailButton: FloatingActionButton
    private lateinit var nameText: EditText
    private lateinit var messageText: EditText
    private lateinit var phoneNumberText: EditText

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        notificationsViewModel =
            ViewModelProvider(this).get(ContactViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_contact, container, false)
        val textView: TextView = root.findViewById(R.id.text_contact)
        sendEmailButton = root.findViewById(R.id.sendEmailButton)
        nameText = root.findViewById(R.id.name)
        messageText = root.findViewById(R.id.message)
        phoneNumberText = root.findViewById(R.id.phoneNumber)

        notificationsViewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        sendEmailButton.setOnClickListener {
            sendEmail()
        }

        return root
    }

    private fun sendEmail() {
        val message = "${messageText.text}\n\n" +
                "Atentamente:" +
                "\n${nameText.text}" +
                "\n${phoneNumberText.text}"

        val emailIntent = Intent(Intent.ACTION_SENDTO).apply {
            data = Uri.parse("mailto:") // only email apps should handle this
            putExtra(Intent.EXTRA_EMAIL, arrayOf("vivamed@eafit.edu.co"))
            putExtra(Intent.EXTRA_SUBJECT, "vIvA-Med light")
            putExtra(Intent.EXTRA_TEXT, message)

        }

        if (emailIntent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(emailIntent)
        }
    }
}
