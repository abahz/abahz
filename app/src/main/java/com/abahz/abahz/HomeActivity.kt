package com.abahz.abahz

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.abahz.abahz.adapters.Adapters
import com.abahz.abahz.databases.MonApi
import com.abahz.abahz.databases.MyUtils
import com.abahz.abahz.databinding.ActivityHomeBinding
import com.google.android.material.tabs.TabLayoutMediator

class HomeActivity : AppCompatActivity() {
    private val binding by lazy { ActivityHomeBinding.inflate(layoutInflater) }
    private val adapter by lazy { Adapters(this) }
    private lateinit var api: MonApi

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        binding.viewPager.adapter = adapter
        api = MonApi(this)
        binding.userMode.setOnClickListener {
            AlertDialog.Builder(this@HomeActivity)
                .setItems(arrayOf("B. Physique", "B. Virtuelle")) { dialog, witch ->
                    dialog.dismiss()
                    if (witch == 0) getPhysicMode() else getVirtualMode()
                }.create().show()
        }

        getBottomIcons()


        binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                when (position) {
                    0 -> isVisibleToolbar(true)
                    1 -> isVisibleToolbar(true)
                    2 -> isVisibleToolbar(true)
                    3 -> isVisibleToolbar(false)
                }
            }
        })
    }

    private fun getPhysicMode(){
        binding.userMode.text = "B. Physique"
        api.addMode(MyUtils.MODE_PHYSIC)
        getBottomIcons()
        val map = mapOf("mode" to MyUtils.MODE_PHYSIC)
        MyUtils.useRef(api.getPhone().toString()).update(map)
    }

    private fun getVirtualMode() {
        binding.userMode.text = "B. Virtuelle"
        api.addMode(MyUtils.MODE_VIRTUAL)
        getBottomIcons()
        val map = mapOf("mode" to MyUtils.MODE_VIRTUAL)
        MyUtils.useRef(api.getPhone().toString()).update(map)
    }
    fun isVisibleToolbar(isVisible: Boolean) {
        if (isVisible) {
            binding.toolbar01.visibility = View.VISIBLE
            binding.toolbar02.visibility = View.GONE
        } else {
            binding.toolbar01.visibility = View.GONE
            binding.toolbar02.visibility = View.VISIBLE
        }
    }
    fun getBottomIcons(){
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            when (position) {
                0 -> {
                    tab.text = "Acceuil"
                    tab.icon = getDrawable(R.drawable.round_home_24)
                }

                1 -> {
                    if (api.getMode()== MyUtils.MODE_VIRTUAL){
                        tab.text = "Chat"
                        tab.icon = getDrawable(R.drawable.ic_chat_24)
                    }else{
                        tab.text = "Facture"
                        tab.icon = getDrawable(R.drawable.round_pos_24)
                    }

                }

                2 -> {
                    if (api.getMode()== MyUtils.MODE_VIRTUAL){
                        tab.text = "Categories"
                        tab.icon = getDrawable(R.drawable.round_grid_view_24)
                    }else{
                        tab.text = "Depense"
                        tab.icon = getDrawable(R.drawable.round_wallet_24)
                    }
                }

                3 -> {
                    tab.text = "Menu"
                    tab.icon = getDrawable(R.drawable.round_menu_24)
                }
            }
        }.attach()
    }
}

