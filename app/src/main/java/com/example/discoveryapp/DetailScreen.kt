package com.example.discoveryapp

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.discoveryapp.databinding.DetailScreenBinding

class DetailScreen() : Fragment() {

    var title : String? = "No Title Present"
    var posterImage : String? = null
    var releaseDate : String? = "Nothing to Display"

    lateinit var binding : DetailScreenBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DetailScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
           Log.d("log"," detail screen opened")
                accessData()
        clickListener()

    }

    private fun clickListener() {

        binding.button.setOnClickListener{
            var findFrag = requireActivity().supportFragmentManager.findFragmentByTag("home")

            if(findFrag?.tag == "home"){
                requireActivity().supportFragmentManager.popBackStack("home",0)
            }
            else{
                requireActivity().supportFragmentManager.beginTransaction().replace(R.id.fragmentContainerView,
                    Home()).addToBackStack("home").commit()
            }
        }
    }

    fun accessData(){
        if(arguments != null){
            title = arguments?.getString("title")
            posterImage = arguments?.getString("posterImage")
            releaseDate = arguments?.getString("releaseDate")

        }

        binding.titleTextView.text = title
      Glide.with(this)
          .load(posterImage)
          .into(binding.posterImageView)
        binding.dateTextView.text = releaseDate

    }
}