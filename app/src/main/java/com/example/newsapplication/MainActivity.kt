package com.example.newsapplication


import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity(), CustomAdapter.RecyclerViewClickListener{

    val source_url: String = "https://newsapi.org/v2/sources?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr"
    var current_source: String = "le-monde"
    lateinit var queue: RequestQueue
    var sources = JSONArray()
    var source_list = ArrayList<String>()
    var articles_list = JSONArray()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    var dataset = ArrayList<ArticlesDto>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getSources(source_url)
        getArticles()


        viewManager = LinearLayoutManager(this)
        viewAdapter = CustomAdapter(dataset, this)

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

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val  inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.example_menu, menu)
        return true
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        super.onPrepareOptionsMenu(menu)
        if (sources.length() > 0) {
            menu?.clear()
        }
        for (index in 0 until sources.length()) {
            menu?.add(0, index, index, sources.getJSONObject(index).getString("name"))
        }
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        super.onOptionsItemSelected(item)
        current_source = sources.getJSONObject(item.itemId).getString("id")
        getArticles()
        return true
    }



    private fun getSources(source_url: String) {
        queue = Volley.newRequestQueue(this)
        var nom: String
        val sourcesRequest = object: JsonObjectRequest(
                Method.GET, source_url, null,
                { response ->
                    sources = response.getJSONArray("sources")
                    for (i in 0 until sources.length()) {
                        nom = sources.getJSONObject(i).get("name").toString()
                        source_list.add(nom)
                    }
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

    private fun getArticles() {
        var currentSource = current_source
        var articles_url: String = "https://newsapi.org/v2/everything?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr&sources=$currentSource"
        queue = Volley.newRequestQueue(this)
        dataset.clear()
        val sourcesRequest = object: JsonObjectRequest(
                Method.GET, articles_url, null,
                { response ->
                    articles_list = response.getJSONArray("articles")
                    for (i in 0 until articles_list.length()) {
                        var source: JSONObject = articles_list.getJSONObject(i).get("source") as JSONObject
                        var name_source: String = source.get("name").toString()
                        var article: ArticlesDto = ArticlesDto("" + articles_list.getJSONObject(i).get("title"),
                                "" + articles_list.getJSONObject(i).get("author"),
                                "" + articles_list.getJSONObject(i).get("publishedAt"),
                                "" + name_source,
                                "" + articles_list.getJSONObject(i).get("description"),
                                "" + articles_list.getJSONObject(i).get("url"),
                                "" + articles_list.getJSONObject(i).get("urlToImage"))
                        dataset.add(article)
                    }
                    //Toast.makeText(this, "" + dataset[0].title, Toast.LENGTH_LONG).show()
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
        viewAdapter = CustomAdapter(dataset, this)

        recyclerView = findViewById<RecyclerView>(R.id.my_recycler_view).apply {
            setHasFixedSize(true)
            layoutManager = viewManager
            adapter = viewAdapter
        }
    }


    override fun recyclerViewListClicked(index: Int){
        val monIntent = Intent(this, ArticleDetail::class.java)
        monIntent.putExtra("title", dataset[index].title)
        monIntent.putExtra("author", dataset[index].author)
        monIntent.putExtra("source", dataset[index].source)
        monIntent.putExtra("date", dataset[index].date)
        monIntent.putExtra("description", dataset[index].description)
        monIntent.putExtra("link", dataset[index].link)
        monIntent.putExtra("linkImg", dataset[index].linkImg)

        startActivity(monIntent)
        }



}


