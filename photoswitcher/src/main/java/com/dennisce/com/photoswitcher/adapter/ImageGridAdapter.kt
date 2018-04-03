package com.dennisce.com.photoswitcher.adapter

import android.content.Context
import android.graphics.Point
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.BaseAdapter
import android.widget.ImageView
import com.dennisce.com.photoswitcher.MultiImageSelectorFragment
import com.dennisce.com.photoswitcher.R
import com.dennisce.com.photoswitcher.bean.Image
import com.squareup.picasso.Picasso
import java.io.File
import java.util.*


/**
 * Created by dennis on 17/10/2017.
 * so.....
 */
class ImageGridAdapter(private val mContext: Context, showCamera: Boolean, column: Int) : BaseAdapter() {

    private val mInflater: LayoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    private var showCamera = true
    private var showSelectIndicator = true

    private var mImages: MutableList<Image>? = ArrayList()
    private val mSelectedImages = ArrayList<Image>()

    internal val mGridWidth: Int

    var isShowCamera: Boolean
        get() = showCamera
        set(b) {
            if (showCamera == b) return

            showCamera = b
            notifyDataSetChanged()
        }

    init {
        this.showCamera = showCamera
        val wm = mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val width: Int
        val size = Point()
        wm.defaultDisplay.getSize(size)
        width = size.x
        mGridWidth = width / column
    }

    /**
     * 显示选择指示器
     * @param b
     */
    fun showSelectIndicator(b: Boolean) {
        showSelectIndicator = b
    }

    /**
     * 选择某个图片，改变选择状态
     * @param image
     */
    fun select(image: Image) {
        if (mSelectedImages.contains(image)) {
            mSelectedImages.remove(image)
        } else {
            mSelectedImages.add(image)
        }
        notifyDataSetChanged()
    }

    /**
     * 通过图片路径设置默认选择
     * @param resultList
     */
    fun setDefaultSelected(resultList: ArrayList<String>) {
        resultList.mapNotNullTo(mSelectedImages) { getImageByPath(it) }
        if (mSelectedImages.size > 0) {
            notifyDataSetChanged()
        }
    }

    private fun getImageByPath(path: String): Image? {
        if (mImages != null && mImages!!.size > 0) {
            mImages!!
                    .asSequence()
                    .filter { it.path.equals(path, ignoreCase = true) }
                    .forEach { return it }
        }
        return null
    }

    /**
     * 设置数据集
     * @param images
     */
    fun setData(images: MutableList<Image>?) {
        mSelectedImages.clear()

        if (images != null && images.size > 0) {
            mImages = images
        } else {
            mImages!!.clear()
        }
        notifyDataSetChanged()
    }

    override fun getViewTypeCount(): Int {
        return 2
    }

    override fun getItemViewType(position: Int): Int {
        return if (showCamera) {
            if (position == 0) TYPE_CAMERA else TYPE_NORMAL
        } else TYPE_NORMAL
    }

    override fun getCount(): Int {
        return if (showCamera) mImages!!.size + 1 else mImages!!.size
    }

    override fun getItem(i: Int): Image? {
        return if (showCamera) {
            if (i == 0) {
                null
            } else mImages!![i - 1]
        } else {
            mImages!![i]
        }
    }

    override fun getItemId(i: Int): Long {
        return i.toLong()
    }

    override fun getView(i: Int, view: View?, viewGroup: ViewGroup): View? {
        var viewNow = view

        if (isShowCamera) {
            if (i == 0) {
                viewNow = mInflater.inflate(R.layout.mis_list_item_camera, viewGroup, false)
                return viewNow
            }
        }

        val holder: ViewHolder?
        if (viewNow == null) {
            viewNow = mInflater.inflate(R.layout.mis_list_item_image, viewGroup, false)
            holder = ViewHolder(viewNow)
        } else {
            holder = viewNow.tag as ViewHolder
        }

        holder.bindData(getItem(i))

        return viewNow
    }

    internal inner class ViewHolder(view: View) {
        var image: ImageView = view.findViewById<View>(R.id.image) as ImageView
        var indicator: ImageView = view.findViewById<View>(R.id.checkmark) as ImageView
        var mask: View = view.findViewById(R.id.mask)

        init {
            view.tag = this
        }

        fun bindData(data: Image?) {
            if (data == null) return
            // 处理单选和多选状态
            if (showSelectIndicator) {
                indicator.visibility = View.VISIBLE
                if (mSelectedImages.contains(data)) {
                    // 设置选中状态
                    indicator.setImageResource(R.drawable.mis_btn_selected)
                    mask.visibility = View.VISIBLE
                } else {
                    // 未选择
                    indicator.setImageResource(R.drawable.mis_btn_unselected)
                    mask.visibility = View.GONE
                }
            } else {
                indicator.visibility = View.GONE
            }
            val imageFile = File(data.path)
            if (imageFile.exists()) {
                // 显示图片
                Picasso.get()
                        .load(imageFile)
                        .placeholder(R.drawable.mis_default_error)
                        .tag(MultiImageSelectorFragment.TAG)
                        .resize(mGridWidth, mGridWidth)
                        .centerCrop()
                        .into(image)
            } else {
                image.setImageResource(R.drawable.mis_default_error)
            }
        }
    }

    companion object {

        private val TYPE_CAMERA = 0
        private val TYPE_NORMAL = 1
    }

}
