package com.example.a7minuteworkout

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import com.example.a7minuteworkout.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private  var binding : ActivityMainBinding?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        binding!!.flStart.setOnClickListener {
            Toast.makeText(this, "Start tapped", Toast.LENGTH_LONG).show()
            val intent= Intent(this, ExerciseActivity::class.java)
            startActivity(intent)
        }
    }
}