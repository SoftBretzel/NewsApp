package com.example.newsapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ProgressBar
import androidx.core.view.isVisible

class WebViewActivity : AppCompatActivity() {
    private lateinit var webView: WebView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view)
        progressBar = findViewById<ProgressBar>(R.id.progressBar2)
        webView = findViewById<WebView>(R.id.webView)
        webView.setWebViewClient( WebViewClient())

        progressBar.isVisible = true
        intent.getStringExtra("link")?.let { webView.loadUrl(it) }
        progressBar.isVisible = false
    }
}