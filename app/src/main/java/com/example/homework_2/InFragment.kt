package com.example.homework_2

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.bumptech.glide.Glide


class InFragment : Fragment() {

    lateinit var bindingClass: InFragment
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_in, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        val img = view.findViewById<ImageView>(R.id.in_img)
        var url: String? = "a"

        arguments?.let {
            url = it.getString("url")
        }



        Glide.with(view)
            .load(url)
            .into(img)
    }

    companion object {

        @JvmStatic
        fun newInstance(elem: DataObject) =
            InFragment().apply {
                arguments = Bundle().apply {
                    putString("url", elem.images.ogImage.url)
                }
            }


    }
}