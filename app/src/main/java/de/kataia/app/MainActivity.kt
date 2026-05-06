package de.kataia.app

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.rememberNavController
import de.kataia.app.navigation.SetupNavGraph
import de.kataia.app.ui.theme.KataiaTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Repositories initialisieren
        val challengeRepository = de.kataia.app.data.FakeChallengeRepository(this)
        val userPrefsRepository = de.kataia.app.data.UserPreferencesRepository(this)

        enableEdgeToEdge()
        setContent {
            KataiaTheme {
                val navController = rememberNavController()

                //Repositories an NavGraph übergeben
                SetupNavGraph(
                    navController = navController,
                    repository = challengeRepository,
                    userPrefsRepository = userPrefsRepository
                )
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    androidx.compose.material3.Text(text = "Hello $name!")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    KataiaTheme {
        Greeting("Android")
    }
}

