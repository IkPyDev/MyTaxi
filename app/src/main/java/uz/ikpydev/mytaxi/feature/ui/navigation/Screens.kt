package uz.ikpydev.mytaxi.feature.ui.navigation

import kotlinx.serialization.Serializable

sealed interface Screens {

    @Serializable
    data object MapScreens: Screens
}