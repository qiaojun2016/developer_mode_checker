import 'developer_mode_checker_platform_interface.dart';

class DeveloperModeChecker {
  Future<String?> getPlatformVersion() {
    return DeveloperModeCheckerPlatform.instance.getPlatformVersion();
  }

  Future<bool?> isAdbEnabled() {
    return DeveloperModeCheckerPlatform.instance.isAdbEnabled();
  }

  Future<bool?> isEmulator() {
    return DeveloperModeCheckerPlatform.instance.isEmulator();
  }

  Future<int?> deviceRooted() {
    return DeveloperModeCheckerPlatform.instance.deviceRooted();
  }
}
