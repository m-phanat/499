package com.example.m.mproject499.activity

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.Settings
import android.speech.RecognitionListener
import android.speech.RecognizerIntent
import android.speech.SpeechRecognizer
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.MotionEvent
import android.widget.EditText
import android.widget.Toast
import com.example.m.mproject499.MainApp
import com.example.m.mproject499.R
import com.example.m.mproject499.fragment.SpeakingFragment
import kotlinx.android.synthetic.main.activity_speaking.*
import kotlinx.android.synthetic.main.app_bar_main.*
import java.util.*


class SpeakingActivity : AppCompatActivity() {

    companion object {
        fun getStartIntent(context: Context) = Intent(context, SpeakingActivity::class.java)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_speaking)
        MainApp.graph.inject(this)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Speaking"

        checkPermission()
        //startSpeechToText()
        createFragment(SpeakingFragment.fragment(this), "XFDF")
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun createFragment(fragment: Fragment, name: String) {
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.speaking_frag, fragment)
        fragmentTransaction.commit()
        title = name
    }

    private fun checkPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS, Uri.parse("package:$packageName"))
                startActivity(intent)
                finish()
                Toast.makeText(this, "Enable Microphone Permission..!!", Toast.LENGTH_SHORT).show()
            }
        }
    }

}