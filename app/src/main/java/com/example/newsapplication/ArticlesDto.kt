package com.example.newsapplication

class ArticlesDto {
    var title: String = ""
    var author: String = ""
    var date: String = ""
    var source: String=""
    var description: String= ""
    var link: String=""
    var linkImg: String=""


    constructor() {}

    constructor(title: String, author: String, date: String, source: String, description:String, link: String, linkiImg:String) {
        this.title = title
        this.author = author
        this.date = date
        this.source = source
        this.description = description
        this.link = link
        this.linkImg = linkiImg
    }
}