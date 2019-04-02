package dev.rafaelsermenho.newsreader.view.activity


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import dev.rafaelsermenho.newsreader.R
import dev.rafaelsermenho.newsreader.view.fragment.NewsFragment
import dev.rafaelsermenho.newsreader.view.fragment.SourceContainerFragment
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)


        val fragmentManager = supportFragmentManager

        val retainedFragment = fragmentManager.findFragmentById(R.id.container)
        when (retainedFragment) {
            is NewsFragment -> {
                val sourceId = retainedFragment.getSourceId()
                switchFragment(fragmentManager, NewsFragment.newInstance(sourceId))
            }
            else -> {
                switchFragment(fragmentManager, SourceContainerFragment.newInstance())
            }
        }
    }

    private fun switchFragment(fragmentManager: FragmentManager, fragment: Fragment) {
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.container, fragment).addToBackStack(null).commit()
    }

    override fun onBackPressed() {
        val count = supportFragmentManager.backStackEntryCount
        if (count == 1) {
            finish()
        } else {
            supportFragmentManager.popBackStack()
        }
    }


}
