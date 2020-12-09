package com.example.newsapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.TextWatcher
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso

class ArticleDetail : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_article_detail)

        findViewById<TextView>(R.id.Title).text = "Titre: "+intent.getStringExtra("title")
        findViewById<TextView>(R.id.Author).text = "Auteurice: "+intent.getStringExtra("author")
        findViewById<TextView>(R.id.Source).text = "Source: "+intent.getStringExtra("source")
        findViewById<TextView>(R.id.Date).text = "Date: "+intent.getStringExtra("date")
        findViewById<TextView>(R.id.Description).text = "Description: "+intent.getStringExtra("description")

        Picasso.get().load(intent.getStringExtra("linkImg")).into(findViewById<ImageView>(R.id.Image))

        findViewById<TextView>(R.id.Link).setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse(intent.getStringExtra("link")))
           // val intent = Intent(Intent.ACTION_VIEW,Uri.parse("http://www.google.com"))
            startActivity(intent)
        }

    }
}