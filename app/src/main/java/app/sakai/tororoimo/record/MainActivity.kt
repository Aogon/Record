package app.sakai.tororoimo.record

import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException


class MainActivity : AppCompatActivity() {
    private var mp: MediaPlayer? = null
    private var rec: MediaRecorder? = null

    /* 録音先のパス */
    val filePath =
        Environment.getExternalStorageDirectory().toString() + "/sample2.wav"


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

    




    fun startRecord() {
        var wavFile: File? = File(filePath)
        if (wavFile!!.exists()) {
            wavFile.delete()
        }
        wavFile = null
        try {

            rec = MediaRecorder()
            rec!!.setAudioSource(MediaRecorder.AudioSource.MIC)
            rec!!.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT)
            rec!!.setAudioEncoder(MediaRecorder.AudioEncoder.DEFAULT)
            rec!!.setOutputFile(filePath)
            rec!!.prepare()
            rec!!.start()


        } catch (e: Exception) {
            e.printStackTrace()
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
        ;
        try {
            mp = MediaPlayer()
            mp!!.setDataSource(filePath)
            mp!!.prepare()
            mp!!.start()
        } catch (e: IOException) {
            e.printStackTrace()
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
