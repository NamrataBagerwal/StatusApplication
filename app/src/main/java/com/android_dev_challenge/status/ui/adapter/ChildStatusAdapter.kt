package com.android_dev_challenge.status.ui.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.android_dev_challenge.status.BR
import com.android_dev_challenge.status.R
import com.android_dev_challenge.status.ui.dto.StatusDto

class ChildStatusAdapter(val onItemClickListener: (status: StatusDto.ChildStatus) -> Unit) :
    RecyclerView.Adapter<ChildStatusAdapter.ChildStatusViewHolder>() {

    var statusList: List<StatusDto.ChildStatus> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChildStatusViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding =
            DataBindingUtil.inflate<ViewDataBinding>(layoutInflater, viewType, parent, false)
        return ChildStatusViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ChildStatusViewHolder, position: Int) {
        val childStatus: StatusDto.ChildStatus = statusList[position]

        holder.run {
            bind(childStatus)
            itemView.setOnClickListener { onItemClickListener(childStatus) }
        }
    }

    override fun getItemCount() = statusList.size

    override fun getItemViewType(position: Int) = R.layout.adapter_list_item_user

    class ChildStatusViewHolder(
        private val binding: ViewDataBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(childStatus: StatusDto.ChildStatus) {
            binding.setVariable(BR.childStatus, childStatus)
            binding.executePendingBindings()
        }
    }
}
