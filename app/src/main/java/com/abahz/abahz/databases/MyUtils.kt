package com.abahz.abahz.databases

import android.annotation.SuppressLint
import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import com.abahz.abahz.fragments.ChatFragment
import com.abahz.abahz.fragments.HomeFragment
import com.abahz.abahz.fragments.MoreFragment
import com.abahz.abahz.fragments.NoteFragment
import com.google.firebase.firestore.FirebaseFirestore
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object MyUtils {
    val fragmentLists = arrayOf(HomeFragment(), ChatFragment(), NoteFragment(), MoreFragment())

    const val MODE_PHYSIC = "PHYSIC"
    const val MODE_VIRTUAL = "VIRTUAL"

    private fun db() = FirebaseFirestore.getInstance()

    fun useRef(phone: String) = db().collection("Users").document(phone)

    fun invoice(phone: String) = db().collection("Invoices").document(phone)
        .collection("Invoices").document("lastInvoice")

    fun prodRef(phone: String) = db().collection("Products")
        .document(phone).collection("Products")

    fun cartRef(phone: String) = db().collection("Carts")
        .document(phone).collection("Carts")

    fun ordRef(phone: String)= db().collection("Orders")
        .document(phone).collection("Orders")

    fun itemRef(phone: String) = db().collection("Items")
        .document(phone).collection("Items")

    fun noteRef(phone: String) = db().collection("Notes")
        .document(phone).collection("Notes")


    fun myProgress(isProgress: Boolean, btn: TextView, pg: ProgressBar) {
        if (isProgress) {
            btn.visibility = View.GONE
            pg.visibility = View.VISIBLE
        } else {
            btn.visibility = View.VISIBLE
            pg.visibility = View.GONE
        }
    }
    fun alert(context: Context?, title: String = "Alerte", message: String = "Message") {
        if (context is Activity && !context.isFinishing) {
            AlertDialog.Builder(context).apply {
                setTitle(title)
                setMessage(message)
                setPositiveButton("Oui") { dialog, _ -> dialog.dismiss() }
            }.create().show()
        }
    }
    @Suppress("DEPRECATION")
    fun isConnect(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo= connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    @SuppressLint("SimpleDateFormat", "ConstantLocale")
    val DATE_COMPLETE: String = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())

    const val SEND = "SEND"
    const val ACCEPTED = "ACCEPTED"
    const val REFUSED = "REFUSED"
    const val NATURAL = "NATURAL"

    @SuppressLint("ConstantLocale")
    var MONTH_ONLY: String = SimpleDateFormat("MM", Locale.getDefault()).format(Date())

    fun rapport() = when(MONTH_ONLY){
        "01"-> "Jan."
        "02"-> "Fev."
        "03"-> "Mrs."
        "04"-> "Avr."
        "05"-> "Mai"
        "06"-> "Jun."
        "07"-> "Jul."
        "08"-> "Aug."
        "09"-> "Sep."
        "10"-> "Oct."
        "11"-> "Nov."
        "12"-> "Déc."
        else -> { " " }
    }

}