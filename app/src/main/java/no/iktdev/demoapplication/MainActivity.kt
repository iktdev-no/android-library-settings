package no.iktdev.demoapplication

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import no.iktdev.demoapplication.databinding.ActivityMainBinding
import no.iktdev.demoapplication.services.LockscreenWidgetActivity
import no.iktdev.setting.ReactiveSettingsReceiver
import no.iktdev.setting.access.GroupedReactiveSetting

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(LayoutInflater.from(this))
        setContentView(binding.root)

        ReactiveSettingsReceiver(this, object : ReactiveSettingsReceiver.Listener {
            override fun onReactiveGroupSettingsChanged(group: String, key: String, payload: Any?) {
                super.onReactiveGroupSettingsChanged(group, key, payload)
                Toast.makeText(this@MainActivity, "${this@MainActivity::class.java.simpleName} Setting group: $group got changed value for key $key with value $payload", Toast.LENGTH_LONG).show()
            }
        })

        binding.toastMe.setOnClickListener {
            val group = GroupedReactiveSetting("Main", "TestMain").asReactive()
            group.setBoolean(this, !group.getBoolean(this))
        }

        binding.launcTest.setOnClickListener(Uri.parse("https://iktdev.no"))

        binding.goToSettings.setOnClickListener {
            startActivity(Intent(this, SettingActivity::class.java))
        }
        binding.goToSettings.setOnLongClickListener {
            startActivity(Intent(this, SettingActivity2::class.java))
            true
        }

        binding.startLockActivity.setOnClickListener {
            startActivity(Intent(applicationContext, LockscreenWidgetActivity::class.java))
        }


        binding.startScreen.setOnClickListener {
            startActivity(Intent(applicationContext, OverlayActivity::class.java))
            lifecycleScope.launch(Dispatchers.IO) {
                delay(500)
                withContext(Dispatchers.Main) {
                    this@MainActivity.finish()
                }
            }
        }
    }
}