package com.example.bookapp.adapter


import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.example.bookapp.Screens.CategoryFragment
// Adapter for ViewPager2 that manages Category fragment for each categories
class CategoryPagerAdapter(fragment: Fragment, private val categories: List<String>) :
    FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = categories.size

    override fun createFragment(position: Int): Fragment {
        val categoryFragment = CategoryFragment()
        val bundle = Bundle() //creates bundle to hold categories
        bundle.putString("CATEGORY_KEY", categories[position]) // Passing the category name
        categoryFragment.arguments = bundle
        return categoryFragment
    }
}

