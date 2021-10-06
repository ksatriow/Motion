package com.satrio.motion.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.satrio.motion.R
import com.satrio.motion.data.entity.Cast
import com.satrio.motion.databinding.ItemCastBinding
import kotlinx.coroutines.ExperimentalCoroutinesApi
import com.satrio.motion.util.AppConstant

@ExperimentalCoroutinesApi
class CastAdapter(
    val context: Context,
    val castList: ArrayList<Cast>
) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val binding = ItemCastBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.item_cast, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        with(holder) {

            if (position == 0) {
                binding.spacingStart.visibility = View.VISIBLE
            } else if (position == Math.min(20, castList.size) - 1) {
                binding.spacingEnd.visibility = View.VISIBLE
            } else {
                binding.spacingEnd.visibility = View.GONE
                binding.spacingStart.visibility = View.GONE
            }

            binding.castImage.load(AppConstant.IMGBASEURL + castList[position].profile_path) {
                placeholder(AppConstant.actorPlaceHolder[position % 4])
                error(AppConstant.actorPlaceHolder[position % 4])
            }

            binding.castName.text = castList[position].name

            binding.root.setOnClickListener {
                val bundle = bundleOf(AppConstant.cast to castList[position])
                it.findNavController()
                    .navigate(R.id.action_movieDetailsFragment_to_actorDetailsFragment, bundle)
            }
        }
    }

    override fun getItemCount(): Int = Math.min(20, castList.size)

}