package uz.ikpydev.mytaxi.feature.ui.presentation.map.component

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.compose.ui.viewinterop.NoOpUpdate
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.mapbox.geojson.Point
import com.mapbox.maps.CameraOptions
import com.mapbox.maps.MapInitOptions
import com.mapbox.maps.MapView
import com.mapbox.maps.MapboxExperimental
import com.mapbox.maps.dsl.cameraOptions
import com.mapbox.maps.extension.compose.animation.viewport.rememberMapViewportState
import com.mapbox.maps.plugin.animation.flyTo
import com.mapbox.maps.plugin.annotation.annotations
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationManager
import com.mapbox.maps.plugin.annotation.generated.PointAnnotationOptions
import com.mapbox.maps.plugin.annotation.generated.createPointAnnotationManager
import dev.shreyaspatil.permissionflow.compose.rememberPermissionFlowRequestLauncher
import dev.shreyaspatil.permissionflow.compose.rememberPermissionState
import kotlinx.coroutines.launch
import uz.ikpydev.mytaxi.R
import uz.ikpydev.mytaxi.core.presentation.component.*
import uz.ikpydev.mytaxi.core.util.LocationService
import uz.ikpydev.mytaxi.core.util.MapUtils.MAPBOX_NAVIGATION_LIGHT
import uz.ikpydev.mytaxi.core.util.MapUtils.MAPBOX_NAVIGATION_NIGHT
import uz.ikpydev.mytaxi.core.util.MapUtils.TASHKENT_LATITUDE
import uz.ikpydev.mytaxi.core.util.MapUtils.TASHKENT_LONGITUDE
import uz.ikpydev.mytaxi.feature.ui.presentation.map.MapEvent
import uz.ikpydev.mytaxi.feature.ui.presentation.map.MapState
import uz.ikpydev.mytaxi.feature.ui.presentation.map.MapViewModel
import uz.ikpydev.mytaxi.feature.ui.theme.*

