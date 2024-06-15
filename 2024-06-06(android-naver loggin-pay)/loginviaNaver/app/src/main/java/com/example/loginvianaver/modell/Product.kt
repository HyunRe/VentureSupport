package com.example.loginvianaver.modell

data class Product(
    val id: Long?, //상품 일련번호
    val name: String?, //상품명
    //val description: String?, //상품 상세정보
    val price: Double?, //가격
    val quantity: Int?, //개수
    val user: User? //상품보유자
)