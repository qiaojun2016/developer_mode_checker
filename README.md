# developer_mode_checker

一个用于检查 Android 设备是否root、是否处于开发者模式的插件

## 使用

检查是否启用ADB调试:
```dart
DeveloperModeChecker().isAdbEnabled();
```

检查是否收拾模拟器环境:
```dart
DeveloperModeChecker().isEmulator();
```
检查是否被root，返回0表示没有被root
```dart
DeveloperModeChecker().deviceRooted();
```
