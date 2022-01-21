package com.example.pioneerstask.ui.display

import android.app.Activity
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.pioneerstask.R
import com.example.pioneerstask.data.model.Item


class GitAdapter(
    var context: Context, private var model: List<Item>,
) :
    RecyclerView.Adapter<GitAdapter.GitViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): GitViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.git_item, parent, false)

        return GitViewHolder(view);
    }

    override fun getItemCount(): Int = model.size

    override fun onBindViewHolder(holder: GitViewHolder, position: Int) {
        Glide.with(context)
            .load(model[position].owner!!.avatar_url)
            .into(holder.itemView.findViewById(R.id.git_row_image))
        holder.itemView.findViewById<TextView>(R.id.git_name).text = model[position].name
        holder.itemView.findViewById<TextView>(R.id.git_description).text = model[position].language


    }

    class GitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    }

}