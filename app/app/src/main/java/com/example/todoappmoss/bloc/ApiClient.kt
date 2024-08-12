import com.example.todoappmoss.data.model.ToDoItem
import com.google.common.reflect.TypeToken
import okhttp3.OkHttpClient
import okhttp3.Request
import com.google.gson.Gson
import java.io.IOException

class ApiClient {

    private val client = OkHttpClient()
    private val gson = Gson()
    private val baseUrl = "https://10.0.2.2:7259/api/todoitems"

    @Throws(IOException::class)
    fun getTodoItems(): List<ToDoItem> {
        val request = Request.Builder()
            .url(baseUrl)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val json = response.body?.string()
            val todoListType = object : TypeToken<List<ToDoItem>>() {}.type

            return gson.fromJson(json, todoListType)
        }
    }

    @Throws(IOException::class)
    fun getTodoItemById(id: Int): ToDoItem? {
        val url = "$baseUrl/$id"
        val request = Request.Builder()
            .url(url)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) throw IOException("Unexpected code $response")

            val json = response.body?.string()
            return gson.fromJson(json, ToDoItem::class.java)
        }
    }
}
