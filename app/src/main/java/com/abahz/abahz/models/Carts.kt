package com.abahz.abahz.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable
@Entity
data class Carts(
    @PrimaryKey(autoGenerate = false)
    val name:String ="",
    val image:String ="",
    val unity:String ="",
    var price:Double = 0.0,
    var total:Double = 0.0,
    var qty:Int = 0
) : Serializable {
    constructor() : this("", "", "", 0.0, 0.0, 0)
}
