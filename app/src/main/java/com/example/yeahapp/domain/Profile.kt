package com.example.yeahapp.domain

data class Profile (
    val firstName: String,
    val secondName: String,
){
//    val fullName: String = this.firstName + this.secondName
    val fullName: String = "$firstName $secondName"
}