package com.therockakash.shaketrace.shake

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.therockakash.shaketrace.databinding.ActivityShakeBinding

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException
import java.io.PrintWriter

/**
 * @Created by akash on 7/20/2023.
 * Know more about author on https://akash.cloudemy.in
 */

class ShakeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityShakeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityShakeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        CoroutineScope(Dispatchers.IO).launch {
            setLogs()
        }.invokeOnCompletion {
            binding.scrollView.post {
                binding.scrollView.fullScroll(View.FOCUS_DOWN);
            }
        }
        setClickListeners()


    }

    private fun setLogs() {
        try {
            val temp = File(cacheDir, "log.file")
            val path: String = temp.path
            val fileReader = FileReader(path)
            val bufferedReader = BufferedReader(fileReader)
            var buffer: String?
            val stringBuilder = StringBuilder()

            while (bufferedReader.readLine().also { buffer = it } != null) {
                stringBuilder.append(buffer)
                stringBuilder.append("\n")
            }
            binding.text.text = stringBuilder.toString()

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun clearLogs() {
        val logFile = File(cacheDir, "log.file")
        if (!logFile.exists()) {
            return
        }
        val writer = PrintWriter(logFile)

        try {
            writer.print("")
            writer.close()
        } catch (e: IOException) {
            writer.close()
            e.printStackTrace()
        }
    }

    private fun setClickListeners() {
        binding.apply {
            ivBack.setOnClickListener {
                finish()
            }
            tvClear.setOnClickListener {
                clearLogs()
                setLogs()
            }
        }
    }
}