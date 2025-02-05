package com.abahz.abahz.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class Products(
    @PrimaryKey(autoGenerate = false)
    val name:String ="",
    val desc:String ="",
    val unity:String ="",
    var price:Double =0.0,
    val total:Double =0.0,
    var qty:Int =0,
    val phone:String ="",
    val sync:String ="",
    val images : MutableList<PhotoModel>
): Serializable {
    constructor():this("", "","", 0.0, 0.0, 0, "","", mutableListOf())
}