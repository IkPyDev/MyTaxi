package uz.ikpydev.mytaxi.di

import android.app.Application
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import uz.ikpydev.mytaxi.core.util.Constants
import uz.ikpydev.mytaxi.feature.data.datasource.LocationDatabase
import uz.ikpydev.mytaxi.feature.data.repository.LocationRepositoryImpl
import uz.ikpydev.mytaxi.feature.domain.repository.LocationRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        app: Application
    ) : LocationDatabase {
        return Room.databaseBuilder(
            app,
            LocationDatabase::class.java,
            Constants.DATABASE
        ).build()
    }

    @Provides
    @Singleton
    fun provideLocationRepository(db: LocationDatabase): LocationRepository {
        return LocationRepositoryImpl(db.locationDao)
    }
}