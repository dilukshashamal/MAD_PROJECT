package com.example.tochsm

import android.annotation.SuppressLint
import android.app.KeyguardManager
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.SharedPreferences
import android.content.pm.PackageManager
import android.hardware.biometrics.BiometricPrompt
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CancellationSignal
import android.widget.ImageView
import android.widget.Switch
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_settings.*

class SettingsActivity : AppCompatActivity() {

    var enabledBiometrics : String = "enabled"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_settings)

        loadSwitch()

        if (!biometricsSwitch.isChecked){
            enabledBiometrics = "disabled"
        }

        biometricsSwitch.setOnCheckedChangeListener { _, _ ->
            if (biometricsSwitch.isChecked){
                enabledBiometrics = "enabled"
            }
            else{
                enabledBiometrics = "disabled"
            }
            saveSwitch()
        }

        back_profile_btn.setOnClickListener {
            val intent = Intent(this, AccountSettingsActivity::class.java)
            startActivity(intent)
        }
    }

    private fun saveSwitch() {
        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.apply {
            putBoolean("BOOLEAN_KEY", biometricsSwitch.isChecked)
        }.apply()
    }

    private fun loadSwitch() {
        val sharedPreferences = getSharedPreferences("sharedPref", Context.MODE_PRIVATE)
        val savedBoolean = sharedPreferences.getBoolean("BOOLEAN_KEY", false)

        biometricsSwitch.isChecked = savedBoolean
    }
}