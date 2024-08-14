package com.example.developer_mode_checker

import android.content.Context
import androidx.annotation.NonNull

import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

/** DeveloperModeCheckerPlugin */
class DeveloperModeCheckerPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel
    private var context: Context? = null

    override fun onAttachedToEngine(flutterPluginBinding: FlutterPlugin.FlutterPluginBinding) {
        channel = MethodChannel(flutterPluginBinding.binaryMessenger, "developer_mode_checker")
        channel.setMethodCallHandler(this)
        context = flutterPluginBinding.applicationContext
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        println("Boot Util ${BootUtil.getDeviceBuildTime()}")
        println("Boot Util ${BootUtil.getFormattedFirstBootTime()}")
        println("Boot Util ${BootUtil.getSystemFileCreationTime()}")
        println("Boot Util ${BootUtil.getInstallNonMarketAppsTime(context!!)}")
        if (call.method == "getPlatformVersion") {
            result.success("Android ${android.os.Build.VERSION.RELEASE}")
        } else if (call.method == "isAdbEnabled") {
            result.success(Util.isAdbEnabled(context!!))
        } else if (call.method == "deviceRooted") {
            result.success(Util.deviceRooted())
        } else if (call.method == "isEmulator") {
            result.success(Util.isEmulator())
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
        context = null
    }
}
