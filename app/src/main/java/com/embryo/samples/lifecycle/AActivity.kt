package com.embryo.samples.lifecycle

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestPermission
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog

private const val TAG = "lifecycle"

class AActivity : ComponentActivity() {

    private val showDialog = mutableStateOf(false)

    private val cameraPermissionLauncher =
        registerForActivityResult(RequestPermission()) { isGranted ->
            Log.d(TAG, "Camera Permission granted $isGranted ")
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "A - onCreate")
        setContent {
            Scaffold(
                topBar = {
                    TopAppBar(
                        title = { Text("Activity A") }
                    )
                }
            ) { innerPadding ->
                Column(
                    modifier = Modifier
                        .padding(innerPadding)
                        .padding(16.dp)
                ) {
                    Button(
                        onClick = {
                            startActivity(Intent(this@AActivity, BActivity::class.java))
                        }
                    ) {
                        Text(text = "Start Activity B")
                    }
                    Button(
                        onClick = {
                            //showDialog.value = !showDialog.value
                            AlertDialog.Builder(this@AActivity)
                                .setTitle("Alert Dialog")
                                .setPositiveButton("Close") { dialog, _ -> dialog.dismiss() }
                                .show()
                        }
                    ) {
                        Text(text = "Start Dialog")
                    }
                    Button(
                        onClick = {
                            cameraPermissionLauncher.launch(android.Manifest.permission.CAMERA)
                        }
                    ) {
                        Text(text = "Ask Camera Permission")
                    }
                    if (showDialog.value) {
                        Dialog(
                            onDismissRequest = { showDialog.value = false }
                        ) {
                            Box(
                                Modifier
                                    .fillMaxWidth()
                                    .height(100.dp)
                            )
                        }
                    }
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "A - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "A - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "A - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "A - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "A - onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "A - onRestart")
    }
}

class BActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "B - onCreate")
        setContent {
            Scaffold(topBar = {
                TopAppBar(title = { Text("Activity B") })
            }) { innerPadding ->
                Box(
                    modifier = Modifier.padding(innerPadding)
                )
            }
        }
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "B - onStart")
    }

    override fun onResume() {
        super.onResume()
        Log.d(TAG, "B - onResume")
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "B - onPause")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "B - onStop")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "B - onDestroy")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "B - onRestart")
    }
}