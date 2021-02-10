package co.covid19.diagnosis.ui

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import co.covid19.diagnosis.util.PreferenceHelper
import co.covid19.diagnosis.util.PreferenceHelper.privacy

/**
 * Activity for the Splash Entry-Point.
 *
 * @author jaiber.yepes
 */
class SplashActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val sharedPreferences = PreferenceHelper.customPreference(
            this,
            PreferenceHelper.PREFERENCE_FILE
        )

        val intent = if (sharedPreferences.privacy) {
            Intent(this, MainActivity::class.java)
        } else {
            Intent(this, PrivacyActivity::class.java)
        }
        startActivity(intent)
        finish()
    }
}
