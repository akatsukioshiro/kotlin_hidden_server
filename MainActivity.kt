package com.example.ktserve


import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.buttonStart
import kotlinx.android.synthetic.main.activity_main.buttonStop
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val portz = 8124
        setContentView(R.layout.activity_main)
        buttonStart.setOnClickListener(View.OnClickListener {
            MyService.startService(this, "Foreground Service is running...")
        })
        buttonStop.setOnClickListener(View.OnClickListener {
            MyService.stopService(this, portz)
        })
    }
}