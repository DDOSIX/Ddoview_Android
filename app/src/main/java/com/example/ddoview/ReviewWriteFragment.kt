package com.example.ddoview

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
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
            binding.company.text = company
            contents = it.getString("contents")
            binding.intro.text = contents
            val category = it.getString("category")
            binding.category.text = category
        }

        //리뷰 상세 내용 쓰기
        //binding.contents.text

        //리뷰 리스트
        adapter = ReviewWriteAdapter()
        adapter.addItem(ReviewWriteItem("리뷰 제목을 써주세요",""))
        adapter.addItem(ReviewWriteItem("1. 이 제품을 사용하는 이유는?",""))
        adapter.addItem(ReviewWriteItem("2. 장점은?",""))
        binding.listView.adapter = adapter

        binding.reviewContents.text =
            "  리뷰 양식에 맞추어 내용을 충분히 작성해주세요.\n" +
                    "  명예훼손, 저작권 침해 등의 내용이 포함되지 않도록 해주세요.\n" +
                    "  비속어가 포함되지 않도록 주의해주세요.\n" +
                    "  사용한 서비스와 작성한 리뷰가 일치하는지 확인해주세요.\n" +
                    "  개인정보가 포함되지 않도록 주의해주세요.\n"

        binding.register.setOnClickListener {
            var view1:View = binding.listView.getChildAt(0)
            var answer1 = view1.findViewById<EditText>(R.id.answer).text.toString()
            var view2:View = binding.listView.getChildAt(1)
            var answer2 = view2.findViewById<EditText>(R.id.answer).text.toString()
            mainActivity!!.mainBundle.putString("answer1",answer1)
            mainActivity!!.mainBundle.putString("answer2",answer2)
            mainActivity!!.mainBundle.putString("id","0000")
            var view3:View = binding.listView.getChildAt(2)
            var answer3 = view3.findViewById<EditText>(R.id.answer).text.toString()
            mainActivity!!.mainBundle.putString("answer3",answer3)
            mainActivity!!.onChangeFragment(0,mainActivity!!.mainBundle)
        }
        binding.back.setOnClickListener{
            mainActivity!!.onChangeFragment(0,mainActivity!!.mainBundle)
        }

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
            val answer = view?.findViewById<EditText>(R.id.answer)
            question!!.setText(item.question)
            answer!!.setText(item.answer)
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