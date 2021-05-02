package kr.hs.youtubewebsc

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.view.LayoutInflater
import kr.hs.youtubewebsc.YTListAdapter.YTListViewHolder
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import java.lang.Exception
import java.net.URL
import java.util.*
import kotlin.collections.ArrayList

// 참고: https://developer.android.com/guide/topics/ui/layout/recyclerview?hl=ko#steps-for-implementing
class YTListAdapter(private val dataSet: ArrayList<Scrapping.ytObj>) : RecyclerView.Adapter<YTListViewHolder>() {
//    private var items: ArrayList<Scrapping.ytObj>

//    constructor {
//        this.items = list
//    }

    class YTListViewHolder(itemView: View): ViewHolder(itemView) {
        var img: ImageView
        var title: TextView
        var youtuber: TextView

        var url: TextView
        var relDate: TextView
        var len: TextView
        var viewNo: TextView

        init {
            img = itemView.findViewById(R.id.img)
            title = itemView.findViewById(R.id.title)
            youtuber = itemView.findViewById(R.id.youtuber)

            url = itemView.findViewById(R.id.url)
            relDate = itemView.findViewById(R.id.relDate)
            len = itemView.findViewById(R.id.len)
            viewNo = itemView.findViewById(R.id.viewNo)
        }
    }

    fun getBitmapByURL(url: String): Bitmap? {
        var image: Bitmap? = null
        try {
            val tmp = URL(url).openStream()
            image = BitmapFactory.decodeStream(tmp)
        } catch (e: Exception) {
            Log.e("YTListAdapter-VH", "Error: 비트맵 이미지를 로드하지 못했습니다!!")
        }
        return image
    }
    override fun onBindViewHolder(holder: YTListViewHolder, position: Int) {
        var itemObj = dataSet[position]

        var imgUrl = itemObj.img
        holder.img.setImageBitmap(getBitmapByURL(imgUrl))
        holder.title.text = itemObj.title
        holder.youtuber.text = itemObj.youtuber
        holder.url.text = itemObj.url
        holder.relDate.text = itemObj.relDate
        holder.len.text = itemObj.len
        holder.viewNo.text = itemObj.viewNo
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): YTListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.ytlist_item, parent, false)

        return YTListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dataSet.size
    }
}