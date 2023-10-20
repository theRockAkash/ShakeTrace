package com.creatorstool.shaketrace

import android.app.Application
import android.content.Context.SENSOR_SERVICE
import android.content.Intent
import android.content.Intent.FLAG_ACTIVITY_NEW_TASK
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.Log
import com.creatorstool.shaketrace.shake.ActivityLiveCycleListener
import com.creatorstool.shaketrace.shake.AppStateListener
import com.creatorstool.shaketrace.shake.ShakeActivity
import java.io.File
import java.io.IOException
import java.io.PrintWriter
import kotlin.math.sqrt

/**
 * @Created by akash on 7/20/2023.
 * Know more about author on https://akash.cloudemy.in
 */

class ShakeTrace  {

 companion object{
     private lateinit var context :Application
     private var sensorManager: SensorManager? = null
     private var acceleration = 0f
     private var currentAcceleration = 0f
     private var lastAcceleration = 0f

     fun init(context: Application) {
         this.context=context


         clearLogs()
         sensorManager = context.getSystemService(SENSOR_SERVICE) as SensorManager
         context.registerActivityLifecycleCallbacks(ActivityLiveCycleListener(object :
             AppStateListener {
             override fun onAppForeGround() {
                 //start network listener
                 sensorManager?.registerListener(
                     sensorListener, sensorManager!!.getDefaultSensor(
                         Sensor.TYPE_ACCELEROMETER
                     ), SensorManager.SENSOR_DELAY_NORMAL
                 )
             }

             override fun onAppBackground() {
                 //remove network listener
                 sensorManager!!.unregisterListener(sensorListener)
             }
         }))

         acceleration = 10f
         currentAcceleration = SensorManager.GRAVITY_EARTH
         lastAcceleration = SensorManager.GRAVITY_EARTH

     }

     fun clearLogs() {
         val logFile = File(context.cacheDir, "log.file")
         if (!logFile.exists()) {
             try {
                 logFile.createNewFile()
             } catch (e: IOException) {
                 e.printStackTrace()
             }
         }

         val writer = PrintWriter(logFile)
         Log.d("ShakeTrace", "${context.cacheDir} ------------------------Logs cleared------------------------")
         try {
             writer.print("")
             writer.close()
         } catch (e: IOException) {
             writer.close()
             e.printStackTrace()
         }
     }


     private val sensorListener: SensorEventListener by lazy {
         object : SensorEventListener {
             override fun onSensorChanged(event: SensorEvent) {

                 // Fetching x,y,z values
                 val x = event.values[0]
                 val y = event.values[1]
                 val z = event.values[2]
                 lastAcceleration = currentAcceleration

                 // Getting current accelerations
                 // with the help of fetched x,y,z values
                 currentAcceleration = sqrt((x * x + y * y + z * z).toDouble()).toFloat()
                 val delta: Float = currentAcceleration - lastAcceleration
                 acceleration = acceleration * 0.9f + delta

                 // Display a Toast message if
                 // acceleration value is over 12
                 if (acceleration > 12) {
                     context.startActivity(
                         Intent(context, ShakeActivity::class.java).addFlags(
                             FLAG_ACTIVITY_NEW_TASK
                         )
                     )
                 }
             }

             override fun onAccuracyChanged(sensor: Sensor, accuracy: Int) {}
         }
     }

 }

}