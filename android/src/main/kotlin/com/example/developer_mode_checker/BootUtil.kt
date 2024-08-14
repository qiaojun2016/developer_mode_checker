package com.example.developer_mode_checker

import android.content.Context
import android.os.SystemClock
import android.provider.Settings
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale


object BootUtil {
    // 获取设备编译时间
    fun getDeviceBuildTime(): String? {
        try {
            // 获取系统属性中的build时间
            val buildDate = System.getProperty("ro.build.date.utc")
            if (buildDate != null) {
                val timeInMillis = buildDate.toLong() * 1000L
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                return dateFormat.format(Date(timeInMillis))
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

    // 检查某些系统文件的构建时间
    fun getSystemFileCreationTime(): String? {
        try {
            val file = File("/data/system")
            val lastModified = file.lastModified()
            val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
            return dateFormat.format(Date(lastModified))
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

    fun getInstallNonMarketAppsTime(context: Context): String? {
        try {
            val firstInstallTime: Long = Settings.Secure.getLong(
                context.contentResolver,
                Settings.Secure.INSTALL_NON_MARKET_APPS,
                0
            )
            if (firstInstallTime > 0) {
                val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
                return dateFormat.format(Date(firstInstallTime))
            }
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return null
    }

    private fun getFirstBootTime(): Long {
        // 获取设备启动以来的时间（毫秒）
        val elapsedTime = SystemClock.elapsedRealtime()

        // 获取当前系统时间（毫秒）
        val currentTime = System.currentTimeMillis()

        // 第一次开机时间 = 当前时间 - 设备启动以来的时间
        return currentTime - elapsedTime
    }

    fun getFormattedFirstBootTime(): String? {
        val firstBootTime = getFirstBootTime()

        // 将毫秒时间转换为日期格式
        val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault())
        val date = Date(firstBootTime)
        return dateFormat.format(date)
    }
}