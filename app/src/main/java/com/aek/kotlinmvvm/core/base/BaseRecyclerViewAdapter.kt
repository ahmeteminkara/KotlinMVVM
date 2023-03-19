package com.aek.kotlinmvvm.core.base

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.viewbinding.ViewBinding

abstract class BaseRecyclerViewAdapter<T, VB : ViewBinding> :
    RecyclerView.Adapter<BaseRecyclerViewAdapter.Holder>() {

    private var items: MutableList<T> = arrayListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        val binding = getViewBinding(LayoutInflater.from(parent.context), parent)
        return Holder(binding)
    }

    @Suppress("UNCHECKED_CAST")
    override fun onBindViewHolder(holder: Holder, position: Int) {
        setData(holder.viewBinding as VB, items[position], position)
    }

    override fun getItemCount() = items.size

    protected abstract fun getViewBinding(inflater: LayoutInflater, parent: ViewGroup?): VB

    protected abstract fun setData(binding: VB, item: T, position: Int)

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<T>) {
        if (items.isEmpty()) {
            items = newList as MutableList<T>
            notifyDataSetChanged()
        } else {
            val diff = BaseDiffUtil(items, newList)
            diff.calculateAndDispatch(this)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clear() {
        items.clear()
        notifyDataSetChanged()
    }

    class Holder(val viewBinding: ViewBinding) : RecyclerView.ViewHolder(viewBinding.root)
}
