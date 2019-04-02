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
import dev.rafaelsermenho.newsreader.model.Source
import dev.rafaelsermenho.newsreader.model.SourceList
import dev.rafaelsermenho.newsreader.view.adapter.SourceAdapter
import dev.rafaelsermenho.newsreader.view.listeners.OnItemClickListener
import dev.rafaelsermenho.newsreader.viewmodel.NewsViewModel
import kotlinx.android.synthetic.main.fragment_news.*
import org.koin.android.viewmodel.ext.android.viewModel

class SourceFragment : Fragment(), OnItemClickListener {

    private val viewModel: NewsViewModel by viewModel()
    private lateinit var viewManager: RecyclerView.LayoutManager
    private lateinit var viewAdapter: SourceAdapter
    private var category: String? = null

    companion object {
        fun newInstance(category: String?) = SourceFragment().also {
            it.category = category
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
        getSources()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupAdapter()
        setupRecyclerView()
    }

    override fun onItemClick(source: Source) {
        source.id?.let { showNewsWithSource(it) }
    }

    private fun getSources() {
        viewModel.getSources(category).observe(this, Observer<SourceList> { sourceList ->
            hideLoading()
            if (shouldUpdateData(sourceList)) {
                updateUI(sourceList!!)
            } else {
                showEmptyStateFragment()
            }

        })
    }

    private fun shouldUpdateData(sourceList: SourceList?) = (sourceList != null && sourceList.sources.isNotEmpty())

    private fun updateUI(sourceList: SourceList) {
        viewAdapter.setData(sourceList)
        viewAdapter.setOnClickListener(this)
    }

    private fun setupAdapter() {
        viewAdapter = SourceAdapter()
    }

    private fun setupRecyclerView() {
        viewManager = LinearLayoutManager(activity)
        recyclerViewNews.apply {
            layoutManager = viewManager
            adapter = viewAdapter
            setHasFixedSize(true)
        }
    }

    private fun showEmptyStateFragment() {
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.container,
            EmptyStateFragment.newInstance()
        ).addToBackStack(null).commit()
    }

    private fun showNewsWithSource(sourceId: String) {
        val fragmentManager = activity!!.supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(
            R.id.container,
            NewsFragment.newInstance(sourceId)
        ).addToBackStack(null).commit()
    }

    private fun showLoading() {
        progressBar.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        progressBar.visibility = View.GONE
    }
}
