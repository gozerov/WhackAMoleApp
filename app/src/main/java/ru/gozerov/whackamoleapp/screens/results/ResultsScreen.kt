package ru.gozerov.whackamoleapp.screens.results

import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import ru.gozerov.whackamoleapp.R
import ru.gozerov.whackamoleapp.navigation.Screens
import ru.gozerov.whackamoleapp.utils.Constants.CELLS_DEFAULT
import ru.gozerov.whackamoleapp.utils.Constants.HIGHEST_SCORE_DEFAULT
import ru.gozerov.whackamoleapp.utils.Constants.KEY_CELLS
import ru.gozerov.whackamoleapp.utils.Constants.KEY_HIGHEST_SCORE
import ru.gozerov.whackamoleapp.utils.sharedPrefs

@Composable
fun ResultsScreen(
    score: Int,
    tabController: NavController,
    activityController: NavController
) {
    val context = LocalContext.current
    val highestScore = context.sharedPrefs.getInt(KEY_HIGHEST_SCORE, HIGHEST_SCORE_DEFAULT)

    DisposableEffect(key1 = context) {
        onDispose {
            if (score > highestScore) {
                context.sharedPrefs.edit()
                    .putInt(KEY_HIGHEST_SCORE, score)
                    .apply()
            }
        }
    }
    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(modifier = Modifier.height(32.dp))

        Text(
            text = stringResource(id = R.string.your_score_is, score),
            fontSize = 24.sp,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .padding(top = 8.dp)
        )
        Text(
            text = stringResource(id = R.string.the_highest_score, highestScore),
            fontSize = 20.sp,
            color = MaterialTheme.colors.secondary,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )

        if (score > highestScore) {
            Text(
                text = stringResource(id = R.string.congratulations),
                fontSize = 24.sp,
                textAlign = TextAlign.Center,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(top = 16.dp, start = 32.dp, end = 32.dp)
            )
        }
        Spacer(modifier = Modifier.height(24.dp))
            Button(
                onClick = {
                    val cells = context.sharedPrefs.getInt(KEY_CELLS, CELLS_DEFAULT)
                    tabController.navigate(Screens.GameScreen.simpleRoute + cells) {
                        launchSingleTop = true
                    }
                    activityController.popBackStack(Screens.MainScreen.route, false)
                },
                modifier = Modifier
                    .size(width = 180.dp, height = 60.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = stringResource(R.string.play_again),
                fontSize = 20.sp,
                color = MaterialTheme.colors.background
            )
        }
        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                tabController.navigate(Screens.MenuScreen.route) {
                    launchSingleTop = true
                    popUpTo(Screens.MenuScreen.route)
                }
                activityController.popBackStack(Screens.MainScreen.route, false)
            },
            modifier = Modifier
                .size(width = 180.dp, height = 60.dp),
            colors = ButtonDefaults.buttonColors(backgroundColor = MaterialTheme.colors.primary)
        ) {
            Text(
                text = stringResource(id = R.string.menu),
                fontSize = 24.sp,
                color = MaterialTheme.colors.background
            )
        }

    }

}