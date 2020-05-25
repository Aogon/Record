package app.sakai.tororoimo.record

import android.Manifest
import android.content.pm.PackageManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File
import java.io.IOException
private const val REQUEST_RECORD_AUDIO_PERMISSION = 200


class MainActivity : AppCompatActivity() {
    private var mp: MediaPlayer? = null
    private var rec: MediaRecorder? = null

    /* 録音先のパス */
    private val filePath: String? =
        Environment.getExternalStorageDirectory().toString() + "/sample.wav"

    private var permissionToRecordAccepted = false
    private var permissions: Array<String> = arrayOf(Manifest.permission.RECORD_AUDIO)







    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION)


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




    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray

        )
    {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        permissionToRecordAccepted = if (requestCode == REQUEST_RECORD_AUDIO_PERMISSION) {
            grantResults[0] == PackageManager.PERMISSION_GRANTED
        } else {
            false
        }
        if (!permissionToRecordAccepted) finish()
    }



    private fun startRecord() {
        rec = MediaRecorder().apply {
            Log.d("recの中身", rec.toString())
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
