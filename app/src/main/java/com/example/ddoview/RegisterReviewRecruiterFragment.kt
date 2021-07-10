package com.example.ddoview

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.ddoview.data.RecruiteWriteItem
import com.example.ddoview.data.ReviewCommentItem
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

        //EditText
        //소개글을 작성해주세요
        //binding.introWrite
        //회사를 소개해 주세요
        //binding.companyWrite
        //서비스를 소개해주세요
        //binding.serviceWrite

        //리뷰 모집글 템플릿
        adapter = RegisterReviewRecruiterAdapter()
        adapter.addItem(RecruiteWriteItem("첫번째 질문을 작성해주세요"))
        adapter.addItem(RecruiteWriteItem("두번째 질문을 작성해주세요"))
        adapter.addItem(RecruiteWriteItem("세번째 질문을 작성해주세요"))
        binding.listView2.adapter = adapter

        //뒤로가기
        binding.back.setOnClickListener {
            val intent2 = Intent(context, MainActivity::class.java)
            startActivity(intent2)
            requireActivity().finish()
        }
        //등록하기
        binding.register.setOnClickListener {
            val intent2 = Intent(context, MainActivity::class.java)
            startActivity(intent2)
            requireActivity().finish()
        }
        binding.plus.setOnClickListener {
            AddCommentMaker()
            Toast.makeText(context,"click",Toast.LENGTH_SHORT).show()
        }
        return rootView
    }

    //새로운 질문 등록하기, alert dialog 실행
    fun AddCommentMaker()
    {
        var builder = AlertDialog.Builder(activity)

        builder.setTitle("질문 추가하기")
        var editText = EditText(context)
        builder.setView(editText)

        builder.setPositiveButton("등록"){ dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(context,"등록 완료", Toast.LENGTH_SHORT).show()

            var question = editText.text.toString()

            //작성된 내용을 listview에 등록, id는 현재 로그인한 아이디를 넣어야함. ("임시 id")에 데이터
            adapter.addItem(RecruiteWriteItem(question))
            binding.listView2.adapter = adapter
        }

        builder.setNegativeButton("취소"){ dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(context,"등록 취소", Toast.LENGTH_SHORT).show()
        }

        var alertDialog = builder.create()
        alertDialog.show()
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