package com.example.newsapplication


import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray
import org.json.JSONObject


class MainActivity : AppCompatActivity(),
        CustomAdapter.RecyclerViewClickListener,
        CustomAdapter.OnBottomReachedListener{

    val source_url: String = "https://newsapi.org/v2/sources?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr"
    var current_source: String = "le-monde"
    var current_page: Int = 5
    lateinit var queue: RequestQueue
    var sources = JSONArray()
    var source_list = ArrayList<String>()
    var articles_list = JSONArray()
    private lateinit var recyclerView: RecyclerView
    private lateinit var viewAdapter: RecyclerView.Adapter<*>
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var progressBar: ProgressBar
    var dataset = ArrayList<ArticlesDto>()

    @SuppressLint("ResourceType")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        progressBar= findViewById<ProgressBar>(R.id.progressBar)
        getSources(source_url)
        getArticles(current_page.toString())


        viewManager = LinearLayoutManager(this)
        viewAdapter = CustomAdapter(dataset, this, this)

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
        dataset.clear()
        super.onOptionsItemSelected(item)
        current_source = sources.getJSONObject(item.itemId).getString("id")
        getArticles(current_page.toString())
        return true
    }



    private fun getSources(source_url: String) {
        progressBar.isVisible = true
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
                    progressBar.isVisible = false
                },
                { val builder: AlertDialog.Builder? = DialogActivity@this?.let{
                    AlertDialog.Builder(it)
                }
                    builder?.setMessage("An error occurred, check your internet connection and retry")
                            ?.setTitle("Error")
                            ?.apply {
                                setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialog, id ->
                                })
                                setNegativeButton(R.string.retry, DialogInterface.OnClickListener{ dialog, id ->
                                    getSources(source_url)
                                })
                            }

                    val dialog: AlertDialog? = builder?.create()
                    dialog?.show()

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

    private fun getArticles(page: String) {
        progressBar.isVisible = true
        var currentSource = current_source
        var articles_url: String = "https://newsapi.org/v2/everything?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr&sources=$currentSource&page=$page"
        queue = Volley.newRequestQueue(this)
        val articlesRequest = object: JsonObjectRequest(
                Method.GET, articles_url, null,
                { response ->
                    if(page == current_page.toString()) {
                        articles_list = response.getJSONArray("articles")
                        getInfo(articles_list, dataset)
                        upRecyclerView()
                    } else {
                        current_page = page.toInt()
                        val dataset2= ArrayList<ArticlesDto>()
                        articles_list = response.getJSONArray("articles")
                        getInfo(articles_list, dataset2)
                        dataset.addAll(dataset2);
                        viewAdapter.notifyDataSetChanged()
                        getInfo(articles_list, dataset)
                }
                    progressBar.isVisible = false
                },
                { error ->
                    val builder: AlertDialog.Builder? = DialogActivity@this?.let{
                        AlertDialog.Builder(it)
                    }
                    builder?.setMessage("An error occurred, you may have reached the end of the data")
                            ?.setTitle("Error")
                            ?.apply {
                                setPositiveButton(R.string.ok, DialogInterface.OnClickListener { dialog, id ->
                                })
                                setNegativeButton(R.string.retry, DialogInterface.OnClickListener{ dialog, id ->
                                    getArticles(page)
                                })
                            }

                    val dialog: AlertDialog? = builder?.create()
                    dialog?.show()
                })
        {
            override fun getHeaders(): MutableMap<String, String> {
                val headers = HashMap<String, String>()
                headers["User-Agent"] = "Mozilla/5.0"
                return headers
            }
        }

        queue.add(articlesRequest)
    }

    private fun upRecyclerView() {
        viewManager = LinearLayoutManager(this)
        viewAdapter = CustomAdapter(dataset, this, this)

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

    override fun onBottomReached(index: Int){
        getArticles((current_page+1).toString())
        Log.d("TEST", current_page.toString())
    }



    fun getInfo(jsonArray: JSONArray, dataset: ArrayList<ArticlesDto>){
        for (i in 0 until jsonArray.length()) {
            var source: JSONObject = jsonArray.getJSONObject(i).get("source") as JSONObject
            var name_source: String = source.get("name").toString()
            var article: ArticlesDto = ArticlesDto("" + jsonArray.getJSONObject(i).get("title"),
                    "" + jsonArray.getJSONObject(i).get("author"),
                    "" + jsonArray.getJSONObject(i).get("publishedAt"),
                    "" + name_source,
                    "" + jsonArray.getJSONObject(i).get("description"),
                    "" + jsonArray.getJSONObject(i).get("url"),
                    "" + jsonArray.getJSONObject(i).get("urlToImage"))
            dataset.add(article)
        }
    }




}


