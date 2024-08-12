package com.example.todoappmoss.bloc

import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class NetworkClient {
    private val client = OkHttpClient()

    @Throws(IOException::class)
    fun get(url: String): String? {
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            return if (!response.isSuccessful) null else response.body?.string()
        }
    }
}
