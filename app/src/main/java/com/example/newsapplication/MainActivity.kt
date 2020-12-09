package com.example.newsapplication


import android.app.DownloadManager
import android.app.VoiceInteractor
import android.content.Intent
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
import com.example.newsapplication.ArticlesDto


class MainActivity : AppCompatActivity() {

    val source_url: String = "https://newsapi.org/v2/sources?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr"
    var current_source: String = "le-monde"
    val articles_url: String = "https://newsapi.org/v2/everything?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr&sources=$current_source"
    lateinit var queue: RequestQueue
    var sources = JSONArray()
    var articles_list = JSONArray()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var dataset: ArrayList<ArticlesDto>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_recycler_view)
        getSources(source_url)
        getArticles(articles_url)
        //initDataset()
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

        recyclerView.setOnClickListener{
            val monIntent = Intent(this, ArticleDetail::class.java)
            startActivity(monIntent)
        }

    }

    private fun initDataset() {
        //dataset = Array(DATASET_COUNT, {i -> "This is element # $i"})

        dataset = ArrayList<ArticlesDto>()

        for (i in 0..DATASET_COUNT) {
            var article: ArticlesDto = ArticlesDto("Beth"+i, "Awesome work ;)", "2020-12-4", "", "", "", "")
            dataset.add(article)
        }
    }
    companion object {
        val TAG = "RecyclerViewActivity"
        private val DATASET_COUNT = 2
    }

    private fun getSources(source_url: String) {
        queue = Volley.newRequestQueue(this)
        val sourcesRequest = object: JsonObjectRequest(
                Method.GET, source_url, null,
                { response ->
                    sources = response.getJSONArray("sources")
                    //Toast.makeText(this, ""+sources.getJSONObject(0).get("name"), Toast.LENGTH_LONG).show()
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

    private fun getArticles(articles_url: String) {
        queue = Volley.newRequestQueue(this)
        dataset = ArrayList<ArticlesDto>()
        val sourcesRequest = object: JsonObjectRequest(
                Method.GET, articles_url, null,
                { response ->
                    articles_list = response.getJSONArray("articles")
                    for (i in 0 until articles_list.length()) {
                        var article: ArticlesDto = ArticlesDto(""+articles_list.getJSONObject(i).get("title"),
                            ""+articles_list.getJSONObject(i).get("author"),
                            ""+articles_list.getJSONObject(i).get("publishedAt"),
                            ""+articles_list.getJSONObject(i).get("source"),
                            ""+articles_list.getJSONObject(i).get("description"),
                            ""+articles_list.getJSONObject(i).get("url"),
                            ""+articles_list.getJSONObject(i).get("urlToImage"))
                        dataset.add(article)
                    }
                    //Toast.makeText(this, ""+dataset[0].title, Toast.LENGTH_LONG).show()
                    upRecyclerView()
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

    private fun upRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = CustomAdapter(dataset)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }

    fun onClickArticle(view: View){
        val monIntent = Intent(this, ArticleDetail::class.java)
        monIntent.putExtra("title", dataset[0].title)
        monIntent.putExtra("author",dataset[0].author)
        monIntent.putExtra("source",dataset[0].source)
        monIntent.putExtra("date",dataset[0].date)
        monIntent.putExtra("description",dataset[0].description)
        monIntent.putExtra("link",dataset[0].link)
        monIntent.putExtra("linkImg",dataset[0].linkImg)

        startActivity(monIntent)

    }
}

