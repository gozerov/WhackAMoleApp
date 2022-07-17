package ru.gozerov.whackamoleapp.screens.game.views

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.gozerov.whackamoleapp.R
import ru.gozerov.whackamoleapp.navigation.Screens
import ru.gozerov.whackamoleapp.screens.game.state_machine.GameViewState


@Composable
fun EndGame(
    activityController: NavController,
    tabNavController: NavController,
    state: GameViewState.EndGame
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 16.dp),
        contentAlignment = Alignment.Center
    ) {
        Button(
            onClick = {
                activityController.navigate(Screens.ResultsScreen.simpleRoute + state.result) {
                    restoreState = true
                }
                tabNavController.popBackStack(Screens.MenuScreen.route, false)
            },
            modifier = Modifier
                .size(width = 180.dp, height = 60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = stringResource(R.string.see_results),
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.background,
                textAlign = TextAlign.Center
            )
        }
    }
}