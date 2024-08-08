package uz.ikpydev.mytaxi.feature.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface LocationDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addLocation(locationEntity: LocationEntity)

    @Query("SELECT * FROM locations ORDER BY id DESC")
    suspend fun getLocations(): List<LocationEntity>
}