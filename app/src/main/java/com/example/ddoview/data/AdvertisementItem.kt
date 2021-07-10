package com.example.ddoview.data
//메인 화면 listview, 베타 테스터를 구함, MainActivity
class AdvertisementItem(var title: String, var category: String, var contents: String) {
    override fun toString(): String {
        return "AdvertisementItem(title='$title', contents='$contents')"
    }
}