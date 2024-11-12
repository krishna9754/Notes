package com.example.notes.screen.items

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.DismissDirection
import androidx.compose.material.DismissState
import androidx.compose.material.DismissValue
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.SwipeToDismiss
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.notes.mvvm.NoteData
import com.example.notes.screen.components.NoteCard
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SwipeToDismissNote(
    note: NoteData,
    scope: CoroutineScope,
    dismissState: DismissState,
    onSelect: () -> Unit,
    onDelete: () -> Job,
) {
    val backgroundColor by animateColorAsState(
        targetValue = if (dismissState.targetValue == DismissValue.Default) Color.Transparent else Color.LightGray
    )

    SwipeToDismiss(
        modifier = Modifier.clickable { onSelect() },
        state = dismissState,
        directions = setOf(DismissDirection.EndToStart),
        background = {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .border(1.dp, backgroundColor, shape = RoundedCornerShape(10.dp))
                    .background(backgroundColor, shape = RoundedCornerShape(10.dp)),
                contentAlignment = Alignment.Center
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.Delete,
                        contentDescription = "Delete",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { onDelete.invoke() }
                    )

                    Icon(
                        imageVector = Icons.Default.Refresh,
                        contentDescription = "Refresh",
                        tint = Color(0xff1ea825),
                        modifier = Modifier
                            .padding(16.dp)
                            .clickable { scope.launch { dismissState.reset() } }
                    )
                }
            }
        },
        dismissContent = { NoteCard(note = note) }
    )
}