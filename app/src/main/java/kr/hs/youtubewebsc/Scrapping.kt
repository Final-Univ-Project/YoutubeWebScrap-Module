package kr.hs.youtubewebsc

import android.os.Parcelable
import android.text.Html
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import java.io.Serializable
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

class Scrapping {
    var FLAG = true
    private lateinit var ytList: ArrayList<ytObj>

    class ytObj {
        var url: String
        var img: String
        var title: String
        var youtuber: String
        var relDate: String
        var len: String
        var viewNo: String

        constructor(
                url: String,
                img: String,
                title: String,
                youtuber: String,
                relDate: String,
                len: String,
                viewNo: String
        ) {
            this.url = url; this.img = img; this.title = title
            this.youtuber = youtuber; this.relDate = relDate
            this.len = len; this.viewNo = viewNo
        }
    }

    // NW를 통한 작업이라 비동기식으로 구현
    constructor(url: String) {
        Thread(Runnable {
            // 참고 Jsoup: https://jsoup.org/apidocs/org/jsoup/nodes/Element.html
            val doc = Jsoup.connect(url).get()
            val first = doc.getElementById("header-container")
            val mov_li = first.getElementsByTag("ytd-video-renderer")

            // works에 저장
            ytList = ArrayList<ytObj>()

            // 1) for문 이용
//            for (item in mov_li) {
//                // 요소 중 안에 같은 태그가 있으면 continue
//                ytList.add(scrapYTlist(item))
//            }

            // 2) forEach 이용
            mov_li.forEach { item ->
                // 요소 중 안에 같은 태그가 있으면 continue
                ytList.add(scrapYTlist(item))
            }

            FLAG = false
        })
    }

    fun get_YTlist(): ArrayList<ytObj> {
        return ytList
    }

    fun scrapYTlist(html: Element): ytObj {
        html.getElementById("thumbnail").attr("href")
        val img = html.getElementById("img").attr("src")
        val len = html.getElementById("overlays")
                .select("span").attr("aria-label")

        val container = html.getElementById("video-title")
        val title = container.attr("title")
        val url = container.attr("href")
        val labels = container.attr("aria-label").split(" ")
        // 게시자: // 조회수

        return ytObj(url, img, title, labels[-5], labels[-4], len, labels[-1])
    }

    // Q. 추천하는 방법
    // (운동한 영상의 메타데이터를 키워드 검색에 활용?)
    // (운동한 영상의 댓글도 가져올 수 있나?)
    // 1. 영상길이 (4분 이내/ 10분 이내/ 10분 이상/ 등등)
    // 2. 키워드 검색
}