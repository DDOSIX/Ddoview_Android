package com.example.ddoview.data
//베타 테스터 후기.. review item
class ReviewItem(var title: String, var contents: String) {

    //date를 추가적으로 넣어주어야 함
    override fun toString(): String {
        return "ReviewItem(title='$title', contents='$contents')"
    }
}