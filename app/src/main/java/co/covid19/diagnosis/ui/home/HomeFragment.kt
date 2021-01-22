package co.covid19.diagnosis.ui.home

import android.content.Intent
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
import co.covid19.diagnosis.ui.SimulationActivity
import com.google.android.material.floatingactionbutton.FloatingActionButton


class HomeFragment : Fragment() {

    private lateinit var homeViewModel: HomeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_home, container, false)
        val textView: TextView = root.findViewById(R.id.text_contact)
        val pickPhotoButton: FloatingActionButton = root.findViewById(R.id.pickPhotoButton)

        homeViewModel.text.observe(viewLifecycleOwner, Observer {
//            textView.text = it
        })

        textView.text = Html.fromHtml(getString(R.string.home_message))

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            textView.justificationMode = JUSTIFICATION_MODE_INTER_WORD
        }

        pickPhotoButton.setOnClickListener {
            goToSimulation()
        }

        return root
    }

    private fun goToSimulation() {
        val intent = Intent(context, SimulationActivity::class.java)
        startActivity(intent)
    }
}