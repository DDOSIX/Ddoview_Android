package com.example.ddoview.listview
//review에 대한 댓글, DetailReviewFragment
class ReviewCommentItem(var id: String, var contents: String) {
    override fun toString(): String {
        return "ReviewCommentItem(id='$id', contents='$contents')"
    }
}