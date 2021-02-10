package co.covid19.diagnosis.ui.privacy

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.*
import android.text.method.LinkMovementMethod
import android.text.style.ClickableSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.covid19.diagnosis.R
import co.covid19.diagnosis.ui.MainActivity
import co.covid19.diagnosis.util.TypePDF
import com.google.android.material.button.MaterialButton
import com.google.android.material.checkbox.MaterialCheckBox

class PrivacyFragment : Fragment() {

    private lateinit var viewModel: PrivacyViewModel
    private lateinit var checkboxPrivacy: MaterialCheckBox
    private lateinit var checkboxTerms: MaterialCheckBox
    private lateinit var button: MaterialButton

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.privacy_fragment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val textView: TextView = view.findViewById(R.id.text_privacy)
        textView.text = Html.fromHtml(getString(R.string.privacy_message))

        checkboxPrivacy = view.findViewById(R.id.checkbox_privacy)
        checkboxTerms = view.findViewById(R.id.checkbox_terms)
        button = view.findViewById(R.id.continue_button)

        initViews()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(PrivacyViewModel::class.java)

        observeLiveData()
    }

    private fun observeLiveData() {
        viewModel.getIsValid().observe(viewLifecycleOwner, {
            it ?: return@observe
            button.isEnabled = it
        })
    }

    private fun initViews() {
        checkboxPrivacy.apply {
            addClickableLink(
                fullText = getString(R.string.privacy_policy_message),
                linkText = SpannableString(getString(R.string.privacy_policy_underline_message))
            ) {
                goToPrivacy()
            }

            setOnCheckedChangeListener { _, isChecked ->
                viewModel.setPrivacySelected(isChecked)
            }
        }

        checkboxTerms.apply {
            addClickableLink(
                fullText = getString(R.string.terms_and_conditions_message),
                linkText = SpannableString(getString(R.string.terms_and_conditions_underline_message))
            ) {
                goToTerms()
            }

            setOnCheckedChangeListener { _, isChecked ->
                viewModel.setTermsSelected(isChecked)
            }
        }

        button.setOnClickListener {
            viewModel.savePreferences()
            goToMain()
        }
    }

    private fun goToMain() {
        val intent = Intent(requireActivity(), MainActivity::class.java)
        startActivity(intent)
        requireActivity().finish()
    }

    private fun goToPrivacy() {
        val action =
            PrivacyFragmentDirections.actionNavigationPrivacyToNavigationShowPDFFragment(TypePDF.PRIVACY)
        findNavController().navigate(action)
    }

    private fun goToTerms() {
        val action =
            PrivacyFragmentDirections.actionNavigationPrivacyToNavigationShowPDFFragment(TypePDF.TERMS)
        findNavController().navigate(action)
    }

}

fun MaterialCheckBox.addClickableLink(
    fullText: String,
    linkText: SpannableString,
    callback: () -> Unit
) {
    val clickableSpan = object : ClickableSpan() {
        override fun onClick(widget: View) {
            widget.cancelPendingInputEvents() // Prevent CheckBox state from being toggled when link is clicked
            callback.invoke()
        }

        override fun updateDrawState(ds: TextPaint) {
            super.updateDrawState(ds)
            ds.isUnderlineText = true // Show links with underlines
            ds.color = Color.WHITE
        }
    }
    linkText.setSpan(clickableSpan, 0, linkText.length, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
    val fullTextWithTemplate = fullText.replace(linkText.toString(), "^1", false)

    text = TextUtils.expandTemplate(fullTextWithTemplate, linkText)
    movementMethod = LinkMovementMethod.getInstance() // Make link clickable
}