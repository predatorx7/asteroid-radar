package com.udacity.asteroidradar.database

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*


@Database(entities = [DatabaseAsteroid::class], version = 1, exportSchema = false)
abstract class AsteroidDatabase : RoomDatabase() {
    abstract val asteroidDao: AsteroidDao
}

private lateinit var CACHED_DB_INSTANCE: AsteroidDatabase

fun getDatabase(context: Context): AsteroidDatabase {
    synchronized(AsteroidDatabase::class.java) {
        if (!::CACHED_DB_INSTANCE.isInitialized) {
            CACHED_DB_INSTANCE = Room.databaseBuilder(context.applicationContext,
                AsteroidDatabase::class.java,
                "asteroids").build()
        }
    }
    return CACHED_DB_INSTANCE
}

@Dao
interface AsteroidDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg asteroid: DatabaseAsteroid)

    @Query("SELECT * FROM asteroids WHERE closeApproachDate BETWEEN :startDate AND :endDate ORDER BY closeApproachDate DESC")
    fun getAsteroidsBetweenDates(startDate: String, endDate: String): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids ORDER BY closeApproachDate DESC")
    fun getAsteroids(): LiveData<List<DatabaseAsteroid>>

    @Query("SELECT * FROM asteroids WHERE closeApproachDate = :startDate ORDER BY closeApproachDate DESC")
    fun getAsteroidsDay(startDate: String): LiveData<List<DatabaseAsteroid>>
}

object Constants {
    const val API_KEY = "FJzrPcO2sadrM0QeTh7GwP88hLg7YeF9er8pQ7ZT"
    const val API_QUERY_DATE_FORMAT = "YYYY-MM-dd"
    const val DEFAULT_END_DATE_DAYS = 7
    const val BASE_URL = "https://api.nasa.gov/"
}
