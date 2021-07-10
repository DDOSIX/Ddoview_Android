package com.example.ddoview

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.renderscript.ScriptGroup
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.ddoview.databinding.ReviewFragmentBinding
import com.example.ddoview.listview.AdvertisementItem
import com.example.ddoview.listview.ReviewItem

class ReviewFragment: Fragment() {

    //reviewPage,
    private var _binding: ReviewFragmentBinding? = null
    private val binding get() = _binding!!
    private var bundle:Bundle? = null
    private lateinit var adapter: ReviewAdapter
    private var mainActivity: MainActivity? = null

    //뒤로가기
    private lateinit var callBack:OnBackPressedCallback

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                //메인 화면으로 이동
                val intent = Intent(context,MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        //뒤로 버튼을 누를 경우 뒤로 이동
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)

        mainActivity = activity as MainActivity
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ReviewFragmentBinding.inflate(inflater,container,false)

        val rootView = binding.root


        arguments?.let {
            val name = it.getString("company")
            binding.company.setText(name)
        }

        //리뷰 상세 내용 쓰기
        //binding.contents.text

        //리뷰 리스트
        adapter = ReviewAdapter()
        adapter.addItem(ReviewItem("id:leehy","좋아요"))
        adapter.addItem(ReviewItem("id:gdg","별로에요"))
        binding.listView.adapter = adapter

        //리뷰 작성 페이지로 이동하기, ReviewWriteFragment
        binding.write.setOnClickListener {
            mainActivity!!.onChangeFragment(1, mainActivity!!.mainBundle)
        }

        binding.listView.setOnItemClickListener { parent, view, position, id ->
            val item = adapter.getItem(position) as ReviewItem

            //reviewFragment로 이동하기, id 넘기기
            mainActivity!!.mainBundle.putString("id",item.title)
            mainActivity!!.onChangeFragment(3,mainActivity!!.mainBundle)
        }
        return rootView
    }

    inner class ReviewAdapter : BaseAdapter() {
        var items: ArrayList<ReviewItem> = ArrayList<ReviewItem>()

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
        fun addItem(item: ReviewItem)
        {
            items.add(item)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            if(view == null)
            {
                view = LayoutInflater.from(parent?.context).inflate(
                        R.layout.review_item,
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


    override fun onDetach() {
        super.onDetach()
        _binding = null
        bundle = null
        callBack.remove()
        mainActivity = null
    }
}