package org.loop.example.view

import android.content.Context
import android.support.constraint.ConstraintLayout
import android.support.v7.util.DiffUtil
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.reddit_item_a.view.*
import org.loop.example.R
import org.loop.example.models.pojo.Child
import org.loop.example.models.pojo.RedditResponse


class HomeAdapter(private val context: Context, var response: RedditResponse)
    : RecyclerView.Adapter<HomeAdapter.ViewHolder>() {


    private var list = mutableListOf<Child>()

    init {
        if (response.data?.children != null) list.addAll(response.data.children)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder? {
        val v: View = LayoutInflater.from(parent.context)
                .inflate(R.layout.reddit_item_a, parent, false)

        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(context).load(list[position].data.thumbnail).into(holder.thumb)
        holder.title.text = list[position].data.title
    }

    override fun getItemCount(): Int {
        return list.size
    }

    fun updateList(response: RedditResponse) {
        this.response = response

        val diffResult = DiffUtil.calculateDiff(DiffCallback(this.list, getUpdatedList(response.data.children)), false)
        this.list.addAll(response.data.children)
        diffResult.dispatchUpdatesTo(this)


    }

    public fun requestList() : List<Child>{
        return this.list
    }


    private fun getUpdatedList(newList: List<Child>): MutableList<Child> {
        val list = arrayListOf<Child>()
        list.addAll(this.list)
        list.addAll(newList)
        return list
    }

    fun requestNextKey(): String {
        return response.data.after ?: ""
    }

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val thumb: ImageView = itemView.reddit_item_thumb
        val title: TextView = itemView.reddit_item_title
        val container: ConstraintLayout = itemView.reddit_item_container

        init {
            container.setOnClickListener(this)
        }

        override fun onClick(v: View?) {}
    }

}
