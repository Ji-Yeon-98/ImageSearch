package com.example.imagesearch.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.imagesearch.MainActivity
import com.example.imagesearch.R
import com.example.imagesearch.data.ImageDataModel
import com.example.imagesearch.databinding.RvItemBinding

class MyAdapter(
    private val onClickItem: (Int, ImageDataModel) -> Unit,
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    private val list = ArrayList<ImageDataModel>()

    fun addItems(items: List<ImageDataModel>) {
        list.addAll(items)
        notifyDataSetChanged()
    }

    fun ItemsClear(){
        list.clear()
        notifyDataSetChanged()
    }


    fun modifyItem(
        position: Int?,
        imageDataModel: ImageDataModel?
    ) {
        if (position == null || imageDataModel == null) {
            return
        }
        list[position] = imageDataModel
        notifyItemChanged(position)
    }

    fun removeItem(position: Int, imageDataModel: ImageDataModel?){
        list.remove(imageDataModel)
        Log.d("removedlist", list.toString())
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            RvItemBinding.inflate(LayoutInflater.from(parent.context)),
            onClickItem
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = list[position]
        holder.bind(item)
    }

    class ViewHolder(
        private val binding: RvItemBinding,
        private val onClickItem: (Int, ImageDataModel) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(item: ImageDataModel) = with(binding) {

            Glide.with(root.context)
                .load(item.img)
                .into(rvImg)

            rvTitle.setText(item.title)
            rvDatetime.setText(item.dateTime.substring(0 until 10))

            rvHeart.setOnClickListener {
                onClickItem(
                    adapterPosition,
                    item
                )
            }

            if(item.heart){
                rvHeart.setImageResource(R.drawable.full_heart)

            }else{
                rvHeart.setImageResource(R.drawable.heart)
            }

        }
    }

}