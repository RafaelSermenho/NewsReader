package dev.rafaelsermenho.newsreader.model

data class SourceList(
    var status: String,
    val sources: List<Source>
)