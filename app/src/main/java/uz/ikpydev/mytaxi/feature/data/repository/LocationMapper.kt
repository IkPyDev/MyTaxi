package uz.ikpydev.mytaxi.feature.data.repository

import uz.ikpydev.mytaxi.feature.data.datasource.LocationEntity
import uz.ikpydev.mytaxi.feature.domain.model.LocationModel

object LocationMapper {

    fun LocationEntity.toLocationModel(entity: LocationEntity): LocationModel {
        return LocationModel(
            id = entity.id ?: 0,
            latitude = entity.latitude,
            longitude = entity.longitude,
        )
    }

    fun LocationModel.toLocationEntity(locationModel: LocationModel): LocationEntity {
        return LocationEntity(
            id = locationModel.id,
            latitude = locationModel.latitude,
            longitude = locationModel.longitude,
        )
    }


}