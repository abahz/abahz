package com.abahz.abahz

import android.Manifest
import android.annotation.SuppressLint
import android.app.AlertDialog.Builder
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.abahz.abahz.databases.MonApi
import com.abahz.abahz.databases.MyUtils
import com.abahz.abahz.databinding.ActivityMainBinding
import com.abahz.abahz.models.Users

class MainActivity : AppCompatActivity() {
    private val binding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val api by lazy { MonApi(this) }
    private var arePermissionsGranted = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        enableEdgeToEdge()

        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        val permissions = mutableListOf<String>()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU -> {
                permissions.add(Manifest.permission.READ_MEDIA_IMAGES)
                permissions.add(Manifest.permission.BLUETOOTH_SCAN)
                permissions.add(Manifest.permission.BLUETOOTH_CONNECT)
                permissions.add(Manifest.permission.POST_NOTIFICATIONS)
            }
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q -> {
                permissions.add(Manifest.permission.BLUETOOTH)
                permissions.add(Manifest.permission.BLUETOOTH_ADMIN)
                permissions.add(Manifest.permission.ACCESS_MEDIA_LOCATION)
            }
            else -> {
                permissions.add(Manifest.permission.INTERNET)
                permissions.add(Manifest.permission.ACCESS_NETWORK_STATE)
                permissions.add(Manifest.permission.READ_EXTERNAL_STORAGE)
                permissions.add(Manifest.permission.WRITE_EXTERNAL_STORAGE)
            }
        }

        binding.button.setOnClickListener {
            if (arePermissionsGranted) loginOrCreateAccount() else launcher.launch(permissions.toTypedArray())

        }
    }

    @SuppressLint("SetTextI18n")
    private val launcher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions()) {
        var allGranted = true
        for (granted in it.values) { allGranted = allGranted && granted }
        if (allGranted) {
            arePermissionsGranted = true
            Toast.makeText(this, "Bien configurer", Toast.LENGTH_SHORT).show()
        } else {
            showPermissionRationaleDialog(it.keys.toList())
         }
    }

    private fun showPermissionRationaleDialog(permissions: List<String>) {
        if (shouldShowRequestPermissionRationale(permissions)) {
            AlertDialog.Builder(this)
                .setTitle("Permissions requises")
                .setMessage("Cette application a besoin de certaines permissions pour fonctionner correctement. Veuillez les accorder.")
                .setPositiveButton("OK") { _, _ -> launcher.launch(permissions.toTypedArray()) }
                .setNegativeButton("Non, merci") { _, _ ->
                    Toast.makeText(this, "Vous pouvez activer les permissions dans les paramètres plus tard.", Toast.LENGTH_SHORT).show()
                }
                .show()
        } else {
            Toast.makeText(this, "Veuillez activer les permissions dans les paramètres.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun shouldShowRequestPermissionRationale(permissions: List<String>): Boolean {
        return permissions.any { ActivityCompat.shouldShowRequestPermissionRationale(this, it) }
    }

    private fun loginOrCreateAccount() {
        MyUtils.myProgress(true, binding.view1,binding.view2)
        val phone = binding.phone.text.toString().trim()
        val pass = binding.pass.text.toString().trim()
        if (phone.isEmpty() || pass.isEmpty()) {
            showMessage("Les champs vides !", "Veuillez remplir tous les champs.")
            return
        }
        if (pass.length <= 4) {
            showMessage("Mot de passe incorrect", "Le mot de passe doit contenir au moins 4 caractères.")
            return
        }
        MyUtils.useRef(phone).addSnapshotListener{ snapshot, error ->
            if (error != null)return@addSnapshotListener
            if (snapshot != null && snapshot.exists()) {
                val user = snapshot.toObject(Users::class.java)
                if (user?.pass != pass) {
                    showMessage("Mot de passe incorrect", "Veuillez saisir un mot de passe valide")
                    return@addSnapshotListener
                }
                api.apply {
                    addPass(pass)
                    addPhone(phone)
                    addMode(MyUtils.MODE_VIRTUAL) }
                startActivity(Intent(this, HomeActivity::class.java))
            }else{
                MyUtils.myProgress(false,binding.view1,binding.view2)
                Builder(this@MainActivity).apply {
                    setTitle("Nouveau numéro detecté")
                    setMessage("Corriger le numéro ou créer un compte ?")
                    setPositiveButton("Créer"){dialog,_-> dialog.dismiss()
                        val users = Users(
                            phone = phone,
                            pass = pass,
                            mode = MyUtils.MODE_VIRTUAL)
                        MyUtils.useRef(phone).set(users).addOnSuccessListener{
                            startActivity(Intent(this@MainActivity, HomeActivity::class.java))
                            api.apply {
                                addPass(pass)
                                addPhone(phone)
                                addMode(MyUtils.MODE_VIRTUAL) }
                            finish()
                        }
                    }
                    setNegativeButton("Corriger"){dialog,_-> dialog.dismiss() }
                }.create().show()
            }
        }
    }
    private fun showMessage(title: String, message: String){
        MyUtils.myProgress(false, binding.view1,binding.view2)
        MyUtils.alert(this,title,message)
    }

    override fun onStart() {
        super.onStart()
        if (api.getPhone()!=null){
            startActivity(Intent(this, HomeActivity::class.java))
        }
    }
}

