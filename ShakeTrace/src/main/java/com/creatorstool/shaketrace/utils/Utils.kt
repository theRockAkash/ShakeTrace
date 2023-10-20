package com.creatorstool.shaketrace.utils

import android.content.Context
import android.widget.Toast
import okhttp3.ResponseBody
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

/**
 * @Created by akash on 7/20/2023.
 * Know more about author on https://akash.cloudemy.in
 */
class Utils {
    companion object {
        private var toast: Toast? = null
        fun toasty(context: Context, msg: String?) {
            if (!msg.isNullOrBlank()) {
                if (toast != null) {
                    toast!!.cancel()
                    toast = null
                }
                toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
                toast!!.show()
            }
        }


        fun getFormattedDateTime(utcString: String,outputFormat:String): String {
            return try {

                val sdf = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.ENGLISH)
                sdf.timeZone = TimeZone.getTimeZone("UTC")
                val date: Date? = sdf.parse(utcString)
                sdf.applyPattern(outputFormat)
                sdf.timeZone = TimeZone.getDefault()
                date?.let { sdf.format(it) } ?: utcString
            } catch (e: Exception) {
                e.printStackTrace()
                utcString
            }.uppercase()
        }


        fun getFormattedDateTime(date: String,inputFormat:String,outputFormat:String): String {
            return try {

                val sdf = SimpleDateFormat(inputFormat, Locale.ENGLISH)
                val date2: Date? = sdf.parse(date)
                sdf.applyPattern(outputFormat)
                date2?.let { sdf.format(it) } ?: date
            } catch (e: Exception) {
                e.printStackTrace()
                date
            }
        }

        fun getError(key:String,errorBody: ResponseBody?): String {
            return try {
                JSONObject(errorBody?.charStream()?.readText() ?: "")
                    .getString(key) ?: "Failed to connect"
            } catch (e: Exception) {
                "Error Occurred"
            }
        }
    }

}