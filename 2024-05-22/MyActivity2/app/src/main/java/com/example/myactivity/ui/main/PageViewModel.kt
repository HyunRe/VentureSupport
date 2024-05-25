package com.example.myactivity.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.map

class PageViewModel : ViewModel() {
    private val mIndex = MutableLiveData<Int>()
    val text = mIndex.map { input: Int -> "Hello world from section: $input" }
    fun setIndex(index: Int) {
        mIndex.value = index
    }
}