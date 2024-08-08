package uz.ikpydev.mytaxi.feature.data.datasource

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [LocationEntity::class],
    version = 1
)
abstract class LocationDatabase: RoomDatabase() {

    abstract val locationDao: LocationDao
}