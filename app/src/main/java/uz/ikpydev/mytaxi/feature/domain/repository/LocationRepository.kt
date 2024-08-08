package uz.ikpydev.mytaxi.feature.domain.repository

import uz.ikpydev.mytaxi.feature.data.datasource.LocationEntity
import uz.ikpydev.mytaxi.feature.domain.model.LocationModel

interface LocationRepository {

    suspend fun addLocation(locationModel: LocationModel)
    suspend fun getLocation(): List<LocationModel>
}