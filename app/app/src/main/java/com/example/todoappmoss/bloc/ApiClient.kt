import android.util.Log
import com.example.todoappmoss.data.model.Task
import com.google.common.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException

class ApiClient {

    private val client = OkHttpClient()
    private val gson = Gson()
    private val baseUrl = "http://10.0.2.2:5286/api/Tasks"

    @Throws(IOException::class)
    fun getTodoItems(): List<Task> {
        val request = Request.Builder()
            .url(baseUrl)
            .build()

        Log.d("ApiClient", "Sending request to $baseUrl")

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("ApiClient", "Request failed with status code: ${response.code}")
                throw IOException("Unexpected code $response")

            }
            val json = response.body?.string()
            Log.d("ApiClient", "Received response: $json")
            val todoListType = object : TypeToken<List<Task>>() {}.type

            return gson.fromJson(json, todoListType)
        }
    }

    @Throws(IOException::class)
    fun getTodoItemById(id: Int): Task? {
        val url = "$baseUrl/$id"
        val request = Request.Builder()
            .url(url)
            .build()

        Log.d("ApiClient", "Sending request to $url")

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            Log.e("ApiClient", "Request failed with status code: ${response.code}")

            val json = response.body?.string()
            return gson.fromJson(json, Task::class.java)
        }
    }




    @Throws(IOException::class)
    fun postTask(newTask: Task): Boolean {
        val jsonTask = gson.toJson(newTask)
        val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
        val requestBody = jsonTask.toRequestBody(mediaType)

        val jsonBody = gson.toJson(newTask)
        Log.d("ApiClient", "Sending JSON to backend: $jsonBody")
        val body = jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url(baseUrl)
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("ApiClient", "Failed to send task. Response code: ${response.code}, message: ${response.message}, body ${response.body?.string()}")
                throw IOException("Unexpected code $response")
            }
            return response.isSuccessful
        }
    }

}
