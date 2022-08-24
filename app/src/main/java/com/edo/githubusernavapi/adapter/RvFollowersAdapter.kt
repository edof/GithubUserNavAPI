package com.edo.githubusernavapi.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edo.githubusernavapi.DetailFollowers
import com.edo.githubusernavapi.R
import com.edo.githubusernavapi.databinding.ItemRowGithubBinding

class RvFollowersAdapter(private val datas: MutableList<DetailFollowers>, val ctx: Context) :
    RecyclerView.Adapter<RvFollowersAdapter.MyViewHolder>() {

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val binding = ItemRowGithubBinding.bind(view)
        val tvName = binding.tvItemName
        val tvUname = binding.tvItemUsername
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_row_github, parent, false)
        return MyViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.tvUname.text = "@" + datas[position].login
        holder.tvName.text = "ID: " + datas[position].id.toString()
        val ava = datas[position].avatarUrl
        Glide.with(holder.itemView.context)
            .load(ava)
            .circleCrop()
            .into(holder.binding.ivAva)
    }


    override fun getItemCount(): Int = datas.size

    fun setData(data: List<DetailFollowers>) {
        datas.clear()
        datas.addAll(data)
        notifyDataSetChanged()
    }
}