package com.example.discoveryapp

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.discoveryapp.databinding.HomeScreenBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Home : Fragment(), SetOnItemClickListener {

    lateinit var binding: HomeScreenBinding
    lateinit var mContext : Context
    lateinit var infoList :List<Release>
     var mActivity : Activity? = null
     var listOfMovies : ArrayList<Movies> = arrayListOf()
     var listOfSeries : ArrayList<Series> = arrayListOf()
    var selectedType : Boolean = false

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext= context
        mActivity = context as Activity
    }


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = HomeScreenBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.shimmerFrameLayout.visibility = View.VISIBLE
        binding.movieRecyclerView.visibility = View.GONE

        fetchFilteredData()

    }

    private fun fetchFilteredData() {

        //according to documentation

        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://api.watchmode.com/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getMoviesAndShows(getString(R.string.apiKey))//receiving the data and checks type
        retrofitData.enqueue(object :Callback<MoviesAndShows> {


            override fun onResponse(call: Call<MoviesAndShows>, response: Response<MoviesAndShows>) {
                if (response.isSuccessful) {
                    binding.shimmerFrameLayout.stopShimmer()
                    binding.shimmerFrameLayout.visibility = View.GONE
                    binding.movieRecyclerView.visibility = View.VISIBLE

                    val responseBody = response.body()
                    if (responseBody != null) {
                        infoList = responseBody.releases

                        initializeArray()

                        binding.movieRecyclerView.layoutManager = LinearLayoutManager(requireContext())

                        // Set default adapter (Movies)
                        binding.movieRecyclerView.adapter = MoviesAndShowsAdapter(ArrayList<Any>(listOfMovies), this@Home)

                        // Handle toggle switching
                        binding.toggleButton.setOnCheckedChangeListener { _, isChecked ->
                            selectedType = isChecked
                            if (!selectedType) {
                                binding.toggleTextView.text = getString(R.string.Movies)
                                binding.movieRecyclerView.adapter = MoviesAndShowsAdapter(ArrayList<Any>(listOfMovies), this@Home)
                            } else {
                                binding.toggleTextView.text = getString(R.string.series)
                                binding.movieRecyclerView.adapter = MoviesAndShowsAdapter(ArrayList<Any>(listOfSeries), this@Home)
                            }
                        }

                    } else {
                        Log.d("API", "Response body is null")
                    }

                } else {
                    Log.d("API", "API call not successful. Code: ${response.code()}")
                }
            }



            override fun onFailure(call: Call<MoviesAndShows?>, t: Throwable) {
                //if api call -  fails
                Log.d("failure"," failure message " + t.message)

            }


            private fun initializeArray() {
                for( information in infoList){

                    if(information.type == "movie" || information.type == "tv_movie"){
                        listOfMovies.add(Movies(information.title,information.poster_url,information.source_name,information.type,information.source_release_date))
                    }
                    if(information.type == "tv_series" || information.type == "tv_miniseries")
                    {
                        listOfSeries.add(Series(information.title,information.poster_url,information.source_name,information.type,information.source_release_date))
                    }


                }

            }
        })
    }

    override fun onClickListener(position: Int) {
       when(value){
              0->{
            Toast.makeText(mContext, listOfMovies[position].title, Toast.LENGTH_SHORT).show()
            val bundle = Bundle()
            val detailScreen = DetailScreen()
            bundle.putString("title",listOfMovies[position].title)
            bundle.putString("releaseDate",listOfMovies[position].releasedDate)
            bundle.putString("posterImage", listOfMovies[position].posterUrl)

            detailScreen.arguments = bundle
            Log.d("log","nav")
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainerView, detailScreen)
                .addToBackStack("detail")
                .commit()
        }
           1 ->{
               Toast.makeText(mContext, listOfSeries[position].title, Toast.LENGTH_SHORT).show()
               val bundle = Bundle()
               val detailScreen = DetailScreen()
               bundle.putString("title",listOfSeries[position].title)
               bundle.putString("releaseDate",listOfSeries[position].releasedDate)
               bundle.putString("posterImage", listOfSeries[position].posterUrl)

               detailScreen.arguments = bundle
               Log.d("log","nav")
               requireActivity().supportFragmentManager.beginTransaction()
                   .replace(R.id.fragmentContainerView, detailScreen)
                   .addToBackStack("detail")
                   .commit()

           }           }


    }


}
