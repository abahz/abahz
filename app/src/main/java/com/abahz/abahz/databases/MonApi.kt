package com.abahz.abahz.databases

import android.content.Context
import android.content.SharedPreferences

class MonApi(context: Context) {
    private val preference: SharedPreferences = context.getSharedPreferences("prefs", Context.MODE_PRIVATE)

    fun addPhone(phone:String){
        val currentPhone = getPhone()
        if (currentPhone!=phone){
            preference.edit().putString(PHONE,phone).apply()
        }
    }
    fun getPhone() = preference.getString(PHONE,"")

    fun addDevise(devise:String){
        val current = getDevise()
        if (current!=devise){
            preference.edit().putString(DEVISE,devise).apply()
        }
    }
    fun getDevise() = preference.getString(DEVISE,"").toString()

    fun addShop(shop:String){
        val current = getShop()
        if (current!=shop){
            preference.edit().putString(SHOP,shop).apply()
        }
    }
    fun getShop() = preference.getString(SHOP,"").toString()

    fun addAddress(address:String){
        val current = getAddress()
        if (current!=address){
            preference.edit().putString(ADDRESS,address).apply()
        }
    }
    fun getAddress() = preference.getString(ADDRESS,"").toString()

    fun addManager(manager:String){
        val current = getManager()
        if (current!=manager){
            preference .edit().putString(MANAGER,manager).apply()
        }
    }
    fun getManager() = preference.getString(MANAGER,"").toString()

    fun addName(name:String){
        val current = getName()
        if (current!=name){
            preference .edit().putString(NAME,name).apply()
        }
    }

    fun getName() = preference.getString(NAME,"").toString()

    fun addPass(pass:String){
        val current = getPass()
        if (current!=pass){
            preference .edit().putString(PASS,pass).apply()
        }
    }
    fun getPass() = preference.getString(PASS,"").toString()

    fun addRccm(rccm:String){
        val current = getRccm()
        if (current!=rccm){
            preference .edit().putString(RCCM,rccm).apply()
        }
    }
    fun getRccm() = preference.getString(RCCM,"").toString()

    fun addIdNat(idnat:String){
        val current = getIdNat()
        if (current!=idnat){
            preference .edit().putString(IDNAT,idnat).apply()
        }
    }
    fun getIdNat() = preference.getString(IDNAT,"").toString()


    fun addImpot(impot:String){
        val current = getImpot()
        if (current!=impot){
            preference .edit().putString(IMPOT,impot).apply()
        }
    }
    fun getImpot() = preference.getString(IMPOT,"").toString()

    fun addMode(mode:String){
        val current = getImpot()
        if (current!=mode){
            preference .edit().putString(MODE,mode).apply()
        }
    }
    fun getMode() = preference.getString(MODE,"").toString()


    companion object{
        const val PHONE = "phone"
        const val TYPE = "type"
        const val DEVISE = "devise"
        const val SHOP = "shop"
        const val ADDRESS = "address"
        const val MANAGER = "manager"
        const val NAME = "name"
        const val PASS = "pass"
        const val RCCM = "rccm"
        const val IDNAT = "idnat"
        const val IMPOT = "impot"
        const val MODE = "mode"
    }
}