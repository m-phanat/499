package com.independent.m.mproject499.adapter

import android.content.Context
import android.os.Build
import android.speech.tts.TextToSpeech
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.android.databinding.library.baseAdapters.BR
import com.independent.m.mproject499.MainApp
import com.independent.m.mproject499.MainApp.Companion.History
import com.independent.m.mproject499.databinding.ItemWordBinding
import com.independent.m.mproject499.model.WordFireBase
import com.independent.m.mproject499.model.WordHistory
import com.vicpin.krealmextensions.save
import kotlinx.android.synthetic.main.item_word.view.*
import java.util.*


class WordsAdapter(val context: Context) : RecyclerView.Adapter<WordsAdapter.WordsAdapterViewHolder>() {


    private var items: ArrayList<WordFireBase> = java.util.ArrayList()

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsAdapterViewHolder {
        val layoutInflator: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        return WordsAdapterViewHolder(
            ItemWordBinding.inflate(
                layoutInflator,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: WordsAdapterViewHolder, position: Int) = holder.bind(items[position])

    class WordsAdapterViewHolder(val binding: ItemWordBinding) : RecyclerView.ViewHolder(binding.root) {

        var tts: TextToSpeech? = MainApp.tts
        val context: Context = binding.root.context

        fun bind(item: WordFireBase) {
            binding.setVariable(BR.word, item.word.capitalize())
            binding.setVariable(BR.meaning, "?")
            binding.setVariable(BR.descEng, item.desc_eng)
            binding.setVariable(BR.descTH, "?")

            itemView.txtName.setOnClickListener {
                WordHistory().let {
                    it.id = item.word
                    it.save()
                }
                speakOut(item.word)
            }
            itemView.txtEng.setOnClickListener {
                speakOut(item.desc_eng)
            }
            itemView.txtMeaning.setOnClickListener {
                binding.setVariable(BR.meaning, item.meaning)
                History.add(item)
                Log.d("addItem", item.word)
            }
            itemView.txtTH.setOnClickListener {
                binding.setVariable(BR.descTH, item.desc_th)
            }
        }

        private fun speakOut(text: String) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                tts!!.speak(text, TextToSpeech.QUEUE_FLUSH, null, "")
            }
        }
    }

    fun loadData(data: ArrayList<WordFireBase>) {
        this.items = data
        notifyDataSetChanged()
    }


}