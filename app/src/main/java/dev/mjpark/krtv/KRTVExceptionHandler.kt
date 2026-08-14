package dev.mjpark.krtv

import android.content.Context
import android.os.Build
import android.util.Log
import kotlin.system.exitProcess

class KRTVExceptionHandler(private val context: Context) : Thread.UncaughtExceptionHandler {
    override fun uncaughtException(t: Thread, e: Throwable) {
        val crashInfo =
            "APP: ${context.appVersionName}, PRODUCT: ${Build.PRODUCT}, DEVICE: ${Build.DEVICE}, SUPPORTED_ABIS: ${Build.SUPPORTED_ABIS.joinToString()}, BOARD: ${Build.BOARD}, MANUFACTURER: ${Build.MANUFACTURER}, MODEL: ${Build.MODEL}, VERSION: ${Build.VERSION.SDK_INT}\nThread: ${t.name}\nException: ${e.message}\nStackTrace: ${Log.getStackTraceString(e)}\n"

        // Keep crash details on-device in Logcat. KRTV does not transmit device or crash data.
        Log.e(TAG, crashInfo)
        android.os.Process.killProcess(android.os.Process.myPid())
        exitProcess(1)
    }

    companion object {
        private const val TAG = "KRTVException"
    }
}
