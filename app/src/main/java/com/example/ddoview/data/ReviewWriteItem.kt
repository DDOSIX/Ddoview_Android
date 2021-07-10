package com.example.ddoview.data
//설문지에 대한 답변을 쓰기, from review write fragment
class ReviewWriteItem(var question: String,var answer: String) {
    override fun toString(): String {
        return "ReviewWriteItem(question='$question', answer='$answer')"
    }
}