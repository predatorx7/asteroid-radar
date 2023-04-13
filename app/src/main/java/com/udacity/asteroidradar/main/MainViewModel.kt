package com.udacity.asteroidradar.main

import android.app.Application
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.lifecycle.*
import com.udacity.asteroidradar.data.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApiService
import com.udacity.asteroidradar.database.getDatabase
import com.udacity.asteroidradar.repository.AsteroidRepository
import com.udacity.asteroidradar.database.Constants.API_KEY
import com.udacity.asteroidradar.data.AsteroidFilter
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

@RequiresApi(Build.VERSION_CODES.N)
class MainViewModel(application: Application) : AndroidViewModel(application) {

    private val asteroidDatabase = getDatabase(application)
    private val repository = AsteroidRepository(asteroidDatabase)

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToDetailAsteroid = MutableLiveData<Asteroid>()
    val navigateToAsteroidDetail: LiveData<Asteroid>
        get() = _navigateToDetailAsteroid

    private var _filterAsteroid = MutableLiveData(AsteroidFilter.ALL)

    @RequiresApi(Build.VERSION_CODES.O)
    val asteroidList = Transformations.switchMap(_filterAsteroid) {
        when (it!!) {
            AsteroidFilter.WEEK -> repository.weekAsteroids
            AsteroidFilter.TODAY -> repository.todayAsteroids
            else -> repository.allAsteroids
        }
    }

    init {
        viewModelScope.launch {
            repository.refreshAsteroids()
            refreshPictureOfDay()
        }
    }


    private suspend fun refreshPictureOfDay() {
        withContext(Dispatchers.IO) {
            try {
                _pictureOfDay.postValue(
                    AsteroidApiService.AsteroidApi.retrofitService.getPictureOfTheDay(API_KEY)
                )
            } catch (err: Exception) {
                Log.e("refreshPictureOfDay", err.printStackTrace().toString())
            }
        }
    }

    fun onUpdateFilter(filter: AsteroidFilter) {
        _filterAsteroid.postValue(filter)
    }

    fun onAsteroidNavigated() {
        _navigateToDetailAsteroid.value = null
    }

    fun onAsteroidClicked(asteroid: Asteroid) {
        _navigateToDetailAsteroid.value = asteroid
    }

}