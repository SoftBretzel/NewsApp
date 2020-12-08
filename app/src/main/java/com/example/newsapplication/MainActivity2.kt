package com.example.newsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import org.json.JSONArray
import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.IOException
import java.net.URL
import javax.net.ssl.HttpsURLConnection


private const val TAG = "MainActivity2"

class MainActivity2 : AppCompatActivity() {

    val url: String = "https://jsonplaceholder.typicode.com/posts"
    //val sources: String = "https://newsapi.org/v2/sources?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr"
    //val flux: String = "https://newsapi.org/v2/everything?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr&sources="+source

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)


    }

    fun getPostsWithVolley() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        // Request a string response from the provided URL.
        val stringReq = StringRequest(
            Request.Method.GET, url,
            { response ->

                var strResp = response.toString()
                val jsonArray: JSONArray = JSONArray(strResp)
                var strPosts: String = ""
                for (i in 0 until jsonArray.length()) {
                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                    strPosts = strPosts + "\n" + jsonInner.get("title")
                }
                Toast.makeText(this, "3-Fetched Data: "+strResp, Toast.LENGTH_SHORT).show()
                Log.d("3-Fetched Data", strResp)
            },
            {
                Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
                Log.d("Fetched Data","That didn't work!")
            }
        )
        queue.add(stringReq)
    }


}