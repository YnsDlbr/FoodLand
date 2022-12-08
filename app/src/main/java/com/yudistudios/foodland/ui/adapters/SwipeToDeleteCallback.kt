package com.yudistudios.foodland.ui.adapters

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import android.graphics.drawable.ColorDrawable

import android.graphics.drawable.Drawable
import android.view.View
import androidx.core.content.ContextCompat
import com.yudistudios.foodland.R


class SwipeToDeleteCallback(private val mAdapter: BasketFoodRecyclerViewAdapter, context: Context) :
    ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

    private var icon: Drawable? = null
    private var background: ColorDrawable? = null

    init {
        icon = ContextCompat.getDrawable(context,
            R.drawable.ic_baseline_delete_forever_24);
        background = ColorDrawable(Color.RED)
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {

        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        val position = viewHolder.adapterPosition
        if (position != -1) {
            mAdapter.deleteItem(mAdapter.currentList[position])
        }
    }

    override fun onChildDraw(
        c: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)

        val itemView: View = viewHolder.itemView
        val backgroundCornerOffset = 20

        val iconMargin = (itemView.height - icon!!.intrinsicHeight) / 2
        val iconTop = itemView.top + (itemView.height - icon!!.intrinsicHeight) / 2
        val iconBottom = iconTop + icon!!.intrinsicHeight

        icon!!.setTint(Color.WHITE)

        when {
            dX > 0 -> { // Swiping to the right
                val iconLeft = itemView.left + iconMargin + icon!!.intrinsicWidth
                val iconRight = itemView.left + iconMargin
                icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background!!.setBounds(
                    itemView.left, itemView.top,
                    itemView.left + dX.toInt() + backgroundCornerOffset,
                    itemView.bottom
                )
            }
            dX < 0 -> { // Swiping to the left
                val iconLeft = itemView.right - iconMargin - icon!!.intrinsicWidth
                val iconRight = itemView.right - iconMargin
                icon!!.setBounds(iconLeft, iconTop, iconRight, iconBottom)
                background!!.setBounds(
                    itemView.right + dX.toInt() - backgroundCornerOffset,
                    itemView.top, itemView.right, itemView.bottom
                )
            }
            else -> { // view is unSwiped
                background!!.setBounds(0, 0, 0, 0)
            }
        }

        background!!.draw(c)
        icon!!.draw(c)
    }
}