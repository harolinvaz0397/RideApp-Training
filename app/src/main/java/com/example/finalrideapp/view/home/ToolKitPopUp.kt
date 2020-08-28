package com.example.finalrideapp.view.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import com.example.finalrideapp.R

class ToolKitPopUp : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tool_kit_pop_up)

        val imgButton: ImageButton = findViewById<ImageButton>(R.id.imgBtn)
        imgButton.setOnClickListener {
            this.finish()
        }

    }
}
