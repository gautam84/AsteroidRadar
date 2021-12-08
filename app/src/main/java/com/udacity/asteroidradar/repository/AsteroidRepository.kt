package com.udacity.asteroidradar.repository

import android.util.Log
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.Constants.API_KEY
import com.udacity.asteroidradar.PictureOfDay
import com.udacity.asteroidradar.api.AsteroidApi
import com.udacity.asteroidradar.api.parseAsteroidsJsonResult
import com.udacity.asteroidradar.database.AsteroidDatabase
import com.udacity.asteroidradar.database.asDomainModel
import com.udacity.asteroidradar.getSeventhDay
import com.udacity.asteroidradar.getToday
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject

class AsteroidRepository(private val database: AsteroidDatabase) {

    suspend fun refreshAsteroids(
        startDate: String = getToday(),
        endDate: String = getSeventhDay()
    ) {
        var asteroidList: ArrayList<Asteroid> = ArrayList()
        var pictureOfDay: PictureOfDay? = null
//        var abc: String = ""
        withContext(Dispatchers.IO) {
            val asteroidResponseBody: String = AsteroidApi.service.getAsteroidsAsync(
                getToday(), getSeventhDay()
            ).await().string()

//            pictureOfDay =
//            Log.d("tag", asteroidResponseBody)
            asteroidList = parseAsteroidsJsonResult(JSONObject(asteroidResponseBody))
            database.asteroidDao.insertAll(*asteroidList.asDomainModel())
        }
//        pictureOfDay?.toString()?.let { Log.d("tagABC", it) }

    }

//    fun getPictureOfTheDay(): PictureOfDay

    suspend fun deletePreviousDayAsteroids() {
        withContext(Dispatchers.IO) {
            database.asteroidDao.deletePreviousDayAsteroids(getToday())
        }
    }
}