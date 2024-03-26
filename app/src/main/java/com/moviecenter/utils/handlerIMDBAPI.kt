package com.app.moviecenter.utils

import android.util.Log
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URI
import java.net.URL
suspend fun getMovieId(movieName: String): String {
    try {
        val encodedName = java.net.URLEncoder.encode(movieName, "utf-8")
        val apiUrl = "https://api.deezer.com/search/album?q=$encodedName"

        val url: URL = URI.create(apiUrl).toURL()

        val res = withContext(Dispatchers.IO) {
            (url.openConnection() as HttpURLConnection).inputStream.bufferedReader().readText()
        }

        val movieJSONObj = JSONObject(res).getJSONArray("data").getJSONObject(0)

        return movieJSONObj.getString("id")
    } catch (e: Exception) {
        Log.i("APP", e.toString())
        return ""
    }
}

suspend fun getMovieRank(movieId: String): Double {
    try {
        val apiUrl = "https://api.deezer.com/search/album?q=$movieId"

        val url: URL = URI.create(apiUrl).toURL()
        val res = withContext(Dispatchers.IO) {
            (url.openConnection() as HttpURLConnection).inputStream.bufferedReader().readText()
        }

        val movieJSONObj1 = JSONObject(res)
        val movieJSONObj= movieJSONObj1.getJSONArray("data").getJSONObject(0)
        return movieJSONObj.getDouble("nb_tracks")
    } catch (e: Exception) {
        Log.i("APP", e.toString())

        return 0.0
    }
}


