package com.diousk.composeissue

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.diousk.composeissue.ui.theme.ComposeIssueTheme
import org.burnoutcrew.reorderable.ReorderableItem
import org.burnoutcrew.reorderable.detectReorderAfterLongPress
import org.burnoutcrew.reorderable.rememberReorderableLazyGridState
import org.burnoutcrew.reorderable.reorderable

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ComposeIssueTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    VerticalGrid()
                }
            }
        }
    }
}

@Composable
private fun VerticalGrid(
    vm: ReorderListViewModel = viewModel(),
    modifier: Modifier = Modifier,
) {
    val state = rememberReorderableLazyGridState(onMove = vm::moveDog, canDragOver = vm::isDogDragEnabled)
    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        state = state.gridState,
        contentPadding = PaddingValues(horizontal = 8.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        modifier = modifier
            .reorderable(state)
            .detectReorderAfterLongPress(state)
    ) {
        items(vm.dogs, { it.key }) { item ->
            ReorderableItem(state, item.key) { isDragging ->
                val elevation = animateDpAsState(if (isDragging) 8.dp else 0.dp)
                if (item.isLocked) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .size(100.dp)
                            .background(MaterialTheme.colors.surface)
                    ) {
                        Text(item.title)
                    }
                } else {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .shadow(elevation.value)
                            .aspectRatio(1f)
                            .background(MaterialTheme.colors.primary)
                    ) {
                        Text(item.title)
                    }
                }
            }
        }
    }
}
