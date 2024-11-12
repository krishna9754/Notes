package com.example.notes.components

import android.annotation.SuppressLint
import androidx.annotation.StringRes
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.notes.R
import com.example.notes.mvvm.NoteViewModel
import com.example.notes.navigation.NoteEnum


@SuppressLint("SupportAnnotationUsage")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    @StringRes topText: Int = R.string.amy_note,
    isShow: Boolean = true,
    selection: Boolean = true,
    arrow: Boolean = true,
    content: @Composable (PaddingValues) -> Unit,
    noteViewModel: NoteViewModel,
    navController: NavHostController,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.border(width = 1.dp, color = Color.Black),
                title = {
                    Row(
                        modifier = Modifier
                            .padding(horizontal = 8.dp)
                            .fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        if (arrow) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Back",
                                modifier = Modifier.clickable { navController.popBackStack() }
                            )
                        }
                        Text(
                            text = stringResource(id = topText),
                            fontSize = 22.sp,
                            fontWeight = FontWeight.SemiBold
                        )
                        if (arrow) {
                            Icon(
                                painter = painterResource(id = R.drawable.diskette),
                                contentDescription = "Back",
                                modifier = Modifier.size(20.dp)
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = Color(0xff262c3b),
                    titleContentColor = Color.White
                )
            )
        },

        floatingActionButton = {
            if (isShow) {
                FloatingActionButton(
                    onClick = { if (selection) navController.navigate(NoteEnum.ADD_NOTE.name) }
                ) {
                    if (selection) {
                        Row(
                            modifier = Modifier.padding(horizontal = 8.dp),
                            verticalAlignment = Alignment.CenterVertically,
                            horizontalArrangement = Arrangement.spacedBy(4.dp)
                        ) {
                            Icon(
                                imageVector = Icons.Default.Add,
                                contentDescription = "Add Note"
                            )
                            Text(text = stringResource(id = R.string.add_note))
                        }
                    } else {
                        Icon(
                            painter = painterResource(id = R.drawable.paint_palette),
                            contentDescription = "Add Note",
                            modifier = Modifier.size(40.dp)
                        )
                    }
                }
            }
        },
        containerColor = Color(0xff262c3b)
    ) { innerPad ->

        content(innerPad)
    }
}