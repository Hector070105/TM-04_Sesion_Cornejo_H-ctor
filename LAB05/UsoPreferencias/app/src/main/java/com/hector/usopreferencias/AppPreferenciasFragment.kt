package com.hector.usopreferencias

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat

class AppPreferenciasFragment : PreferenceFragmentCompat() {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.misapppreferencias, rootKey)
    }
}
