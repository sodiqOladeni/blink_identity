package io.blusalt.simreg.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import io.blusalt.simreg.R
import io.blusalt.simreg.databinding.ModelHomeIdentityListBinding
import io.blusalt.simreg.dto.UserIdentityDto

class HomeIdentityListAdapter : ListAdapter<UserIdentityDto, HomeIdentityListAdapter.ViewHolder>(
    RequestSecurityCallback()
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    class ViewHolder(private val viewDataBinding: ModelHomeIdentityListBinding) : RecyclerView.ViewHolder(viewDataBinding.root) {
        companion object {
            @LayoutRes
            val LAYOUT = R.layout.model_home_identity_list

            fun from(parent: ViewGroup): ViewHolder {
                val withDataBinding: ModelHomeIdentityListBinding = DataBindingUtil.inflate(
                    LayoutInflater.from(parent.context), LAYOUT, parent, false)
                return ViewHolder(withDataBinding)
            }
        }

        fun bind(userIdentityDto: UserIdentityDto) {
            viewDataBinding.also {
                it.userIdentityDto = userIdentityDto
                it.executePendingBindings()
            }
        }
    }

    class RequestSecurityCallback : DiffUtil.ItemCallback<UserIdentityDto>() {
        override fun areItemsTheSame(oldItem: UserIdentityDto, newItem: UserIdentityDto): Boolean {
            return oldItem.documentNumber == newItem.documentNumber
        }

        override fun areContentsTheSame(oldItem: UserIdentityDto, newItem: UserIdentityDto): Boolean {
            return oldItem == newItem
        }
    }
}