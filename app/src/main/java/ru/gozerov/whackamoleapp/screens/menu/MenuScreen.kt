package ru.gozerov.whackamoleapp.screens.menu

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
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
fun MenuScreen(
    tabController: NavController
) {
    val context = LocalContext.current
    var cells = if (context.sharedPrefs.contains(KEY_CELLS)) {
        context.sharedPrefs.getInt(KEY_CELLS, CELLS_DEFAULT)
    } else CELLS_DEFAULT

    val num = listOf(6,7,8,9)
    val (selectedButton, onButtonSelected) = remember { mutableStateOf(cells) }
    val highestScore = context.sharedPrefs.getInt(KEY_HIGHEST_SCORE, HIGHEST_SCORE_DEFAULT)
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = stringResource(R.string.choose_number_of_holes),
                fontSize = 24.sp,
                color = MaterialTheme.colors.secondary,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(top = 32.dp)
            )

            Spacer(modifier = Modifier.height(16.dp))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.selectableGroup()
            ) {
                num.forEach { n->
                    RadioButton(
                        selected = (n==selectedButton),
                        onClick = {
                            onButtonSelected(n)
                            cells = n
                            context.sharedPrefs.edit()
                                .putInt(KEY_CELLS, n)
                                .apply()
                        },
                        colors = RadioButtonDefaults.colors(selectedColor = MaterialTheme.colors.primary)
                    )
                    Text(text = n.toString())
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.the_highest_score, highestScore),
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colors.secondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = stringResource(id = R.string.rules),
                textAlign = TextAlign.Center,
                fontSize = 18.sp,
                color = MaterialTheme.colors.secondary,
                modifier = Modifier
                    .padding(horizontal = 32.dp)
            )

            Spacer(modifier = Modifier.height(24.dp))

            Button(
                onClick = {
                    tabController.navigate(Screens.GameScreen.simpleRoute + cells) {
                        launchSingleTop = true
                    }
                },
                modifier = Modifier.size(width = 120.dp, height = 48.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primary
                )
            ) {
                Text(
                    text = stringResource(id = R.string.play),
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colors.background
                )
            }
        }

    }
}