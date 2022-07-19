package com.edo.githubusernavapi

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.edo.githubusernavapi.databinding.ItemRowGithubBinding
import java.net.URL

class RvAdapter(private val datas: MutableList<GitResponse>, val ctx: Context) :
    RecyclerView.Adapter<RvAdapter.MyViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

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
        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(datas[holder.adapterPosition])
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: GitResponse)
    }

    override fun getItemCount(): Int = datas.size

    fun setData(data: List<GitResponse>) {
        datas.clear()
        datas.addAll(data)
        notifyDataSetChanged()
    }
}