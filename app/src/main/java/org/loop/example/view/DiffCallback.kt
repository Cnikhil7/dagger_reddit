package org.loop.example.view

import android.support.v7.util.DiffUtil
import org.loop.example.models.pojo.Child

class DiffCallback(var oldList: MutableList<Child>
                   , var newList: MutableList<Child>) : DiffUtil.Callback() {


    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].data.id == newList[newItemPosition].data.id
    }

    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] == newList[newItemPosition]
    }

}