package com.example.newsapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject
import java.net.URL


class MainActivity : AppCompatActivity() {

    val url: String = "https://jsonplaceholder.typicode.com/posts"
    //val sources: String = "https://newsapi.org/v2/sources?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr"
    //val flux: String = "https://newsapi.org/v2/everything?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr&sources=google-news-fr"

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

        /*recyclerView.findViewById(R.id.imgAvatar).setOnClickListener{
            val monIntent = Intent(this, MainActivity2::class.java)
            startActivity(monIntent)
        }*/
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
                dataset = ArrayList<UserDto>()
                for (i in 0 until jsonArray.length()) {
                    var jsonInner: JSONObject = jsonArray.getJSONObject(i)
                    strPosts = strPosts + "\n" + jsonInner.get("title")
                    var user: UserDto = UserDto(jsonInner.get("title") as String, "Awesome work ;)")
                    dataset.add(user)
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
    /**
     * Generates Strings for RecyclerView's adapter. This data would usually come
     * from a local content provider or remote server.
     */

    companion object {
        val TAG = "RecyclerViewActivity"
        private val DATASET_COUNT = 2
    }


}