@OptIn(ExperimentalMaterial3Api::class, MapboxExperimental::class)
@Composable
fun MapScreens(
    viewModel: MapViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    val state by viewModel.getState().collectAsStateWithLifecycle()
    val scaffoldState = rememberBottomSheetScaffoldState()
    val isNight = isSystemInDarkTheme()
    var pointAnnotationManager: PointAnnotationManager? by remember { mutableStateOf(null) }

    val marker = remember(context) { context.getDrawable(R.drawable.taxi_car)!!.toBitmap() }

    val permissionLauncher = rememberPermissionFlowRequestLauncher()
    val permissionList = arrayOf(
        android.Manifest.permission.ACCESS_FINE_LOCATION,
        android.Manifest.permission.ACCESS_COARSE_LOCATION
    )
    val permissionState by rememberPermissionState(android.Manifest.permission.ACCESS_FINE_LOCATION)

    val mapViewportState = rememberMapViewportState {
        setCameraOptions {
            zoom(state.mapZoomState)
            center(state.point ?: Point.fromLngLat(TASHKENT_LONGITUDE, TASHKENT_LATITUDE))
            MapInitOptions(
                context = context,
                styleUri = if (isNight) {
                    MAPBOX_NAVIGATION_NIGHT
                } else {
                    MAPBOX_NAVIGATION_LIGHT
                },
            )
        }
    }

    mapViewportState.flyTo(cameraOptions { zoom(state.mapZoomState) })

    viewModel.onEvent(MapEvent.StartLocationUpdates)
    Surface(
        modifier = Modifier.fillMaxSize()
    ) {
        BottomSheetScaffold(
            scaffoldState = scaffoldState,
            sheetPeekHeight = 100.dp,
            sheetContent = { BottomSheetContent() },
            sheetDragHandle = null,
            sheetContainerColor = MaterialTheme.colorScheme.background
        ) {
            ConstraintLayout(
                modifier = Modifier.fillMaxSize()
            ) {
                val (topContainer, centerLeftContainer, centerRightContainer, map) = createRefs()

                AndroidView(factory = {
                    MapView(it).also { mapView ->
                        setMapStyle(mapView, isNight)

                        val annotationApi = mapView.annotations

                        pointAnnotationManager = annotationApi.createPointAnnotationManager()
                    }
                }, update = { mapView ->
                    if (state.point != null) {
                        pointAnnotationManager?.let { maps ->
                            maps.deleteAll()
                            val pointAnnotationOptions =
                                PointAnnotationOptions().withPoint(state.point ?: Point.fromLngLat(
                                    TASHKENT_LONGITUDE, TASHKENT_LONGITUDE)).withIconImage(marker)
                            maps.create(pointAnnotationOptions)
                        }
                        mapView.mapboxMap.flyTo(flyCameraToLiveLocation(state))
                    }
                    NoOpUpdate
                }, modifier = Modifier
                    .fillMaxSize()
                    .constrainAs(map) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                        end.linkTo(parent.end)
                    })

                Row(
                    modifier = Modifier
                        .padding(16.dp)
                        .constrainAs(topContainer) {
                            top.linkTo(parent.top)
                        },
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    CustomButtonWithIcon(
                        icon = R.drawable.ic_menu,
                        onClick = {
                            with(scaffoldState.bottomSheetState) {
                                scope.launch {
                                    if (currentValue.ordinal.dp == 2.dp) {
                                        expand()
                                    } else {
                                        partialExpand()
                                    }
                                }
                            }
                        }
                    )
                    CustomSwitchHeader(
                        modifier = Modifier.weight(1F),
                        driverState = state.driverState
                    ) {
                        viewModel.onEvent(MapEvent.DriverState)
                    }
                    CustomTextButton(
                        text = stringResource(id = R.string.number),
                        textColor = BlackPrimary,
                        textSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        containerInsideColor = ButtonGreen
                    )
                }

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .constrainAs(centerLeftContainer) {
                            start.linkTo(parent.start)
                            top.linkTo(centerRightContainer.top)
                        }
                ) {
                    AnimatedVisibility(
                        visible = scaffoldState.bottomSheetState.targetValue.ordinal.dp == 2.dp,
                        enter = slideInHorizontally() + fadeIn(),
                        exit = slideOutHorizontally() + fadeOut()
                    ) {
                        CustomButtonWithIcon(
                            icon = R.drawable.two_arrow_up,
                            iconTint = MaterialTheme.colorScheme.onSecondary,
                            containerInsideColor = MaterialTheme.colorScheme.surface,
                            alpha = 0.9f
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .padding(16.dp)
                        .constrainAs(centerRightContainer) {
                            top.linkTo(topContainer.bottom)
                            end.linkTo(parent.end)
                            bottom.linkTo(parent.bottom)
                        }
                ) {
                    AnimatedVisibility(
                        visible = scaffoldState.bottomSheetState.targetValue.ordinal.dp == 2.dp,
                        enter = slideInHorizontally(initialOffsetX = { it / 2 }) + fadeIn(),
                        exit = slideOutHorizontally(targetOffsetX = { it / 2 }) + fadeOut()
                    ) {
                        Column(
                            verticalArrangement = Arrangement.spacedBy(16.dp)
                        ) {
                            CustomButtonWithIcon(
                                icon = R.drawable.ic_plus,
                                iconTint = MaterialTheme.colorScheme.onSecondary,
                                alpha = 0.9f,
                                onClick = { viewModel.onEvent(MapEvent.MapZoomIn) }
                            )
                            CustomButtonWithIcon(
                                icon = R.drawable.ic_minus,
                                iconTint = MaterialTheme.colorScheme.onSecondary,
                                alpha = 0.9f,
                                onClick = { viewModel.onEvent(MapEvent.MapZoomOut) }
                            )
                            CustomButtonWithIcon(
                                icon = R.drawable.ic_navigation,
                                iconTint = Blue,
                                alpha = 0.9f,
                                onClick = {
                                    scope.launch {
                                        if (permissionState.isGranted) {
                                            mapViewportState.flyTo(cameraOptions {
                                                zoom(15.0)
                                                center(state.point)
                                            })
                                        } else {
                                            permissionLauncher.launch(permissionList)
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }

    LaunchedEffect(permissionState) {
        scope.launch {
            if (permissionState.isGranted) {
                viewModel.onEvent(MapEvent.MapPoint(LocationService.getCurrentLocation(context)))
            } else {
                permissionLauncher.launch(permissionList)
            }
        }
    }
}


private fun flyCameraToLiveLocation(state: MapState): CameraOptions {
    return CameraOptions.Builder().zoom(state.mapZoomState).center(
        Point.fromLngLat(
            state.point?.longitude() ?: TASHKENT_LONGITUDE,
            state.point?.latitude() ?: TASHKENT_LATITUDE
        )
    ).build()
}

private fun setMapStyle(mapView: MapView, isDarkTheme: Boolean) {
    val styleUri = if (isDarkTheme) {
        MAPBOX_NAVIGATION_NIGHT
    } else {
        MAPBOX_NAVIGATION_LIGHT
    }
    mapView.mapboxMap.loadStyle(styleUri)
}




@Composable
private fun BottomSheetContent(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .padding(16.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(MaterialTheme.colorScheme.surface)
    ) {
        BottomSheetItem(
            icon = R.drawable.ic_tariff,
            title = stringResource(id = R.string.tariff),
            value = "6 / 8"
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 16.dp, end = 16.dp)
                .background(MaterialTheme.colorScheme.onSurface)
        )
        BottomSheetItem(
            icon = R.drawable.ic_order,
            title = stringResource(id = R.string.orders),
            value = "0"
        )
        Spacer(
            modifier = Modifier
                .fillMaxWidth()
                .height(1.dp)
                .padding(start = 16.dp, end = 16.dp)
                .background(MaterialTheme.colorScheme.onSurface)
        )
        BottomSheetItem(
            icon = R.drawable.ic_rocket,
            title = stringResource(id = R.string.rocket)
        )
    }
}
