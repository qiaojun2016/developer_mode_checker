import 'dart:ffi';

import 'package:plugin_platform_interface/plugin_platform_interface.dart';

import 'developer_mode_checker_method_channel.dart';

abstract class DeveloperModeCheckerPlatform extends PlatformInterface {
  /// Constructs a DeveloperModeCheckerPlatform.
  DeveloperModeCheckerPlatform() : super(token: _token);

  static final Object _token = Object();

  static DeveloperModeCheckerPlatform _instance =
      MethodChannelDeveloperModeChecker();

  /// The default instance of [DeveloperModeCheckerPlatform] to use.
  ///
  /// Defaults to [MethodChannelDeveloperModeChecker].
  static DeveloperModeCheckerPlatform get instance => _instance;

  /// Platform-specific implementations should set this with their own
  /// platform-specific class that extends [DeveloperModeCheckerPlatform] when
  /// they register themselves.
  static set instance(DeveloperModeCheckerPlatform instance) {
    PlatformInterface.verifyToken(instance, _token);
    _instance = instance;
  }

  Future<String?> getPlatformVersion() {
    throw UnimplementedError('platformVersion() has not been implemented.');
  }

  Future<bool?> isAdbEnabled() {
    throw UnimplementedError('isAdbEnabled() has not been implemented.');
  }

  Future<int?> deviceRooted() {
    throw UnimplementedError('deviceRooted() has not been implemented.');
  }

  Future<bool?> isEmulator() {
    throw UnimplementedError('isEmulator() has not been implemented.');
  }
}
