package uz.ikpydev.mytaxi.feature.ui.presentation.map

import com.mapbox.geojson.Point
import uz.ikpydev.mytaxi.core.util.DriverState

data class MapState(
    val driverState: DriverState = DriverState.DriverBusy,
    val point: Point? = null,
    val mapZoomState: Double = 15.0
)
