package dev.rafaelsermenho.newsreader.view.fragment

import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.view.ViewPager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.rafaelsermenho.newsreader.R
import dev.rafaelsermenho.newsreader.view.adapter.TabsAdapter

class SourceContainerFragment : Fragment() {
    private lateinit var viewPager: ViewPager

    companion object {
        fun newInstance() = SourceContainerFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        retainInstance = true
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_source_container, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupFragment(view)
    }

    private fun setupFragment(view: View) {
        val resources = resources
        val adapter = TabsAdapter(childFragmentManager)
        adapter.add(SourceFragment.newInstance(null), resources.getString(R.string.all))
        adapter.add(
            SourceFragment.newInstance(resources.getString(R.string.business)),
            resources.getString(R.string.business)
        )
        adapter.add(
            SourceFragment.newInstance(resources.getString(R.string.entertainment)),
            resources.getString(R.string.entertainment)
        )
        adapter.add(
            SourceFragment.newInstance(resources.getString(R.string.general)),
            resources.getString(R.string.general)
        )
        adapter.add(
            SourceFragment.newInstance(resources.getString(R.string.health)),
            resources.getString(R.string.health)
        )
        adapter.add(
            SourceFragment.newInstance(resources.getString(R.string.science)),
            resources.getString(R.string.science)
        )
        adapter.add(
            SourceFragment.newInstance(resources.getString(R.string.sports)),
            resources.getString(R.string.sports)
        )
        adapter.add(
            SourceFragment.newInstance(resources.getString(R.string.technology)),
            resources.getString(R.string.technology)
        )

        viewPager = view.findViewById<View>(R.id.viewpager) as ViewPager
        viewPager.adapter = adapter

        val tabLayout = view.findViewById<View>(R.id.tabs) as TabLayout
        tabLayout.setupWithViewPager(viewPager)
    }

}
