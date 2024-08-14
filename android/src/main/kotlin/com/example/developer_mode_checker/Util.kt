package com.example.developer_mode_checker

import android.content.Context
import android.os.Build
import android.provider.Settings
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader


object Util {
    fun isAdbEnabled(context: Context): Boolean {
        return Settings.Secure.getInt(context.contentResolver, Settings.Secure.ADB_ENABLED, 0) > 0
    }

    fun deviceRooted(): Int {
        if (checkRoot1()) return 1
        if (checkRoot2()) return 2
        if (checkRoot3()) return 3
        return 0
    }

    private fun checkRoot1(): Boolean {
        val buildTags = Build.TAGS
        return buildTags != null && buildTags.contains("test-keys")
    }

    private fun checkRoot2(): Boolean {
        try {
            val paths = arrayOf(
                "/system/app/Superuser.apk",
                "/sbin/su",
                "/system/bin/su",
                "/system/xbin/su",
                "/data/local/xbin/su",
                "/data/local/bin/su",
                "/system/sd/xbin/su",
                "/system/bin/failsafe/su",
                "/data/local/su"
            )
            for (path in paths) {
                if (File(path).exists()) {
                    return true
                }
            }
        } catch (e: Exception) {
            // Ignore exception
        }
        return false
    }

    private fun checkRoot3(): Boolean {
        var process: Process? = null
        return try {
            process = Runtime.getRuntime().exec(arrayOf("/system/xbin/which", "su"))
            val `in` = BufferedReader(InputStreamReader(process.inputStream))
            `in`.readLine() != null
        } catch (e: java.lang.Exception) {
            false
        } finally {
            process?.destroy()
        }
    }

    fun isEmulator(): Boolean {
        return checkEmulatorFiles() || checkEmulatorBuildProperties() || checkQEmuDrivers()
    }

    private fun checkEmulatorFiles(): Boolean {
        val knownEmulatorFiles = arrayOf(
            "/dev/socket/qemud",
            "/dev/qemu_pipe",
            "/system/lib/libc_malloc_debug_qemu.so",
            "/sys/qemu_trace",
            "/system/bin/qemu-props"
        )
        for (file in knownEmulatorFiles) {
            if (File(file).exists()) {
                return true
            }
        }
        return false
    }

    private fun checkEmulatorBuildProperties(): Boolean {
        val brand = Build.BRAND
        val device = Build.DEVICE
        val product = Build.PRODUCT
        val model = Build.MODEL
        val hardware = Build.HARDWARE
        val fingerprint = Build.FINGERPRINT
        return Build.FINGERPRINT.startsWith("generic") ||
                brand.startsWith("generic") || brand == "google" ||
                device.startsWith("generic") ||
                model.contains("Emulator") ||
                product.contains("sdk") || hardware == "goldfish" || hardware == "ranchu" || hardware == "qemu"
    }

    private fun checkQEmuDrivers(): Boolean {
        return "goldfish" == getSystemProperty("ro.hardware") || "ranchu" == getSystemProperty("ro.hardware")
    }

    private fun getSystemProperty(propertyName: String): String? {
        var property: String? = null
        try {
            val systemProperties = Class.forName("android.os.SystemProperties")
            val get = systemProperties.getMethod("get", String::class.java)
            property = get.invoke(systemProperties, propertyName) as String
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        return property
    }
}