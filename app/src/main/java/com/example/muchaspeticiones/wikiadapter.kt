package com.example.muchaspeticiones

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.item_wiki.view.*

class wikiadapter(val wikiitem: ArrayList<wikiGETItem>): RecyclerView.Adapter<wikiadapter.wikiholder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): wikiholder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return wikiholder(layoutInflater.inflate(R.layout.item_wiki, parent, false))
    }

    override fun onBindViewHolder(holder: wikiholder, position: Int) {
        holder.render(wikiitem[position])
    }

    override fun getItemCount(): Int = wikiitem.size

    class wikiholder(val view: View): RecyclerView.ViewHolder(view) {
        fun render(wikigetitem: wikiGETItem) {
            view.idtextodescwiki.text   = wikigetitem.pages[0].description
            view.idtextonombrewiki.text = wikigetitem.pages[0].displaytitle
            Picasso.get().load(wikigetitem.pages[0].thumbnail.source).into(view.idimgwiki)
        }
    }

}