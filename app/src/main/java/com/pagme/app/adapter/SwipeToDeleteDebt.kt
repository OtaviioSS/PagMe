package com.pagme.app.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.pagme.app.business.DebtBusiness
var debtBussines = DebtBusiness()

class SwipeToDeleteDebt(var adapter: DebtAdapter):ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT) {
    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        TODO("Not yet implemented")
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
    val pos = viewHolder.adapterPosition
        debtBussines.removeDebt(adapter.deleteItem(pos))
    }

}