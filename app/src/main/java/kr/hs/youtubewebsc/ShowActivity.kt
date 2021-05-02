package kr.hs.youtubewebsc

import kr.hs.youtubewebsc.Scrapping.ytObj
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.*
import kotlin.collections.ArrayList

class ShowActivity: Activity() {
    lateinit var searchList: RecyclerView
    lateinit var goHomeBtn: Button

    var ytList: ArrayList<ytObj> ?= null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.show_layout)

        if (intent.hasExtra("ytSearchList")) {
            ytList = intent.getSerializableExtra("ytSearchList") as ArrayList<ytObj>?

        } else {
            Toast.makeText(this, "Error: 검색결과가 존재하지 않습니다.", Toast.LENGTH_SHORT)
        }

        searchList = findViewById(R.id.searchList)
        goHomeBtn = findViewById(R.id.goHomeBtn)

        // 참고 RecyclerView: https://recipes4dev.tistory.com/154
        if (ytList.isNullOrEmpty()) {
            Toast.makeText(this, "검색결과가 존재하지 않습니다!", Toast.LENGTH_SHORT)
        } else {
            searchList.layoutManager = LinearLayoutManager(this)
            searchList.adapter = YTListAdapter(ytList!!)
        }
        goHomeBtn.setOnClickListener {
            val intent = Intent(this, ScrapActivity::class.java)
            startActivity(intent)
        }
    }
}