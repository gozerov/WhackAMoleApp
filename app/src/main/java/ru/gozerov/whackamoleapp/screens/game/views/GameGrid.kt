package ru.gozerov.whackamoleapp.screens.game.views

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import ru.gozerov.whackamoleapp.R
import ru.gozerov.whackamoleapp.screens.game.GameViewModel
import ru.gozerov.whackamoleapp.screens.game.state_machine.GameIntent


@OptIn(ExperimentalFoundationApi::class)
@Composable
fun GameGrid(
    states: List<MutableState<Boolean>>,
    viewModel: GameViewModel,
    score: MutableState<Int>
) {
    Box(
        modifier = Modifier.fillMaxSize()
    ) {

        LazyVerticalGrid(
            modifier = Modifier.align(Alignment.Center),
            cells = GridCells.Fixed(3),
            content = {
                states.forEach { state ->
                    item {
                        GameItem(state) {
                            score.value++
                            viewModel.handleIntent(GameIntent.OnMoleWhacked)
                        }
                    }
                }
            }
        )
    }
}


@Composable
fun GameItem(state: MutableState<Boolean>, onClick: () -> Unit) {
    val isChecked = remember { mutableStateOf(false) }
    val paint = painterResource(id = R.drawable.mole_back)
    val imageState = remember {
        mutableStateOf(paint)
    }
    if (state.value && !isChecked.value)
        imageState.value = painterResource(id = R.drawable.mole_image)
    if (!state.value && !isChecked.value)
        imageState.value = painterResource(id = R.drawable.mole_back)
    Image(
        modifier = Modifier
            .padding(all = 8.dp)
            .size(120.dp)
            .clickable {
                if (state.value && !isChecked.value) {
                    imageState.value = paint
                    isChecked.value = true
                    state.value = false
                    onClick()

                }
            },
        painter = imageState.value,
        contentDescription = stringResource(R.string.mole)
    )
    if (isChecked.value && !state.value) isChecked.value = false
}