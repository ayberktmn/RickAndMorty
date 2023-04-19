package com.ayberk.rickandmorty20

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.ayberk.rickandmorty20.prefs.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }
}