package dev.rafaelsermenho.newsreader.view.listeners

import dev.rafaelsermenho.newsreader.model.Source

interface OnItemClickListener {
    fun onItemClick(source: Source)
}