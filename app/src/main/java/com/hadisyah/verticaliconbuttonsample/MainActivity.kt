package com.hadisyah.verticaliconbuttonsample

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.hadisyah.verticaliconbutton.VerticalIconBoxedButton
import com.hadisyah.verticaliconbutton.VerticalIconButton

class MainActivity : AppCompatActivity() {

    private lateinit var button : VerticalIconButton
    private lateinit var button2 : VerticalIconBoxedButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        button = findViewById(R.id.button_test)
        button2 = findViewById(R.id.button2)

        button.setOnClickListener{
            Toast.makeText(this, "dsashdiasbdoashb", Toast.LENGTH_SHORT).show()
        }

        button2.setOnClickListener{
            Toast.makeText(this, "a", Toast.LENGTH_SHORT).show()
        }

    }
}