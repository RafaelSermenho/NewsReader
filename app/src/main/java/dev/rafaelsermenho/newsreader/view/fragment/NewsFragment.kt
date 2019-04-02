package dev.rafaelsermenho.newsreader.view.fragment

import android.arch.lifecycle.Observer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.rafaelsermenho.newsreader.R
import dev.rafaelsermenho.newsreader.model.Article
import dev.rafaelsermenho.newsreader.view.adapter.ArticleAdapter
import dev.rafaelsermenho.newsreader.view.components.EndlessRecyclerView
import dev.rafaelsermenho.newsreader.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class NewsFragment : Fragment() {
    private val viewModel: NewsViewModel by viewModel()
    private lateinit var viewManager: LinearLayoutManager
    private lateinit var viewAdapter: ArticleAdapter
    private var sourceId: String = ""
    private lateinit var endlessRecyclerView: EndlessRecyclerView

    companion object {
        fun newInstance(sourceId: String) = NewsFragment().also {
            it.sourceId = sourceId
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        showLoading()
        getArticles()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setScrollListener()
        setupRecyclerView()
    }

    private fun setupRecyclerView() {
        recyclerViewNews.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            setHasFixedSize(true)
            addOnScrollListener(endlessRecyclerView)
        }
    }

    private fun setupAdapter() {
        viewAdapter = ArticleAdapter()
    }

    private fun setScrollListener() {
        viewManager = LinearLayoutManager(activity, RecyclerView.VERTICAL, false)
        endlessRecyclerView = object : EndlessRecyclerView(viewManager) {
            override fun onLoadMore(page: Int) {
                showLoading()
                getArticles(page)
            }
        }
    }

    private fun getArticles(page: Int = 1) {
        viewModel.getArticlesFrom(sourceId, page).observe(this, Observer<List<Article>> { articleList ->
            hideLoading()
            if (shouldUpdateData(articleList)) {
                updateUI(articleList!!)
            } else {
                if (shouldShowEmptyFragment()) {
                    showEmptyStateFragment()
                }
            }

        })
    }

    private fun shouldUpdateData(articleList: List<Article>?) = (!articleList.isNullOrEmpty())

    private fun shouldShowEmptyFragment() = (viewAdapter.itemCount == 0)

    private fun updateUI(articleList: List<Article>) {
        viewAdapter.setData(articleList)
    }

    private fun showEmptyStateFragment() {
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.container,
            EmptyStateFragment.newInstance()
        ).addToBackStack(null).commit()

    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }

    fun getSourceId(): String {
        return sourceId
    }


}
