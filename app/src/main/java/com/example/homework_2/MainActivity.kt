package com.example.homework_2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.homework_2.databinding.ElemItemBinding
import com.example.homework_2.databinding.FragmentMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var bindingClass: ElemItemBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        replaceFragment(MainFragment())

        bindingClass = ElemItemBinding.inflate(layoutInflater)

        /*bindingClass.img.setOnClickListener() {
            replaceFragment(InFragment.newInstance())
        } ????????? */

    }

    private fun replaceFragment(mainFragment: Fragment) {

        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout, mainFragment)
        fragmentTransaction.commit()

    }


}