package com.hector.usopreferencias

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class AppPreferenciasActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_preferencias)
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.fragment_container, AppPreferenciasFragment())
            .commit()
    }
}
