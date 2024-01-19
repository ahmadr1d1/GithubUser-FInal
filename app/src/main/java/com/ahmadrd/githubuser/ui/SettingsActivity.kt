package com.ahmadrd.githubuser.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ahmadrd.githubuser.R
import com.ahmadrd.githubuser.databinding.ActivitySettingsBinding

class SettingsActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButtonSettings.setOnClickListener {
            val moveIntent = Intent(this, MainActivity::class.java)
            startActivity(moveIntent)
            finish()
        }
    }
}