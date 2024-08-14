import 'package:flutter/services.dart';
import 'package:flutter_test/flutter_test.dart';
import 'package:developer_mode_checker/developer_mode_checker_method_channel.dart';

void main() {
  TestWidgetsFlutterBinding.ensureInitialized();

  MethodChannelDeveloperModeChecker platform = MethodChannelDeveloperModeChecker();
  const MethodChannel channel = MethodChannel('developer_mode_checker');

  setUp(() {
    TestDefaultBinaryMessengerBinding.instance?.defaultBinaryMessenger.setMockMethodCallHandler(
      channel,
      (MethodCall methodCall) async {
        return '42';
      },
    );
  });

  tearDown(() {
    TestDefaultBinaryMessengerBinding.instance?.defaultBinaryMessenger.setMockMethodCallHandler(channel, null);
  });

  test('getPlatformVersion', () async {
    expect(await platform.getPlatformVersion(), '42');
  });
}
