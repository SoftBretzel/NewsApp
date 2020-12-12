package com.example.newsapplication

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ProgressBar
import androidx.appcompat.app.AlertDialog
import androidx.core.view.isVisible
import com.android.volley.RequestQueue
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import org.json.JSONArray

class SplasScreenActivity : AppCompatActivity() {

    val source_url: String = "https://newsapi.org/v2/sources?apiKey=719161fc33f648fb9e43e12a6dd05682&language=fr"
    lateinit var queue: RequestQueue
    var sources = JSONArray()
    var source_list = ArrayList<String>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splas_screen)
        getSources(source_url)
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
                val monIntent = Intent(this, MainActivity::class.java)
                startActivity(monIntent)
            },
            {
                val builder: AlertDialog.Builder? = DialogActivity@ this?.let {
                    AlertDialog.Builder(it)
                }
                builder?.setMessage("An error occurred, check your internet connection and retry")
                    ?.setTitle("Error")
                    ?.apply {
                        setPositiveButton(
                            R.string.ok,
                            DialogInterface.OnClickListener { dialog, id ->
                            })
                        setNegativeButton(
                            R.string.retry,
                            DialogInterface.OnClickListener { dialog, id ->
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
}