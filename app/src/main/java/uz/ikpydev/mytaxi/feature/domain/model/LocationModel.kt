package uz.ikpydev.mytaxi.feature.domain.model

data class LocationModel(
    val latitude: Double,
    val longitude: Double,
    val id: Int? = null
)
