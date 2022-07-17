package ru.gozerov.whackamoleapp.screens.game.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import ru.gozerov.whackamoleapp.R
import ru.gozerov.whackamoleapp.utils.Constants
import kotlin.time.Duration.Companion.seconds


@Composable
fun GameTimer(isStarted: MutableState<Boolean>) {
    if (isStarted.value) {
        var ticks by remember { mutableStateOf(Constants.TIMER_DEFAULT) }
        LaunchedEffect(key1 = Unit) {
            while (ticks>0) {
                delay(1.seconds)
                ticks--
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, start = 16.dp),
            contentAlignment = Alignment.CenterStart
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = stringResource(id = R.string.time_left_is, ticks),
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold
                )

            }
        }
    }
}