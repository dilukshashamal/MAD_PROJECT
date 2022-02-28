package com.example.versions

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.versions.adapter.ItemAdapter
import com.example.versions.data.DataSource

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val textView : TextView = findViewById(R.id.textView1)
        //textView.text = DataSource().loadVersions().size.toString()

        val myDataset = DataSource().loadVersions()

        val recycleView = findViewById<RecyclerView>(R.id.recycler_view)
        recycleView.adapter = ItemAdapter(this, myDataset)



        recycleView.setHasFixedSize(true)
    }
}