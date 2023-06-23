package com.pagme.app.presentation.adapter

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

open class SwipeToDeleteCallback(private val adapter: CardAdapter) : ItemTouchHelper.Callback() {

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        // Defina os tipos de movimentos suportados (deslizar para a direita)
        val swipeFlags = ItemTouchHelper.RIGHT
        return makeMovementFlags(0, swipeFlags)

    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        // Não é necessário implementar para deslizar
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        // Notifique o adapter sobre a ação de deslizar
        val position = viewHolder.adapterPosition
        adapter.deleteItem(position)

    }
}
