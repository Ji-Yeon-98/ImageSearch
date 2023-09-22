package com.example.imagesearch.adapter

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.imagesearch.LikeFragment
import com.example.imagesearch.SearchFragment
import com.example.imagesearch.data.MainTabs

class MainViewPagerAdapter(
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity) {

    private val fragments = ArrayList<MainTabs>()

    init {
        fragments.add(
            MainTabs(SearchFragment.newInstance(), "검색 결과")
        )
        fragments.add(
            MainTabs(LikeFragment.newInstance(), "내 보관함"),
        )
    }

    fun getFragment(position: Int) : Fragment {
        return fragments[position].fragment
    }

    fun getTitle(position: Int): String {
        return fragments[position].titleRes
    }

    override fun getItemCount(): Int {
        return fragments.size
    }

    override fun createFragment(position: Int): Fragment {
        return fragments[position].fragment
    }
}