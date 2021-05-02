package kr.hs.youtubewebsc

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class ScrapActivity : Activity() {
    lateinit var searchText: EditText
    lateinit var searchBtn: Button

    var searchByKeyword: String = "https://www.youtube.com/results?search_query="

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.scrap_layout)

        searchText = findViewById(R.id.searchText)
        searchBtn = findViewById(R.id.searchBtn)

        searchBtn.setOnClickListener {
            searchByKeyword += searchText.text

            // keyword 검색결과 데이터를 local에 저장
            var scrapObj = Scrapping(searchByKeyword)
            while (scrapObj.FLAG);  // 다되었는지 확인

            // 검색결과 액티비티로 이동
            val intent = Intent(this, ShowActivity::class.java)
            intent.putExtra("ytSearchList", scrapObj.get_YTlist())
            startActivity(intent)
        }
    }
}