package co.covid19.diagnosis.ui.protocols

import android.graphics.text.LineBreaker.JUSTIFICATION_MODE_INTER_WORD
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import co.covid19.diagnosis.R
import co.covid19.diagnosis.util.TypePDF

/**
 *
 * @author jaiber.yepes
 */
class ProtocolsFragment : Fragment() {

    private lateinit var viewModel: ProtocolsViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewModel =
            ViewModelProvider(this).get(ProtocolsViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_protocols, container, false)
        val textView: TextView = root.findViewById(R.id.text_protocols)
        val userManual: TextView = root.findViewById(R.id.text_user_manual)

        viewModel.text.observe(viewLifecycleOwner, {
            textView.text = it
        })

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        userManual.setOnClickListener {
            goToShowPDF()
        }

        return root
    }

    private fun goToShowPDF() {
        val nav = findNavController()
        val action =
            ProtocolsFragmentDirections.actionNavigationProtocolsToNavigationShowPDFFragment(
                TypePDF.PROTOCOL
            )
        nav.navigate(action)
    }
}