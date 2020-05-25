package app.sakai.tororoimo.record

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private var mp: MediaPlayer? = null
    private var rec: MediaRecorder? = null

    /* 録音先のパス */
    private val filePath: String? =
        Environment.getExternalStorageDirectory().toString() + "/sample.wav"


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        recordButton.setOnClickListener {
            startRecord()

        }

        stopButton.setOnClickListener {
            stopRecord()

        }


        playButton.setOnClickListener {
            startPlay()

        }

        playStopButton.setOnClickListener {
            stopPlay()

        }
    }

    




//    fun startRecord() {
//
//        var wavFile: File? = File(filePath)
//        Log.d("filepath", filePath)
//        if (wavFile!!.exists()) {
//            wavFile.delete()
//            Log.d("hoge", "ifの実行")
//        }
//        wavFile = null
//        try {
//
//            rec = MediaRecorder()
//            rec!!.setAudioSource(MediaRecorder.AudioSource.MIC)
//            rec!!.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
//            rec!!.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
//            rec!!.setOutputFile(filePath)
//            rec!!.prepare()
//            rec!!.start()
//            Log.d("hoge", "tryの実行")
//
//
//        } catch (e: Exception) {
//            e.printStackTrace()
//            Log.d("hoge", "catchの実行")
//        }
//    }

    private fun startRecord() {
        rec = MediaRecorder().apply {
            setAudioSource(MediaRecorder.AudioSource.MIC)
            setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
            setOutputFile(filePath)
            setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
            try {
                prepare()
            } catch (e: IOException) {
                Log.e("Log_tag", "prepare() failed")
            }
            start()
        }
    }


    private fun stopRecord() {
        try {
            rec!!.stop()
            rec!!.reset()
            rec!!.release()
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
    }

    private fun startPlay() {

        try {
            mp = MediaPlayer()
            mp!!.setDataSource(filePath)
            mp!!.prepare()
            mp!!.start()
            Log.d("hoge", "1")
        } catch (e: IOException) {
            e.printStackTrace()
            Log.d("hoge", "2")
        }
    }

    private fun stopPlay() {
        try {
            mp!!.stop()
            mp!!.prepare()
            mp!!.release()
        } catch (e: IllegalStateException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}
