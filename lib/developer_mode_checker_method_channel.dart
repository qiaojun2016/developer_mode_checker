import 'package:flutter/foundation.dart';
import 'package:flutter/services.dart';

import 'developer_mode_checker_platform_interface.dart';

/// An implementation of [DeveloperModeCheckerPlatform] that uses method channels.
class MethodChannelDeveloperModeChecker extends DeveloperModeCheckerPlatform {
  /// The method channel used to interact with the native platform.
  @visibleForTesting
  final methodChannel = const MethodChannel('developer_mode_checker');

  @override
  Future<String?> getPlatformVersion() async {
    final version =
        await methodChannel.invokeMethod<String>('getPlatformVersion');
    return version;
  }

  @override
  Future<bool?> isAdbEnabled() async {
    return await methodChannel.invokeMethod<bool?>('isAdbEnabled');
  }

  @override
  Future<bool?> isEmulator() async {
    return await methodChannel.invokeMethod<bool?>('isEmulator');
  }

  @override
  Future<int?> deviceRooted() async {
    return await methodChannel.invokeMethod<int?>('deviceRooted');
  }
}
