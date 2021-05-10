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

            if (searchText.text.equals("요가")) {
                // case 1
                searchByKeyword = "https://www.youtube.com/watch?v=ezEs6sbSsOg"
            } else if (searchText.text.equals("요가 짧게")) {
                // case 2
                searchByKeyword = "https://youtu.be/ezEs6sbSsOg"
            }
            println(" - seachNyworkd: $searchByKeyword")

            // keyword 검색결과 데이터를 local에 저장
            // 1. 검색 결과 URL 스크래핑 --> 로그인 페이지로 이동
            // 2. 특정 영상 URL 스크래핑 --> 잘됨    (ex. "https://youtu.be/1z0x5yTPO4M")
            // 3. 특정 채널 URL 스크래핑 --> 로그인 페이지로 이동

            var scrapObj = Scrapping(searchByKeyword).execute()
//            while (scrapObj.FLAG) {
//                System.out.println("Scrapping!")// 다되었는지 확인
//            }
//
//            // 검색결과 액티비티로 이동
//            val intent = Intent(this, ShowActivity::class.java)
//            intent.putExtra("ytSearchList", scrapObj.get_YTlist())
//            startActivity(intent)
        }
    }
}