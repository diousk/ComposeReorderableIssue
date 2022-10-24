package com.diousk.composeissue

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import org.burnoutcrew.reorderable.ItemPosition

data class ItemData(val title: String, val key: String, val isLocked: Boolean = false)

class ReorderListViewModel : ViewModel() {
    var cats by mutableStateOf(List(500) { ItemData("Cat $it", "id$it") })
    var dogs by mutableStateOf(List(500) {
        if (it.mod(10) == 0) ItemData("Locked", "id$it", true) else ItemData("Dog $it", "id$it")
    })

    fun moveCat(from: ItemPosition, to: ItemPosition) {
        cats = cats.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }

    fun moveDog(from: ItemPosition, to: ItemPosition) {
        dogs = dogs.toMutableList().apply {
            add(to.index, removeAt(from.index))
        }
    }

    fun isDogDragEnabled(pos: ItemPosition) = dogs.getOrNull(pos.index)?.isLocked != true
}