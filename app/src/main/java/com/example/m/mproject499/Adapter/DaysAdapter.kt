package com.example.m.mproject499.Adapter

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.example.m.mproject499.Activity.WordsActivity
import com.example.m.mproject499.Model.Days
import com.example.m.mproject499.databinding.DaysListBinding
import java.util.*


class DaysAdapter(val context: Context): RecyclerView.Adapter<DaysAdapter.DaysAdapterViewHolder>() {

    private var items: ArrayList<Days> = java.util.ArrayList()


    override fun getItemCount(): Int {
        return items.size
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DaysAdapterViewHolder {
        val layoutInflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return DaysAdapterViewHolder(com.example.m.mproject499.databinding.DaysListBinding.inflate(layoutInflator, parent, false))
    }

    override fun onBindViewHolder(holder: DaysAdapterViewHolder, position: Int) = holder.bind(items[position])

    class DaysAdapterViewHolder(val binding: DaysListBinding) : RecyclerView.ViewHolder(binding.root) {

        val context: Context = binding.root.context

        fun bind(item: Days) {

            binding.setVariable(BR.name, item.name)
            binding.setVariable(BR.comment, item.comment)
            itemView.setOnClickListener {
                Log.d("","test $itemId")
            }

        }
    }

    fun loadDatas(data: ArrayList<Days>){
        this.items = data
        notifyDataSetChanged()
    }
}