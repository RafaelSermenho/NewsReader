package dev.rafaelsermenho.newsreader.view.fragment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import dev.rafaelsermenho.newsreader.R

class EmptyStateFragment : Fragment() {

    companion object {
        fun newInstance() = EmptyStateFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        return inflater.inflate(R.layout.fragment_empty_state, container, false)
    }


}
