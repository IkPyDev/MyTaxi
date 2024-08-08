package uz.ikpydev.mytaxi.feature.ui.presentation.map

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mapbox.geojson.Point
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import uz.ikpydev.mytaxi.core.util.DriverState
import uz.ikpydev.mytaxi.core.util.LocationService
import uz.ikpydev.mytaxi.core.util.MapUtils.MAP_ZOOM_VALUE
import uz.ikpydev.mytaxi.feature.domain.model.LocationModel
import uz.ikpydev.mytaxi.feature.domain.repository.LocationRepository
import javax.inject.Inject
import kotlin.math.abs

@HiltViewModel
class MapViewModel @Inject constructor(
    private val repository: LocationRepository,
    @ApplicationContext private val context: Context
) : ViewModel() {

    private val _state = MutableStateFlow(MapState())
    fun getState() = _state.asStateFlow()

    fun onEvent(event: MapEvent) {
        when(event) {
            is MapEvent.DriverState -> {
                changeDriverState()
            }
            is MapEvent.MapPoint -> {
                setPoint(event.point)
            }
            is MapEvent.MapZoomIn -> {
                changeMapZoomState(abs(MAP_ZOOM_VALUE))
            }
            is MapEvent.MapZoomOut -> {
                changeMapZoomState(MAP_ZOOM_VALUE)
            }
            is MapEvent.StartLocationUpdates -> {
                startContinuousLocationUpdates()
            }
        }
    }

    private fun changeDriverState() {
        _state.update { state ->
            state.copy(

                driverState = if (state.driverState == DriverState.DriverBusy) {
                    DriverState.DriverActive
                } else {
                    DriverState.DriverBusy
                }

            )
        }
    }
    private fun setPoint(point: Point?) {
        if (point == null) return

        _state.update { state ->
            state.copy(
                point = point
            )
        }

        viewModelScope.launch {
            repository.addLocation(
                locationModel = LocationModel(
                    longitude = point.longitude(),
                    latitude = point.latitude(),
                )
            )
        }
    }
    private fun changeMapZoomState(value: Double) {
        with(getState().value) {
            if (mapZoomState < 2.0 && value < 0 || mapZoomState > 18.0 && value > 0) return
            _state.update { state ->
                state.copy(
                    mapZoomState = mapZoomState + value
                )
            }
        }
    }
    private fun startContinuousLocationUpdates() {
        viewModelScope.launch {
            while (true) {
                val point = LocationService.getCurrentLocation(context)
                setPoint(point)
                delay(1000)
            }
        }
    }
}