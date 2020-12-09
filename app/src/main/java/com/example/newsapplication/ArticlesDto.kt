package com.example.newsapplication

class ArticlesDto {
    var title: String = ""
    var author: String = ""
    var date: String = ""

    constructor() {}

    constructor(title: String, author: String, date: String) {
        this.title = title
        this.author = author
        this.date = date
    }
}