package com.example.ddoview

import android.app.Activity
import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import com.example.ddoview.databinding.DetailReviewFragmentBinding
import com.example.ddoview.data.ReviewCommentItem

class DetailReviewFragment: Fragment() {

    //reviewPage,
    private var _binding: DetailReviewFragmentBinding? = null
    private val binding get() = _binding!!
    private var bundle: Bundle? = null
    private lateinit var adapter: CommentAdapter
    private lateinit var comment: String
    private var mainActivity: MainActivity? = null

    //뒤로가기
    private lateinit var callBack: OnBackPressedCallback

    override fun onAttach(activity: Activity) {
        super.onAttach(activity)

        mainActivity = activity as MainActivity

        callBack = object : OnBackPressedCallback(true){
            override fun handleOnBackPressed() {
                mainActivity!!.onChangeFragment(0,mainActivity!!.mainBundle)
            }
        }

        //ReviewFragment로 이동
        requireActivity().onBackPressedDispatcher.addCallback(this, callBack)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        _binding = DetailReviewFragmentBinding.inflate(inflater,container,false)

        val rootView = binding.root


        //리뷰를 쓴 아이디와, 회사이름을 bundle에서 받아와 등록, 데이터에서 내용을 call을 하여 setText를 해야 할 필요가 있음
        //detail_review_fragment.xml 참조
        arguments?.let {
            //리뷰 작성자 id와 리뷰를 응답한 회사 이름 가져온 후 set
            val id = it.getString("id")
            binding.id.setText(id)

            val company = it.getString("company")
            binding.company.setText(company)

            val category = it.getString("category")
            binding.category.setText(category)

            val answer1 = it.getString("answer1")
            binding.answer.setText(answer1)

            val answer2 = it.getString("answer2")
            binding.answer2.setText(answer2)
        }

        //리뷰 상세 내용 쓰기
        //binding.contents.text

        //리뷰에 대한 코멘트 (댓글)
        adapter = CommentAdapter()
        /*adapter.addItem(ReviewCommentItem("id:leehy","리뷰 작성 잘 해주셨어요"))
        adapter.addItem(ReviewCommentItem("id:gdg","별로에요"))*/
        binding.listView.adapter = adapter


        //새로운 댓글 등록
        binding.count!!.text = adapter.count.toString().plus("개")
        binding.add.setOnClickListener {
            AddCommentMaker()
        }
        return rootView
    }

    //새로운 코멘트 등록하기, alert dialog 실행
    fun AddCommentMaker()
    {
        var builder = AlertDialog.Builder(activity)

        builder.setTitle("리뷰에 댓글 달기")
        var editText = EditText(context)
        builder.setView(editText)

        builder.setPositiveButton("등록"){ dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(context,"등록 완료",Toast.LENGTH_SHORT).show()

            comment = editText.text.toString()

            //작성된 내용을 listview에 등록, id는 현재 로그인한 아이디를 넣어야함. ("임시 id")에 데이터
            adapter.addItem(ReviewCommentItem("임시 id",comment))
            binding.listView.adapter = adapter
            binding.count.text = adapter.count.toString().plus("개")
        }

        builder.setNegativeButton("취소"){ dialogInterface: DialogInterface, i: Int ->
            Toast.makeText(context,"등록 취소",Toast.LENGTH_SHORT).show()
        }

        var alertDialog = builder.create()
        alertDialog.show()
    }


    inner class CommentAdapter : BaseAdapter() {
        var items: ArrayList<ReviewCommentItem> = ArrayList<ReviewCommentItem>()

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
        fun addItem(item: ReviewCommentItem)
        {
            items.add(item)
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
            var view = convertView
            if(view == null)
            {
                view = LayoutInflater.from(parent?.context).inflate(
                        R.layout.review_comment_item,
                        parent,
                        false
                )
            }
            val item = items.get(position)
            val id = view?.findViewById<TextView>(R.id.id)
            val contents = view?.findViewById<TextView>(R.id.contents)
            id!!.setText(item.id)
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