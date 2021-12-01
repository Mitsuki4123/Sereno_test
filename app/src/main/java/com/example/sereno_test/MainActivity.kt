package com.example.sereno_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.widget.TextView
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.lang.Exception
import java.lang.IllegalStateException
import kotlin.concurrent.thread

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val textView: TextView = findViewById(R.id.text)

        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://sheetdb.io/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()

        thread {
            try {
                val service: UserService = retrofit.create(UserService::class.java)
                val sheetApiResponse = service.getUserInfo().execute().body()
                    ?: throw IllegalStateException("bodyがnullだよ!")

                Handler(Looper.getMainLooper()).post {
                    textView.text = sheetApiResponse[0].name.toString()
                }
            } catch (e: Exception) {
                Log.d("Error:", "${e}")
            }
        }
    }
}