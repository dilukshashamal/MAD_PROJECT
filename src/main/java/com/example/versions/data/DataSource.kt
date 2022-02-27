package com.example.versions.data

import com.example.versions.R
import com.example.versions.model.AndroidVersion

class DataSource {
    fun loadVersions() : List<AndroidVersion>
    {
        return listOf<AndroidVersion>(
            AndroidVersion(R.string.blog1,R.drawable.cnn),
            AndroidVersion(R.string.blog2,R.drawable.bbc_new),
            AndroidVersion(R.string.blog3,R.drawable.aljazeera),
            AndroidVersion(R.string.blog4,R.drawable.adaderana),
            AndroidVersion(R.string.blog5,R.drawable.sundaytimes),
            AndroidVersion(R.string.blog6,R.drawable.sundayobserver),
            AndroidVersion(R.string.blog7,R.drawable.discovery),
            AndroidVersion(R.string.blog8,R.drawable.nationalgeographic),
            AndroidVersion(R.string.blog9,R.drawable.cricbuzz),
            AndroidVersion(R.string.blog10,R.drawable.espncricinfo)

        )
    }
}