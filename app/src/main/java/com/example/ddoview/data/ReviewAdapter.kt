package com.example.ddoview.data

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.ddoview.MainActivity
import com.example.ddoview.R

class ReviewAdapter(private val items: ArrayList<ReviewItem>,private val activity: MainActivity) :
    RecyclerView.Adapter<ReviewAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflatedView = LayoutInflater.from(parent.context)
            .inflate(R.layout.review_item, parent, false)
        return ReviewAdapter.ViewHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val listener = View.OnClickListener {

        }

        holder.bind(listener, items[position])

        holder.itemView.setOnClickListener {
            var mainActivity:MainActivity = (activity as MainActivity)
            mainActivity.mainBundle.putString("id",items[position].title)
            mainActivity.mainBundle.putString("company",items[position].contents)
            mainActivity.onChangeFragment(3,mainActivity.mainBundle)
        }
    }

    override fun getItemCount(): Int = items.size

    class ViewHolder(private var v: View) : RecyclerView.ViewHolder(v){
        //date를 추가적으로 넣어주어야 함
        val company = itemView.findViewById<TextView>(R.id.company)
        val contents = itemView.findViewById<TextView>(R.id.contents)

        fun bind(listener: View.OnClickListener, item: ReviewItem){
            v.setOnClickListener(listener)
            company.text = item.title
            contents.text = item.contents
        }
    }

}