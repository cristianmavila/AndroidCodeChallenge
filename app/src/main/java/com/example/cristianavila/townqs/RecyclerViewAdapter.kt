package com.example.cristianavila.townqs

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.content.Context
import android.content.Intent
import android.view.ViewGroup
import kotlinx.android.synthetic.main.post_item_layout.view.*

class PostItemAdapter(val context: Context, val postList: List<Post>) : RecyclerView.Adapter<PostItemAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.post_item_layout, parent, false))
    }

    override fun getItemCount(): Int {
        return postList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val postItem = postList.get(position)
        holder.view.txtPostTitle.text = postItem.title
        holder.postItem = postItem
    }

    class ViewHolder(val view: View, var postItem: Post? = null) : RecyclerView.ViewHolder(view) {

        companion object {
            val POST_ID = "POST_ID"
            val POST_TITLE = "POST_TITLE"
            val POST_USER = "POST_USER"
        }

        init {
            view.setOnClickListener {
                val intent = Intent(view.context, DetailActivity::class.java)
                intent.putExtra(POST_ID, postItem?.id)
                intent.putExtra(POST_TITLE, postItem?.title)
                intent.putExtra(POST_USER, postItem?.userId)
                view.context.startActivity(intent)
            }
        }
    }

}