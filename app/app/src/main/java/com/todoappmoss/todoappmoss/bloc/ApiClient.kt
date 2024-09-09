import android.util.Log
import com.todoappmoss.todoappmoss.data.model.Task
import com.google.common.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import com.todoappmoss.todoappmoss.data.model.User
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.IOException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class ApiClient {

    // Initialize OkHttpClient and Gson
    private val client = OkHttpClient()
    private val gson = Gson()

    // Define your API endpoints here
    private val baseUrl = "https://mossrestapi-esdkf7hpc3fmadf4.germanywestcentral-01.azurewebsites.net/api/Tasks"
    private val userUrl = "https://mossrestapi-esdkf7hpc3fmadf4.germanywestcentral-01.azurewebsites.net/api/Users"

    // Fetch tasks from the API
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

    // Fetch a specific task by ID
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

    // Register a new user
    @Throws(IOException::class)
    suspend fun registerUser(username: String, email: String, password: String): Boolean {
        val newUser = mapOf(
            "Username" to username,
            "Email" to email,
            "PasswordHash" to password,
            "CreatedAt" to LocalDateTime.now().format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
        )

        val jsonBody = gson.toJson(newUser)
        val requestBody = jsonBody.toRequestBody("application/json; charset=utf-8".toMediaType())

        val request = Request.Builder()
            .url("https://mossrestapi-esdkf7hpc3fmadf4.germanywestcentral-01.azurewebsites.net/api/Users")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("ApiClient", "Failed to register user. Response code: ${response.code}, body: ${response.body?.string()}")
                return false
            }
            return true
        }
    }

    // Fetch tasks for a specific date
    @Throws(IOException::class)
    fun getTasksForDate(date: String): List<Task> {
        val url = "$baseUrl?date=$date"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val json = response.body?.string()
            val taskListType = object : TypeToken<List<Task>>() {}.type
            return gson.fromJson(json, taskListType)
        }
    }

    // Update a task
    @Throws(IOException::class)
    suspend fun updateTask(updatedTask: Task): Boolean {
        return try {
            val jsonTask = gson.toJson(updatedTask)
            val mediaType = "application/json; charset=utf-8".toMediaTypeOrNull()
            val requestBody = jsonTask.toRequestBody(mediaType)

            val request = Request.Builder()
                .url("$baseUrl/${updatedTask.id}")
                .put(requestBody)
                .build()

            client.newCall(request).execute().use { response ->
                response.isSuccessful
            }
        } catch (e: IOException) {
            Log.e("ApiClient", "Fehler beim Aktualisieren der Aufgabe: ${e.message}")
            false
        }
    }

    // Fetch all users
    @Throws(IOException::class)
    fun getUsers(): List<User> {
        val request = Request.Builder()
            .url(userUrl)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val json = response.body?.string()
            return gson.fromJson(json, object : TypeToken<List<User>>() {}.type)
        }
    }


    // Delete a task
    @Throws(IOException::class)
    fun deleteTask(id: Int): Boolean {
        val request = Request.Builder()
            .url("$baseUrl/$id")
            .delete()
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                Log.e("ApiClient", "Failed to delete task. Response code: ${response.code}, message: ${response.message}")
                throw IOException("Unexpected code $response")
            }
            return response.isSuccessful
        }
    }

    // Create a new task
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


    // Login Request
    @Throws(IOException::class)
    fun login(email: String, password: String): Boolean {
        val json = gson.toJson(mapOf("email" to email, "password" to password))
        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url("$userUrl/login")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            Log.d("ApiClient", "Login response code: ${response.code}")
            return if (response.isSuccessful) {
                Log.d("ApiClient", "Login successful")
                val responseBody = response.body?.string()
                Log.d("ApiClient", "Login successful: $responseBody")
                true
            } else {
                Log.e("ApiClient", "Login failed: ${response.code}")
                false
            }
        }
    }

    // Registration Request
    @Throws(IOException::class)
    fun register(username: String, email: String, password: String): Boolean {
        val json = gson.toJson(
            mapOf("username" to username, "email" to email, "password" to password)
        )
        val requestBody = json.toRequestBody("application/json; charset=utf-8".toMediaTypeOrNull())

        val request = Request.Builder()
            .url(userUrl)
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            return if (response.isSuccessful) {
                Log.d("ApiClient", "Registration successful")
                true
            } else {
                Log.e("ApiClient", "Registration failed: ${response.code}")
                false
            }
        }
    }
}

