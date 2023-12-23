package com.example.homework_2

import android.content.Intent
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import com.example.homework_2.databinding.ElemItemBinding
import com.example.homework_2.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(MainFragment())

        val mainLayout = findViewById<View>(R.id.mainLayout) as ConstraintLayout

        if (!haveNetwork()) {
            val builder = AlertDialog.Builder(this@MainActivity)
            builder.setTitle("Network error")
            builder.setMessage("Internet not found!")
            builder.setNegativeButton("Okay") {dialogInerface, i-> finish()}
            builder.setPositiveButton("Try again") {dialogInterface, i-> restartApp()}
            builder.show()

        }
    }

    private fun haveNetwork(): Boolean {
        var haveWifi = false
        var haveMobile = false
        val connectivityManager = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfos = connectivityManager.allNetworkInfo
        for (info in networkInfos) {
            if (info.typeName.equals("WIFI", ignoreCase = true)) if (info.isConnected) haveWifi = true
            if (info.typeName.equals("MOBILE", ignoreCase = true)) if (info.isConnected) haveMobile = true
        }
        return haveMobile || haveWifi
    }

    private fun restartApp() {
        startActivity(Intent(applicationContext, MainActivity::class.java))
        finish()
    }

    private fun replaceFragment(mainFragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, mainFragment)
        fragmentTransaction.commit()

    }


}