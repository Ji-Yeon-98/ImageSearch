package com.example.imagesearch

import android.content.Context

object SharedPreference {

    fun saveData(context: Context, query: String){
        val pref = context.getSharedPreferences("pref", Context.MODE_PRIVATE)
        pref.edit().putString("searchText", query).apply()
    }

    fun loadData(context: Context): String? {
        val pref = context.getSharedPreferences("pref", 0)
        return pref.getString("searchText", null)
    }

}