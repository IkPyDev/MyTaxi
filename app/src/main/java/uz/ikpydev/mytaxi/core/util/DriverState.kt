package uz.ikpydev.mytaxi.core.util

sealed class DriverState(val isBusy: Int) {
    data object DriverBusy: DriverState(Constants.DRIVER_BUSY)
    data object DriverActive: DriverState(Constants.DRIVER_ACTIVE)
}