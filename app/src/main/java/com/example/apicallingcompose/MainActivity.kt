package com.example.apicallingcompose

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.apicallingcompose.ui.NotesScreen
import com.example.apicallingcompose.ui.theme.ApiCallingComposeTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ApiCallingComposeTheme {
                NotesScreen()
            }
        }
    }
}
