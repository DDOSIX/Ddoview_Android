package com.example.ddoview

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.ddoview.databinding.ActivityMainBinding
import com.example.ddoview.listview.AdvertisementItem

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var adapter: AdvertisementAdapter
    private var reviewFragment:ReviewFragment = ReviewFragment()
    private var reviewWiteFragment:ReviewWriteFragment = ReviewWriteFragment()
    private var detailReviewFragment:DetailReviewFragment = DetailReviewFragment()
    var mainBundle = Bundle()

    override fun onStart() {
        super.onStart()
    }

    override fun onStop() {
        super.onStop()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //listview 어댑터
        adapter = AdvertisementAdapter()
        adapter.addItem(AdvertisementItem("GDG", "개발자 구합니다."))
        adapter.addItem(AdvertisementItem("AAA", "개발자 구합니다.2"))
        binding.listView.adapter = adapter

        //아이템을 클릭할 경우 ReviewFragment로 넘어감
        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val item = adapter.getItem(position) as AdvertisementItem

            //title과 contents만 넘긴 후 reviewFragment로 넘김, bundle
            mainBundle.putString("company",item.title)
            mainBundle.putString("contents",item.contents)
            onChangeFragment(0,mainBundle)
        }
    }


    //fragment를 전환 시 함수를 call 하여 fragment로 전환하기
    fun onChangeFragment(index: Int, bundle: Bundle)
    {
        lateinit var fragment:Fragment

        //ReviewFragment로 이동
        if(index == 0)
        {
            fragment = reviewFragment
            reviewFragment.arguments = bundle
        }
        else if(index == 1)
        {
            //reviewWrtieFragment로 이동
            fragment = reviewWiteFragment
            reviewWiteFragment.arguments = bundle
        }
        else if(index == 3)
        {
            //detailReviewFragment로 이동
            fragment = detailReviewFragment
            detailReviewFragment.arguments = bundle
        }

        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }

    inner class AdvertisementAdapter : BaseAdapter() {
        var items: ArrayList<AdvertisementItem> = ArrayList<AdvertisementItem>()

        override fun getCount(): Int {
            return items.size
        }

        override fun getItem(position: Int): Any {
            return items[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        //아이템 추가할 경우..
        fun addItem(item: AdvertisementItem)
        {
            items.add(item)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            if(view == null)
            {
                view = LayoutInflater.from(parent?.context).inflate(
                    R.layout.advertisement_item,
                    parent,
                    false
                )
            }
            val item = items.get(position)
            val company = view?.findViewById<TextView>(R.id.company)
            val contents = view?.findViewById<TextView>(R.id.contents)
            company!!.setText(item.title)
            contents!!.setText(item.contents)
            return view!!
        }

    }


}