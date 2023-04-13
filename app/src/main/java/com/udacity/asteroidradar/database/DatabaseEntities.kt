package com.udacity.asteroidradar.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.udacity.asteroidradar.data.Asteroid

@Entity(tableName = "asteroids")
data class DatabaseAsteroid constructor(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    val closeApproachDate: String,
    val estimatedDiameter: Double,
    val relativeVelocity: Double,
    val distanceFromEarth: Double,
    val isPotentiallyHazardous: Boolean,
    val codename: String,
    val absoluteMagnitude: Double,
)

fun List<Asteroid>.asDatabaseModel(): Array<DatabaseAsteroid> {
    return map {
        DatabaseAsteroid(
            id = it.id,
            closeApproachDate = it.closeApproachDate,
            estimatedDiameter = it.estimatedDiameter,
            codename = it.codename,
            distanceFromEarth = it.distanceFromEarth,
            absoluteMagnitude = it.absoluteMagnitude,
            relativeVelocity = it.relativeVelocity,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }.toTypedArray()
}

fun List<DatabaseAsteroid>.asDomainModel(): List<Asteroid> {
    return map {
        Asteroid(
            id = it.id,
            absoluteMagnitude = it.absoluteMagnitude,
            codename = it.codename,
            distanceFromEarth = it.distanceFromEarth,
            estimatedDiameter = it.estimatedDiameter,
            relativeVelocity = it.relativeVelocity,
            closeApproachDate = it.closeApproachDate,
            isPotentiallyHazardous = it.isPotentiallyHazardous
        )
    }
}
