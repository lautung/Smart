# SmartNest 智能家居 Android 应用

基于 HTML 原型图开发的 Android 智能家居应用，使用 Jetpack Compose 和 Kotlin 开发。

## 项目概览

SmartNest 是一款智能家居控制应用，提供设备管理、场景联动、商城购物等功能。

## 项目结构

```
app/src/main/java/com/lautung/smart/
├── MainActivity.kt                    # 主入口
├── data/model/
│   └── Models.kt                    # 数据模型
├── ui/
│   ├── theme/
│   │   ├── Color.kt                 # 颜色定义
│   │   ├── Theme.kt                 # 主题配置
│   │   ├── Type.kt                  # 排版样式
│   │   ├── ThemeManager.kt           # 主题管理器
│   │   └── LanguageManager.kt        # 语言管理器
│   ├── components/
│   │   └── CommonComponents.kt       # 可重用组件
│   └── screens/
│       ├── SmartNestApp.kt           # 应用入口和导航
│       ├── SplashScreen.kt           # 闪屏页面
│       ├── HomeScreen.kt             # 首页
│       ├── SceneScreen.kt            # 智能场景页
│       ├── MallScreen.kt             # 商城页
│       ├── ProfileScreen.kt          # 个人中心页
│       ├── SettingsScreen.kt         # 系统设置页
│       ├── CartScreen.kt             # 购物车页
│       ├── PaymentScreen.kt          # 支付页
│       ├── OrderScreen.kt            # 订单页
│       ├── FavoriteScreen.kt         # 收藏页
│       ├── VoiceSearchScreen.kt      # 语音搜索页
│       ├── ProductDetailScreen.kt    # 产品详情页
│       ├── DeviceManagementScreen.kt # 设备管理页
│       ├── EnergyAnalysisScreen.kt   # 能耗分析页
│       ├── MonitorPreviewScreen.kt   # 监控预览页
│       ├── FamilyMemberScreen.kt     # 家庭成员页
│       ├── CommunityScreen.kt        # 社区页
│       ├── NotificationScreen.kt     # 通知页
│       ├── AddSceneScreen.kt         # 添加场景页
│       ├── SceneConfigScreen.kt      # 场景配置页
│       └── HomeModeConfigScreen.kt   # 回家模式配置页
```

## 已实现功能

### 核心功能
- ✅ 首页 - 用户问候、快捷场景、设备状态
- ✅ 智能场景 - 场景列表、开关控制、场景配置
- ✅ 商城 - 商品展示、购物车、收藏
- ✅ 个人中心 - 订单、收藏、设置
- ✅ 语音助手 - 语音命令输入

### 高级功能
- ✅ **主题切换** - 浅色/深色/跟随系统
- ✅ **国际化** - 中文/英文语言支持
- ✅ 设备管理
- ✅ 能耗分析
- ✅ 家庭监控
- ✅ 家庭成员管理

## 新增功能详情

### 1. 主题切换功能
支持三种主题模式：
- **浅色模式** - 白色背景，深色文字
- **深色模式** - 深色背景，浅色文字
- **跟随系统** - 自动跟随系统设置

使用方式：我的 → 系统设置 → 点击主题选项

### 2. 国际化支持
支持中英文切换：
- 中文 (简体中文)
- English (英文)

使用方式：我的 → 系统设置 → 语言 → 点击切换

字符串资源：
- `res/values/strings.xml` - 中文
- `res/values-en/strings.xml` - 英文

## 技术栈

| 类别 | 技术 |
|------|------|
| 语言 | Kotlin |
| UI | Jetpack Compose |
| 导航 | Navigation Compose |
| 设计 | Material Design 3 |
| 架构 | MVVM 模式 |

## 构建运行

### 环境要求
- Android Studio Hedgehog+
- JDK 11+
- Android SDK 24+

### 构建命令
```bash
# 调试构建
./gradlew assembleDebug

# 运行测试
./gradlew test

# 清理构建
./gradlew clean
```

## 资源文件

### 字符串资源
```bash
# 中文默认
app/src/main/res/values/strings.xml

# 英文
app/src/main/res/values-en/strings.xml
```

### 主题配置
```kotlin
// 主题管理器
ThemeManager.kt - 管理浅色/深色/系统主题

// 语言管理器
LanguageManager.kt - 管理中英文切换
```

## 颜色方案

| 用途 | 颜色 |
|------|------|
| 主色 | 蓝色 (#3B82F6) |
| 辅助色 | 紫色 (#8B5CF6) |
| 成功色 | 绿色 (#22C55E) |
| 深色背景 | (#0A0A0A) |
| 浅色背景 | (#FFFFFF) |

## 版本信息

- 应用版本：v2.1.0
- 目标 SDK：Android 15 (API 36)
- 最低 SDK：Android 7.0 (API 24)

## 待完成

- [ ] 后端 API 集成
- [ ] 用户认证系统
- [ ] 本地数据库 (Room)
- [ ] 单元测试
- [ ] UI 测试

## 项目预览

应用包含以下主要页面：
1. 闪屏 → 2. 首页 → 3. 场景 → 4. 商城 → 5. 我的

---

Happy Coding! 🚀
