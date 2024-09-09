package at.fhjoanneum.todoappmoss

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import at.fhjoanneum.todoappmoss.ui.login.LoginActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        Handler(Looper.getMainLooper()).postDelayed({
            //val intent = Intent(this, TaskBoardActivity::class.java)
            val intent = Intent(this, LoginActivity::class.java)

            startActivity(intent)
            finish() // Close the SplashActivity so it won't come back on back press
        }, 3000)
    }
}