/*
package com.example.newsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import android.widget.Button
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.json.JSONArray
import org.json.JSONObject
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import java.io.*
import java.net.URL
import javax.net.ssl.HttpsURLConnection


private const val TAG = "MainActivity2"

class MainActivity2 : AppCompatActivity() {

    val url: String = "https://jsonplaceholder.typicode.com/posts"
    //val sources: String = "https://newsapi.org/v2/sources?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr"
    //val flux: String = "https://newsapi.org/v2/everything?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr&sources="+source

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    //private lateinit var dataset: Array<String>
    private lateinit var dataset: ArrayList<UserDto>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)

        // Initialize dataset, this data would usually come from a local content provider or
        // remote server.
        initDataset()
        getPostsWithVolley()


        viewManager = LinearLayoutManager(this)
        viewAdapter = CustomAdapter(dataset)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            // use this setting to improve performance if you know that changes
            // in content do not change the layout size of the RecyclerView
            setHasFixedSize(true)

            // use a linear layout manager
            layoutManager = viewManager

            // specify an viewAdapter (see also next example)
            adapter = viewAdapter

        }

        */
/*recyclerView.findViewById(R.id.imgAvatar).setOnClickListener{
            val monIntent = Intent(this, MainActivity2::class.java)
            startActivity(monIntent)
        }*//*

    }

    fun getPostsWithVolley() {
        // Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)

        // Request a string response from the provided URL.
        val stringReq = object : JsonObjectRequest(
                Method.GET, url, null,
                { response ->

                    var strResp = response.toString()
                    val jsonArray: JSONArray = JSONArray(strResp)
                    for (i in 0 until jsonArray.length()) {
                        var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                        //var user: UserDto = UserDto(jsonInner.get("name") as String, "Nice try")
                        //dataset.add(user)
                    }
                    Toast.makeText(this, "3-Fetched Data: "+strResp, Toast.LENGTH_SHORT).show()
                    Log.d("3-Fetched Data", strResp)
                },
                {
                    Toast.makeText(this, "That didn't work!", Toast.LENGTH_SHORT).show()
                    Log.d("Fetched Data","That didn't work!")
                }
        )
        {
            override fun getHeaders(): Map<String, String> {
                val headers: MutableMap<String, String> =
                        HashMap()
                headers["User-agent"] = "Mozilla/5.0"
                return headers
            }
        }
        queue.add(stringReq)
    }

    @Throws(IOException::class)
    private fun downloadUrl(url: URL): String? {
        var connection: HttpsURLConnection? = null
        return try {
            connection = (url.openConnection() as? HttpsURLConnection)
            connection?.run {
                // Timeout for reading InputStream arbitrarily set to 3000ms.
                readTimeout = 3000
                // Timeout for connection.connect() arbitrarily set to 3000ms.
                connectTimeout = 3000
                // For this use case, set HTTP method to GET.
                requestMethod = "GET"
                // Already true by default but setting just in case; needs to be true since this request
                // is carrying an input (response) body.
                doInput = true
                // Set Headers
                setRequestProperty("User-Agent","Mozilla/5.0")
                setRequestProperty("Content-Type","application/json")
                // Open communications link (network traffic occurs here).
                connect()

                if (responseCode != HttpsURLConnection.HTTP_OK) {
                    throw IOException("HTTP error code: $responseCode")
                }
                // Retrieve the response body as an InputStream.
                inputStream?.let { stream ->
                    // Converts Stream to String with max length of 500.
                    readStream(stream, 500)
                }
            }
        } finally {
            // Close Stream and disconnect HTTPS connection.
            connection?.inputStream?.close()
            connection?.disconnect()
        }
    }

    @Throws(IOException::class, UnsupportedEncodingException::class)
    fun readStream(stream: InputStream, maxReadSize: Int): String? {
        val reader: Reader? = InputStreamReader(stream, "UTF-8")
        val rawBuffer = CharArray(maxReadSize)
        val buffer = StringBuffer()
        var readSize: Int = reader?.read(rawBuffer) ?: -1
        var maxReadBytes = maxReadSize
        while (readSize != -1 && maxReadBytes > 0) {
            if (readSize > maxReadBytes) {
                readSize = maxReadBytes
            }
            buffer.append(rawBuffer, 0, readSize)
            maxReadBytes -= readSize
            readSize = reader?.read(rawBuffer) ?: -1
        }
        return buffer.toString()
    }
    */
/**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     *//*


    private fun initDataset() {
        //dataset = Array(DATASET_COUNT, {i -> "This is element # $i"})

        dataset = ArrayList<UserDto>()

        for (i in 0..DATASET_COUNT) {
            var user: UserDto = UserDto("", "Awesome work ;)")
            dataset.add(user)
        }
    }
    companion object {
        val TAG = "RecyclerViewActivity"
        private val DATASET_COUNT = 2
    }


}*/
