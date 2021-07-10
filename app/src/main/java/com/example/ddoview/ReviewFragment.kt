package com.example.ddoview

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.ddoview.data.ReviewAdapter
import com.example.ddoview.data.ReviewItem
import com.example.ddoview.databinding.ReviewFragmentBinding

class ReviewFragment: Fragment() {

    //reviewPage,
    private var _binding: ReviewFragmentBinding? = null
    private val binding get() = _binding!!
    private var bundle:Bundle? = null
    //private lateinit var adapter: ReviewAdapter
    private var mainActivity: MainActivity? = null

    //뒤로가기
    private lateinit var callBack:OnBackPressedCallback

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                //메인 화면으로 이동
                val intent = Intent(context, MainActivity::class.java)
                startActivity(intent)
                requireActivity().finish()
            }
        }
        //뒤로 버튼을 누를 경우 뒤로 이동
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)

        mainActivity = activity as MainActivity
    }


    @SuppressLint("ClickableViewAccessibility")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = ReviewFragmentBinding.inflate(inflater, container, false)

        val rootView = binding.root


        arguments?.let {
            val name = it.getString("company")
            binding.company.setText(name)
            val category = it.getString("category")
            binding.category.setText(category)
            val contents = it.getString("contents")
            binding.intro.setText(contents)
        }

        //리뷰 이미지 등록
        //binding.image

        //리뷰 리스트
        //ReviewItem에 date 관련 string을 추가해야 함.
        val list = ArrayList<ReviewItem>()
        list.add(ReviewItem("id gdg","평가: 좋아요"))
        list.add(ReviewItem("id 디프만","평가: 싫어요"))
        binding.listView.layoutManager = LinearLayoutManager(context).apply {
            isAutoMeasureEnabled//onmeasured
        }
        binding.listView.adapter = ReviewAdapter(list,mainActivity!!)

        //리뷰 작성 페이지로 이동하기, ReviewWriteFragment
        binding.write.setOnClickListener {
            mainActivity!!.onChangeFragment(1, mainActivity!!.mainBundle)
        }

        //기업 소개
        binding.companyIntro.setText("스타트업A는, 나와 내 가족이 사고 싶은 상품을 판매합니다. 물류 혁신을 통해 최상의 품질로 전해드리며, 같은 품질에서 최선의 가격을 제공하고 고객의 행복을 먼저 생각합니다. 지속 가능한 유통을 실현하며 함께 변화를 주도하고 새로운 기회를 만들고자 합니다.\n")
        //서비스 소개
        binding.serviceIntro.setText("<신선식품 새벽배송 할인 프로모션>\n" +
                " 1.회원가입 후 신선식품 새벽배송 할인 쿠폰을 받습니다.\n" +
                " 2.프로모션 해당 제품을 장바구니에 담기 후 주문합니다.\n" +
                " 3.배송완료 후 해당 앱 내에 리뷰를 올려주세요.\n" +
                " 4.프로모션 체험 완료 후, “또뷰”에 리뷰해주세요.\n")

        return rootView
    }

    /*inner class ReviewAdapter : BaseAdapter() {
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

    }*/


    override fun onDetach() {
        super.onDetach()
        _binding = null
        bundle = null
        callBack.remove()
        mainActivity = null
    }
}