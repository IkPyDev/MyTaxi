package uz.ikpydev.mytaxi.feature.ui.presentation.map

import com.mapbox.geojson.Point

sealed class MapEvent {
    data object DriverState: MapEvent()
    data class MapPoint(val point: Point?): MapEvent()
    data object MapZoomIn: MapEvent()
    data object MapZoomOut: MapEvent()
    data object StartLocationUpdates : MapEvent()

}