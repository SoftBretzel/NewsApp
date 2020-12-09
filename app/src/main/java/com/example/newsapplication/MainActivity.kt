package com.example.newsapplication


import android.app.DownloadManager
import android.app.VoiceInteractor
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.RequestQueue
import org.json.JSONArray
import org.json.JSONObject
import com.android.volley.toolbox.Volley
import java.io.*
import java.lang.reflect.Method
import java.net.URL
import javax.net.ssl.HttpsURLConnection
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest


class MainActivity : AppCompatActivity() {

    //val url: String = "https://jsonplaceholder.typicode.com/posts"
    val source_url: String = "https://newsapi.org/v2/sources?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr"
    val current_source: String? = "google-news-fr"
    val articles_url: String = "https://newsapi.org/v2/everything?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr&sources=$current_source"
    lateinit var queue: RequestQueue
    var sources = JSONArray()
    var articles = JSONArray()

    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataset: ArrayList<UserDto>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        dataset = ArrayList<UserDto>()
        getArticles()
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

    }

    private fun initDataset() {
        //dataset = Array(DATASET_COUNT, {i -> "This is element # $i"})

        dataset = ArrayList<UserDto>()

        for (i in 0..DATASET_COUNT) {
            var user: UserDto = UserDto("Beth"+i, "Awesome work ;)")
            dataset.add(user)
        }
    }
    companion object {
        val TAG = "RecyclerViewActivity"
        private val DATASET_COUNT = 2
    }

    private fun getSources() {
        queue = Volley.newRequestQueue(this)
        val sourcesRequest = object: JsonObjectRequest(
                Method.GET, source_url, null,
                { response ->
                    sources = response.getJSONArray("sources")
                    Toast.makeText(this, ""+sources.getJSONObject(0).get("name"), Toast.LENGTH_LONG).show()
                },
                { _ ->

                })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        queue.add(sourcesRequest)
    }

    private fun getArticles() {
        queue = Volley.newRequestQueue(this)
        val sourcesRequest = object: JsonObjectRequest(
                Method.GET, articles_url, null,
                { response ->
                    articles = response.getJSONArray("articles")
                    for (j in 0..2) {
                        var user: UserDto = UserDto(""+articles.getJSONObject(j).get("title"), "Awesome work ;)")
                        dataset.add(user)
                    }
                    Toast.makeText(this, ""+articles.getJSONObject(0).get("title"), Toast.LENGTH_LONG).show()
                },
                { _ ->

                })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        queue.add(sourcesRequest)
    }

}