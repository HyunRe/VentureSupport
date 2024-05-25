package com.example.myactivity.ui.confirm.ui.transform

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class TransformViewModel : ViewModel() {
    private val mTexts: MutableLiveData<List<String>>

    init {
        mTexts = MutableLiveData()
        val texts: MutableList<String> = ArrayList()
        for (i in 1..16) {
            texts.add("This is item # $i")
        }
        mTexts.value = texts
    }

    val texts: LiveData<List<String>>
        get() = mTexts
}