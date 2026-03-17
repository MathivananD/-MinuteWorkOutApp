package com.example.a7minuteworkout

import android.annotation.SuppressLint
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.text.Layout
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.addCallback
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.a7minuteworkout.databinding.ActivityExcerciseBinding
import java.lang.Exception
import java.net.URI
import java.util.Locale
import androidx.core.net.toUri
import androidx.recyclerview.widget.LinearLayoutManager

class ExerciseActivity : AppCompatActivity(), TextToSpeech.OnInitListener {
    var binding: ActivityExcerciseBinding? = null;
    private var resetTimer: CountDownTimer? = null;
    private var restProgress = 0;

    private var restTimerProgress: Long = 10;
    private var timeInterval: Long = 1;
    private var resetExerciseTimer: CountDownTimer? = null;
    private var restExerciseProgress = 0;
    private var restTimerExerciseProgress: Long = 30;
    private var exerciseList: ArrayList<ExerciseModel> = ArrayList<ExerciseModel>();
    private var currentExercisePosition: Int = -1
    private var tts: TextToSpeech? = null
    private var player: MediaPlayer? = null

    private var exerciseAdapter: MyExerciseStatusAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityExcerciseBinding.inflate(layoutInflater)
        tts = TextToSpeech(this, this)

        enableEdgeToEdge()
        setContentView(binding?.root)
        setSupportActionBar(binding?.toolBarExercise)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        exerciseList = Constants.defaultExerciseList()

        setUpExercieStatusRecyclerView()
        binding?.toolBarExercise?.setNavigationOnClickListener {

            finish()
        }
        setUpResetView()
    }

    private fun setUpExercieStatusRecyclerView() {
        exerciseAdapter = MyExerciseStatusAdapter(exerciseList)
        binding?.rvExerciseStatus?.layoutManager =
            LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        binding?.rvExerciseStatus?.adapter = exerciseAdapter
    }

    private fun setUpResetView() {

//        try {
//            val soundURI =
//                ("android.resource://com.example.a7minuteworkout/" + R.raw.press).toUri()
//            player = MediaPlayer.create(applicationContext, soundURI)
//            player!!.isLooping = false
//            player?.start()
//        } catch (e: Exception) {
//
//        }
        restProgress = 0
        binding?.flRestView?.visibility = View.VISIBLE
        binding?.tvTitle?.text = "Exercise TImer"
        binding?.tvExerciseName?.visibility = View.INVISIBLE
        binding?.flExerciseView?.visibility = View.INVISIBLE
        binding?.ivImage?.visibility = View.INVISIBLE
        binding?.tvUpcomingLabel?.visibility = View.VISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.VISIBLE


        if (resetTimer != null) {
            resetTimer!!.cancel()
            resetTimer = null
        }
        binding?.tvUpcomingExerciseName?.text = exerciseList[currentExercisePosition + 1].getName()
        setRestProgressBar()
    }

    private fun setUpExerciseTimer() {
        restExerciseProgress = 0
        binding?.flRestView?.visibility = View.INVISIBLE
        binding?.tvTitle?.text = "Exercise TImer"
        binding?.tvExerciseName?.visibility = View.VISIBLE
        binding?.flExerciseView?.visibility = View.VISIBLE
        binding?.ivImage?.visibility = View.VISIBLE
        binding?.tvUpcomingLabel?.visibility = View.INVISIBLE
        binding?.tvUpcomingExerciseName?.visibility = View.INVISIBLE
        if (resetExerciseTimer != null) {
            resetExerciseTimer!!.cancel()
            restExerciseProgress = 0
        }

        val exerciseModel = exerciseList[currentExercisePosition];
        speakOut(exerciseModel.getName())
        binding?.ivImage?.setImageResource(exerciseModel.getImage())
        binding?.tvExerciseName?.text = exerciseModel.getName()
        setRestExerciseProgressBar()
    }

    private fun setRestProgressBar() {
        binding?.progressBar?.progress = restProgress
        resetTimer = object : CountDownTimer(
            secondsToMilliSeconds(restTimerProgress),
            secondsToMilliSeconds(timeInterval)
        ) {
            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
//                Toast.makeText(this@ExerciseActivity, "Done", Toast.LENGTH_SHORT).show()
                currentExercisePosition++
                exerciseList[currentExercisePosition].setIsSelected(true)
                exerciseAdapter!!.notifyDataSetChanged()

                setUpExerciseTimer()

            }

            override fun onTick(millisUntilFinished: Long) {
                Log.d("Tick Timer", "$millisUntilFinished")
                restProgress++
                binding?.progressBar?.progress = 10 - restProgress
                binding?.tvTimer?.text = (10 - restProgress).toString()

            }


        }.start()
    }

    private fun setRestExerciseProgressBar() {
        binding?.progressExerciseBar?.progress = restExerciseProgress
        resetExerciseTimer = object : CountDownTimer(
            secondsToMilliSeconds(restTimerExerciseProgress),
            secondsToMilliSeconds(timeInterval)
        ) {
            @SuppressLint("NotifyDataSetChanged")
            override fun onFinish() {
                if (currentExercisePosition < exerciseList.size - 1) {
                    exerciseList[currentExercisePosition].setIsSelected(false)
                    exerciseList[currentExercisePosition].setIsCompleted(true)
                    exerciseAdapter!!.notifyDataSetChanged()
                    setUpResetView()

                } else {
                    Toast.makeText(this@ExerciseActivity, "Congratulationss", Toast.LENGTH_LONG)
                        .show()
                }
            }

            override fun onTick(millisUntilFinished: Long) {
                Log.d("Tick Timer", "$millisUntilFinished")
                restExerciseProgress++
                binding?.progressExerciseBar?.progress = 30 - restExerciseProgress
                binding?.tvExerciseTimer?.text = (30 - restExerciseProgress).toString()

            }


        }.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        if (resetTimer != null) {
            resetTimer!!.cancel()
        }
        if (resetExerciseTimer != null) {
            resetExerciseTimer!!.cancel()
        }
        binding = null
        if (tts != null) {
            tts!!.stop()
            tts!!.shutdown()

        }
    }

    override fun onInit(status: Int) {
        if (status == TextToSpeech.SUCCESS) {
            val result = tts?.setLanguage(Locale.US)
            if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.e("TTS", "The Language specified is not supported!")

            }
        } else {
            Log.e("TTS", "Initialization Failed")
        }
    }

    private fun speakOut(text: String) {
        tts!!.speak(text, TextToSpeech.QUEUE_ADD, null, "")
    }

    private fun secondsToMilliSeconds(seconds: Long): Long {
        return seconds * 1000
    }

}