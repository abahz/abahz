package com.abahz.abahz.models

data class Users(
    val shop: String = "",
    val phone: String = "",
    val pass: String = "",
    val address: String = "",
    val devise: String = "",
    val status: String = "",
    val manager: String = "",
    val rccm: String = "",
    val idnat: String = "",
    val impot: String = "",
    val mode: String = "",
    val members: MutableList<String> = mutableListOf()
) {
    constructor() : this("", "", "", "", "", "", "", "", "", "", "", mutableListOf())
}
