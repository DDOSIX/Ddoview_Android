package com.example.ddoview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.ddoview.data.RecruiteWriteItem
import com.example.ddoview.data.ReviewWriteItem
import com.example.ddoview.databinding.RecruiteWriteFragmentBinding
import com.example.ddoview.databinding.ReviewFragmentBinding
import com.example.ddoview.databinding.ReviewWriteFragmentBinding

class RegisterReviewRecruiterFragment: Fragment() {
    private var _binding: RecruiteWriteFragmentBinding ?= null
    private val binding get() = _binding!!
    private var bundle: Bundle? = null
    private lateinit var adapter: RegisterReviewRecruiterFragment.RegisterReviewRecruiterAdapter
    private var company: String? = null
    private var contents: String? = null
    private var mainActivity: MainActivity? = null

    //뒤로가기
    private lateinit var callBack: OnBackPressedCallback

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                // 메인 화면으로 이동
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        //뒤로 버튼을 누를 경우 뒤로 이동
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)

        mainActivity = activity as MainActivity
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = RecruiteWriteFragmentBinding.inflate(inflater,container,false)

        val rootView = binding.root

        //리뷰 상세 내용 쓰기
        //binding.contents.text

        //리뷰 모집글 템플릿
        adapter = RegisterReviewRecruiterAdapter()
        adapter.addItem(RecruiteWriteItem("회사 소개"))
        adapter.addItem(RecruiteWriteItem("서비스 소개"))
        adapter.addItem(RecruiteWriteItem("질문"))
        binding.listView2.adapter = adapter

        binding.btnDone.setOnClickListener {
            // TODO: 리뷰어 모집글 업데이트
            // 메인 화면으로
            val intent2 = Intent(context, MainActivity::class.java)
            startActivity(intent2)
            requireActivity().finish()
        }

        return rootView
    }

    inner class RegisterReviewRecruiterAdapter : BaseAdapter() {
        var items: ArrayList<RecruiteWriteItem> = ArrayList<RecruiteWriteItem>()

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
        fun addItem(item: RecruiteWriteItem)
        {
            items.add(item)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            if(view == null)
            {
                view = LayoutInflater.from(parent?.context).inflate(
                    R.layout.recruite_write_item,
                    parent,
                    false
                )
            }
            val item = items.get(position)
            val question = view?.findViewById<TextView>(R.id.question2)
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