package uz.ikpydev.mytaxi.feature.data.datasource

import androidx.room.Entity
import androidx.room.PrimaryKey
import uz.ikpydev.mytaxi.core.util.Constants

@Entity(tableName = Constants.LOCATION)
data class LocationEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    val longitude: Double,
    val latitude: Double,

)
