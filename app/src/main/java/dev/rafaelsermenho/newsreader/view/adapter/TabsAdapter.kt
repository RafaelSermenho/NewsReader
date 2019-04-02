package dev.rafaelsermenho.newsreader.view.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class TabsAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

    private val listFragments = ArrayList<Fragment>()
    private val listFragmentsTitle = ArrayList<String>()

    fun add(frag: Fragment, title: String) {
        this.listFragments.add(frag)
        this.listFragmentsTitle.add(title)
    }

    override fun getItem(position: Int): Fragment {
        return listFragments[position]
    }

    override fun getCount(): Int {
        return listFragments.size
    }

    override fun getPageTitle(position: Int): CharSequence? {
        return listFragmentsTitle[position]
    }
}