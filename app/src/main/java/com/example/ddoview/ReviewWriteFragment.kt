package com.example.ddoview

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.ddoview.databinding.ReviewWriteFragmentBinding
import com.example.ddoview.data.ReviewWriteItem

class ReviewWriteFragment: Fragment() {

    private var _binding: ReviewWriteFragmentBinding? = null
    private val binding get() = _binding!!
    private var bundle:Bundle? = null
    private lateinit var adapter: ReviewWriteAdapter
    private var company:String? = null
    private var contents:String? = null
    private var mainActivity:MainActivity? = null

    //뒤로가기
    private lateinit var callBack:OnBackPressedCallback

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)
        mainActivity = activity as MainActivity
        callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                mainActivity!!.onChangeFragment(0,mainActivity!!.mainBundle)
            }
        }

        //뒤로 버튼을 누를 경우 ReviewFragment로 이동
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ReviewWriteFragmentBinding.inflate(inflater,container,false)

        val rootView = binding.root


        arguments?.let {
            company = it.getString("company")
            contents = it.getString("contents")
        }

        //리뷰 상세 내용 쓰기
        //binding.contents.text

        //리뷰 리스트
        adapter = ReviewWriteAdapter()
        adapter.addItem(ReviewWriteItem("1. 이 제품을 사용하는 이유는?"))
        adapter.addItem(ReviewWriteItem("2. 장점은?"))
        binding.listView.adapter = adapter


        return rootView
    }

    inner class ReviewWriteAdapter : BaseAdapter() {
        var items: ArrayList<ReviewWriteItem> = ArrayList<ReviewWriteItem>()

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
        fun addItem(item: ReviewWriteItem)
        {
            items.add(item)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            if(view == null)
            {
                view = LayoutInflater.from(parent?.context).inflate(
                        R.layout.review_write_item,
                        parent,
                        false
                )
            }
            val item = items.get(position)
            val question = view?.findViewById<TextView>(R.id.question)
            question!!.setText(item.question)
            return view!!
        }

    }


    override fun onDetach() {
        super.onDetach()
        _binding = null
        bundle = null
        callBack.remove()
        mainActivity = null
    }
}