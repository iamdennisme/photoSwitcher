package com.dennisce.com.photoswitcher.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.dennisce.com.photoswitcher.R
import com.dennisce.com.photoswitcher.bean.Folder
import com.squareup.picasso.Picasso
import java.io.File
import java.util.*

/**
 * Created by dennis on 17/10/2017.
 * so.....
 */
class FolderAdapter(private val mContext: Context) : BaseAdapter() {
    private val mInflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private var mFolders: MutableList<Folder>? = ArrayList()

    private var mImageSize: Int = 0

    private var lastSelected = 0

    private val totalImageSize: Int
        get() {
            var result = 0
            if (mFolders != null && mFolders!!.size > 0) {
                for (f in mFolders!!) {
                    result += f.images!!.size
                }
            }
            return result
        }

    var selectIndex: Int
        get() = lastSelected
        set(i) {
            if (lastSelected == i) return

            lastSelected = i
            notifyDataSetChanged()
        }

    init {
        mImageSize = mContext.resources.getDimensionPixelOffset(R.dimen.mis_folder_cover_size)
    }

    /**
     * 设置数据集
     * @param folders
     */
    fun setData(folders: MutableList<Folder>?) {
        if (folders != null && folders.size > 0) {
            mFolders = folders
        } else {
            mFolders!!.clear()
        }
        notifyDataSetChanged()
    }

    override fun getCount(): Int {
        return mFolders!!.size + 1
    }

    override fun getItem(i: Int): Folder? {
        return if (i == 0) null else mFolders!![i - 1]
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    @SuppressLint("SdCardPath", "SetTextI18n")
    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View? {
        var viewNow = view
        val holder: ViewHolder?
        if (viewNow == null) {
            viewNow = mInflater.inflate(R.layout.mis_list_item_folder, viewGroup, false)
            holder = ViewHolder(viewNow)
        } else {
            holder = viewNow.tag as ViewHolder
        }
        if (i == 0) {
            holder.name.setText(R.string.mis_folder_all)
            holder.path.text = "/sdcard"
            holder.size.text = String.format("%d%s",
                    totalImageSize, mContext.resources.getString(R.string.mis_photo_unit))
            if (mFolders!!.size > 0) {
                val f = mFolders!![0]
                Picasso.with(mContext)
                        .load(File(f.cover!!.path))
                        .error(R.drawable.mis_default_error)
                        .resizeDimen(R.dimen.mis_folder_cover_size, R.dimen.mis_folder_cover_size)
                        .centerCrop()
                        .into(holder.cover)
            }
        } else {
            holder.bindData(getItem(i))
        }
        if (lastSelected == i) {
            holder.indicator.visibility = View.VISIBLE
        } else {
            holder.indicator.visibility = View.INVISIBLE
        }
        return viewNow
    }

    internal inner class ViewHolder(view: View) {
        var cover: ImageView = view.findViewById<View>(R.id.cover) as ImageView
        var name: TextView = view.findViewById<View>(R.id.name) as TextView
        var path: TextView = view.findViewById<View>(R.id.path) as TextView
        var size: TextView = view.findViewById<View>(R.id.size) as TextView
        var indicator: ImageView = view.findViewById<View>(R.id.indicator) as ImageView

        init {
            view.tag = this
        }

        @SuppressLint("SetTextI18n")
        fun bindData(data: Folder?) {
            if (data == null) {
                return
            }
            name.text = data.name
            path.text = data.path
            if (data.images != null) {
                size.text = String.format("%d%s", data.images!!.size, mContext.resources.getString(R.string.mis_photo_unit))
            } else {
                size.text = "*" + mContext.resources.getString(R.string.mis_photo_unit)
            }
            if (data.cover != null) {
                // 显示图片
                Picasso.with(mContext)
                        .load(File(data.cover!!.path))
                        .placeholder(R.drawable.mis_default_error)
                        .resizeDimen(R.dimen.mis_folder_cover_size, R.dimen.mis_folder_cover_size)
                        .centerCrop()
                        .into(cover)
            } else {
                cover.setImageResource(R.drawable.mis_default_error)
            }
        }
    }

}
