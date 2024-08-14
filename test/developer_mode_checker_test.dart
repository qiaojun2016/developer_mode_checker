import 'dart:async';
import 'dart:ffi';

import 'package:flutter_test/flutter_test.dart';
import 'package:developer_mode_checker/developer_mode_checker.dart';
import 'package:developer_mode_checker/developer_mode_checker_platform_interface.dart';
import 'package:developer_mode_checker/developer_mode_checker_method_channel.dart';
import 'package:plugin_platform_interface/plugin_platform_interface.dart';

class MockDeveloperModeCheckerPlatform
    with MockPlatformInterfaceMixin
    implements DeveloperModeCheckerPlatform {
  @override
  Future<String?> getPlatformVersion() => Future.value('42');

  @override
  Future<bool?> isAdbEnabled() async {
    return true;
  }

  @override
  Future<int?> deviceRooted() {
    // TODO: implement deviceRooted
    throw UnimplementedError();
  }

  @override
  Future<bool?> isEmulator() {
    // TODO: implement isEmulator
    throw UnimplementedError();
  }
}

void main() {
  final DeveloperModeCheckerPlatform initialPlatform =
      DeveloperModeCheckerPlatform.instance;

  test('$MethodChannelDeveloperModeChecker is the default instance', () {
    expect(initialPlatform, isInstanceOf<MethodChannelDeveloperModeChecker>());
  });

  test('getPlatformVersion', () async {
    DeveloperModeChecker developerModeCheckerPlugin = DeveloperModeChecker();
    MockDeveloperModeCheckerPlatform fakePlatform =
        MockDeveloperModeCheckerPlatform();
    DeveloperModeCheckerPlatform.instance = fakePlatform;

    expect(await developerModeCheckerPlugin.getPlatformVersion(), '42');
  });
}
