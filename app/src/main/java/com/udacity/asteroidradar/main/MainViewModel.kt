package com.udacity.asteroidradar.main

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDBModel
import com.udacity.asteroidradar.getSeventhDay
import com.udacity.asteroidradar.getToday
import com.udacity.asteroidradar.repository.AsteroidRepository
import kotlinx.coroutines.launch
import java.lang.Exception

class MainViewModel(
    application: Application
) : AndroidViewModel(application) {
    private val database = AsteroidDatabase.getDatabase(application)
    private val asteroidRepository = AsteroidRepository(database)

    private var _asteroids = Transformations.map(database.asteroidDao.getAllAsteroids()) {
        it.asDBModel()
    }
    val asteroids: LiveData<List<Asteroid>>
        get() = _asteroids

    private val _pictureOfDay = MutableLiveData<PictureOfDay>()
    val pictureOfDay: LiveData<PictureOfDay>
        get() = _pictureOfDay

    private val _navigateToSelectedAsteroid = MutableLiveData<Asteroid>()
    val navigateToSelectedAsteroid: LiveData<Asteroid>
        get() = _navigateToSelectedAsteroid

    fun displayAsteroidDetails(asteroid: Asteroid) {
        _navigateToSelectedAsteroid.value = asteroid
    }

    fun displayAsteroidDetailsComplete() {
        _navigateToSelectedAsteroid.value = null
    }

    init {
        viewModelScope.launch {
            asteroidRepository.deletePreviousDayAsteroids()
            try {
                asteroidRepository.refreshAsteroids(getToday(), getSeventhDay())
                _pictureOfDay.value = AsteroidApi.service.getPictureOfDayAsync().await()

            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }
}
