package com.example.httpurlconnection

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection


class MainActivity : AppCompatActivity() {
    private val TAG = MainActivity::class.java.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        send_request.setOnClickListener {
            sendRequestWithHttpsURL()
        }
    }

    private fun sendRequestWithHttpsURL() {
        runBlocking {
            //Start a new coroutine (blocked)
            withContext(Dispatchers.Default) {


                var connection: HttpsURLConnection? = null
                try {
                    connection = (URL("https://www.google.com").openConnection() as HttpsURLConnection).apply {
                        requestMethod = "GET"//set request method
                        connectTimeout = 8000//Set the connection timeout
                        readTimeout = 8000//Set the read timeout
                    }

                    connection.inputStream.bufferedReader().useLines {
                        it.forEach {
                            val sb = StringBuilder().apply {
                                append(it)
                            }
                            Log.d(TAG, "onCreate2: $sb")
                            showResponse(sb.toString())
                        }
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                } finally {
                    connection?.disconnect()
                }

            }
        }
    }

    private fun showResponse(response: String) {
        runOnUiThread {
            //This is where you do the UI and display the results to the interface
            response_text.text = response
        }
    }


}