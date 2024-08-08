package uz.ikpydev.mytaxi.feature.ui.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import uz.ikpydev.mytaxi.feature.ui.presentation.map.component.MapScreens

@Composable
fun NavigationMain(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    NavHost(
        modifier = modifier,
        navController = navController,
        startDestination = Screens.MapScreens
    ) {
        composable<Screens.MapScreens> {
            MapScreens()
        }
    }
}