package com.example.discoveryapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.discoveryapp.databinding.EachItemBinding

var value = 0
class MoviesAndShowsAdapter(var List: ArrayList<Any>, var listener: SetOnItemClickListener) : RecyclerView.Adapter<MoviesAndShowsAdapter.CustomViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        var binding = EachItemBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CustomViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var currentItem = List[position]

        holder.bind(currentItem)
        holder.binding.constraintLayout.setOnClickListener{
            val pos = holder.adapterPosition
            if(pos != RecyclerView.NO_POSITION){
                listener.onClickListener(pos)
            }
        }


    }

    override fun getItemCount(): Int {
      return List.size
    }

    inner  class CustomViewHolder(var binding : EachItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(information: Any) {

            when(information){
                is Movies ->
                {
                    value = 0
                    binding.title.text = information.title
                    binding.sourceName.text = information.sourceName
                    binding.releaseDate.text = information.releasedDate
                    Glide.with(binding.root.context)
                        .load(information.posterUrl)
                        .placeholder(R.drawable.load)
//                    .transform(RoundedCorners(10))
                        .into(binding.imageView)
                }
                is Series ->{
                     value = 1

                    binding.title.text = information.title
                    binding.sourceName.text = information.sourceName
                    binding.releaseDate.text = information.releasedDate
                    Glide.with(binding.root.context)
                        .load(information.posterUrl)
                        .placeholder(R.drawable.loading)
//                    .transform(RoundedCorners(10))
                        .into(binding.imageView)
                }
            }


        }
    }
}