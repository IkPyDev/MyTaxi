package uz.ikpydev.mytaxi.feature.data.repository

import uz.ikpydev.mytaxi.feature.data.datasource.LocationDao
import uz.ikpydev.mytaxi.feature.data.repository.LocationMapper.toLocationEntity
import uz.ikpydev.mytaxi.feature.data.repository.LocationMapper.toLocationModel
import uz.ikpydev.mytaxi.feature.domain.model.LocationModel
import uz.ikpydev.mytaxi.feature.domain.repository.LocationRepository

class LocationRepositoryImpl(
    private val locationDao: LocationDao
) : LocationRepository{

    override suspend fun addLocation(locationModel: LocationModel) {
        return locationDao.addLocation(locationModel.toLocationEntity(locationModel))
    }

    override suspend fun getLocation(): List<LocationModel> {
        return locationDao.getLocations().map { it.toLocationModel(it) }
    }
}