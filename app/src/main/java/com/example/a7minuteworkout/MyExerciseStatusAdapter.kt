package com.example.a7minuteworkout

import android.annotation.SuppressLint
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.a7minuteworkout.databinding.ItemExerciseStatusBinding
import androidx.core.graphics.toColorInt

class MyExerciseStatusAdapter(val items: ArrayList<ExerciseModel>) :
    RecyclerView.Adapter<MyExerciseStatusAdapter.MyExerciseStatusHolder>() {

    class MyExerciseStatusHolder(binding: ItemExerciseStatusBinding) :
        ViewHolder(binding.root) {
        val tvItem = binding.tvItem

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MyExerciseStatusHolder {
        return MyExerciseStatusHolder(
            ItemExerciseStatusBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    @SuppressLint("ResourceAsColor")
    override fun onBindViewHolder(
        holder: MyExerciseStatusHolder,
        position: Int
    ) {
        val model: ExerciseModel = items[position]
        holder.tvItem.text = model.getId().toString()
        when {
            model.getIsSelected() -> {
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.tvItem.context,
                    R.drawable.item_circular_color_white_background
                )
                holder.tvItem
                    .setTextColor("#212121".toColorInt())
            }

            model.getIsCompleted() -> {
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.tvItem.context,
                    R.drawable.item_circular_color_white_background
                )
                holder.tvItem
                    .setTextColor(R.color.colorAccent)

            }

            else -> {
                holder.tvItem.background = ContextCompat.getDrawable(
                    holder.tvItem.context,
                    R.drawable.item_circular_color_gray_background
                )
                holder.tvItem
                    .setTextColor("#212121".toColorInt())

            }
        }
    }

    override fun getItemCount(): Int = items.size


}