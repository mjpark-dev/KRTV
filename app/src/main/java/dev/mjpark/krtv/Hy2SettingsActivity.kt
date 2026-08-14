package dev.mjpark.krtv

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import dev.mjpark.krtv.databinding.ActivityHy2SettingsBinding
import kotlin.concurrent.thread

class Hy2SettingsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHy2SettingsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHy2SettingsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.hy2Uri.setText(Hy2ProxyManager.savedUri(this))
        refresh()

        binding.connect.setOnClickListener {
            binding.status.text = getString(R.string.hy2_connecting)
            binding.connect.isEnabled = false
            thread {
                val result = Hy2ProxyManager.connect(this, binding.hy2Uri.text.toString())
                runOnUiThread {
                    binding.connect.isEnabled = true
                    if (result.isFailure) {
                        binding.status.text = getString(R.string.hy2_failed, result.exceptionOrNull()?.message ?: "")
                    } else {
                        refresh()
                    }
                }
            }
        }
        binding.disconnect.setOnClickListener {
            Hy2ProxyManager.disconnect()
            refresh()
        }
        binding.back.setOnClickListener { finish() }
    }

    private fun refresh() {
        binding.status.setText(if (Hy2ProxyManager.isRunning) R.string.hy2_status_on else R.string.hy2_status_off)
    }
}
