package dev.mjpark.krtv

import android.content.Context
import android.util.Log
import java.io.File
import java.util.concurrent.TimeUnit

object Hy2ProxyManager {
    private const val TAG = "Hy2ProxyManager"
    private const val PREFS = "krtv_hy2"
    private const val KEY_URI = "share_uri"
    private const val SOCKS_PORT = 10808
    private var process: Process? = null

    val isRunning: Boolean
        get() = process?.isAlive == true

    fun savedUri(context: Context): String =
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE).getString(KEY_URI, "") ?: ""

    @Synchronized
    fun connect(context: Context, uri: String): Result<Unit> = runCatching {
        val input = uri.trim()
        require(
            input.startsWith("hy2://", ignoreCase = true) ||
                input.startsWith("hysteria2://", ignoreCase = true)
        ) { "Invalid Hy2 URI" }
        val normalizedUri = input.replaceFirst(Regex("(?i)^hy2://"), "hysteria2://")
        disconnect()

        val config = File(context.filesDir, "krtv-hy2.yaml")
        config.writeText(
            """
            server: '${normalizedUri.replace("'", "''")}'
            socks5:
              listen: 127.0.0.1:$SOCKS_PORT
            fastOpen: true
            quic:
              maxIdleTimeout: 30s
              keepAlivePeriod: 20s
            """.trimIndent()
        )
        val binary = File(context.applicationInfo.nativeLibraryDir, "libhysteria.so")
        check(binary.exists()) { "Hy2 core not found" }
        process = ProcessBuilder(binary.absolutePath, "-c", config.absolutePath)
            .redirectErrorStream(true)
            .start()
        Thread {
            process?.inputStream?.bufferedReader()?.useLines { lines ->
                lines.forEach { Log.d(TAG, it) }
            }
        }.start()
        Thread.sleep(350)
        check(process?.isAlive == true) { "Hy2 core failed to start" }
        context.getSharedPreferences(PREFS, Context.MODE_PRIVATE)
            .edit().putString(KEY_URI, input).apply()
        SP.proxy = "socks5://127.0.0.1:$SOCKS_PORT"
    }

    @Synchronized
    fun disconnect() {
        process?.destroy()
        try {
            process?.waitFor(500, TimeUnit.MILLISECONDS)
        } catch (_: InterruptedException) {
            Thread.currentThread().interrupt()
        }
        if (process?.isAlive == true) process?.destroyForcibly()
        process = null
        runCatching { SP.proxy = SP.DEFAULT_PROXY }
    }
}
