package kr.hs.youtubewebsc

import android.os.AsyncTask
import androidx.lifecycle.ViewModel
import com.google.gson.annotations.SerializedName
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.nodes.Element
import org.jsoup.select.Elements
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import java.io.IOException
import java.util.concurrent.TimeUnit
import kotlin.collections.ArrayList

class Scrapping(val url: String): AsyncTask<Void, Void, Void>() {
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

    class Networking {
        fun createClient(): OkHttpClient {
            return OkHttpClient.Builder()
                    .connectTimeout(30L, TimeUnit.SECONDS)
                    .readTimeout(15L, TimeUnit.SECONDS)
                    .writeTimeout(15L, TimeUnit.SECONDS)
                    .build()
        }

        inline fun <reified T> createWebService(okHttpClient: OkHttpClient, url: String) : T{
            val retrofit = Retrofit.Builder()
                    .baseUrl(url)
                    .client(okHttpClient)
                    // For json parser
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()

            return retrofit.create(T::class.java)
        }
    }

    override fun doInBackground(vararg params: Void?): Void? {
        try {
            val doc = Jsoup.connect(url).get().head()
            // get().body().allElements.tagName("").first()
            println(" - isBlock: ${doc.isBlock}")
            println(" - doc: $doc")

            val first: Element? = doc.getElementsByTag("ytd-app").first()
            println("   - first: $first")
            val tmp = doc.select("ytd-app")
            println("   - tmp (${first?.equals(tmp)}): $tmp")

            // ??? ???????????? ????????? ???????????? ??????
            // --> ?????? ?????? ???????????? ?????? ???????????? <head>?????? ????????? ??? ??????
            //     ?????? meta data??? ????????? ???????????? ?????? ???????????? ??????
            //     ????????? ????????? API??? ???????????? DB??? ???????????? ???????????? ??? ?????????
            val second = first?.getElementsByTag("title")?.first()
            println("   - second(title): $second")
            val third: Elements? = first?.getElementsByTag("meta")
            println("   - third(meta): $third")

            if (third != null) {
                for (li in third) {
                    val desc = third.attr("name")
                    println("   - desc: $desc")
                    val keywords = third.attr("content")
                    println("   - keywords: $keywords")
                }
            }

//            // works??? ??????
//            ytList = ArrayList<ytObj>()
//
//            // 1) for??? ??????
////            for (item in mov_li) {
////                // ?????? ??? ?????? ?????? ????????? ????????? continue
////                ytList.add(scrapYTlist(item))
////            }
//
//            // 2) forEach ??????
//            mov_li?.forEach { item ->
//                // ?????? ??? ?????? ?????? ????????? ????????? continue
//                ytList.add(scrapYTlist(item))
//            }

        } catch (e: IOException) {
            e.printStackTrace()
        }
        return null
    }

    override fun onPreExecute() {
        super.onPreExecute()
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
        // ?????????: // ?????????

        return ytObj(url, img, title, labels[-5], labels[-4], len, labels[-1])
    }

    // Q. ???????????? ??????
    // (????????? ????????? ?????????????????? ????????? ????????? ???????)
    // (????????? ????????? ????????? ????????? ??? ???????)
    // 1. ???????????? (4??? ??????/ 10??? ??????/ 10??? ??????/ ??????)
    // 2. ????????? ??????
}