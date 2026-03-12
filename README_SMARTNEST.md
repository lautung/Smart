# SmartNest Android 应用

这是一个基于 HTML 原型图创建的 Android 智能家居应用，使用 Jetpack Compose 和 Kotlin 开发。

## 项目结构

```
app/src/main/java/com/lautung/smart/
├── MainActivity.kt                    # 主 Activity
├── data/model/
│   └── Models.kt                      # 数据模型
├── ui/
│   ├── theme/
│   │   ├── Color.kt                   # 颜色定义
│   │   ├── Theme.kt                   # 主题配置
│   │   └── Type.kt                    # 排版样式
│   ├── components/
│   │   └── CommonComponents.kt        # 可重用组件
│   └── screens/
│       ├── SmartNestApp.kt            # 应用入口和导航
│       ├── SplashScreen.kt            # 闪屏页面
│       ├── HomeScreen.kt              # 首页
│       ├── SceneScreen.kt             # 智能场景页
│       ├── MallScreen.kt              # 商城页
│       ├── ProfileScreen.kt           # 个人中心页
│       ├── VoiceSearchScreen.kt       # 语音搜索页
│       └── ProductDetailScreen.kt     # 产品详情页
```

## 已实现的功能

### 1. 闪屏页 (Splash Screen)
- 渐变背景
- Logo 动画
- 加载进度条
- 自动跳转到首页

### 2. 首页 (Home Screen)
- 用户问候和时间显示
- 快捷场景卡片（起床模式、睡眠模式）
- 设备状态概览（空调、灯光、门锁）
- 底部导航栏

### 3. 智能场景页 (Scene Screen)
- 场景列表展示
- 场景开关控制
- 场景描述信息

### 4. 商城页 (Mall Screen)
- 商品网格展示
- 商品卡片（图片、名称、描述、价格）
- 购买按钮

### 5. 个人中心页 (Profile Screen)
- 用户信息展示
- 菜单项（订单、收藏、设置）
- 退出登录按钮

### 6. 语音搜索页 (Voice Search)
- 语音输入动画
- 示例指令快捷按钮

## 技术栈

- **语言**: Kotlin
- **UI 框架**: Jetpack Compose
- **导航**: Navigation Compose
- **架构**: MVVM 模式（基础结构）
- **Material Design 3**: 现代化 UI 组件

## 设计特点

- **深色主题**: 采用深色渐变背景，营造科技感
- **渐变效果**: 大量使用渐变背景增强视觉效果
- **卡片布局**: 圆角卡片设计，层次分明
- **图标表情**: 使用 Emoji 作为图标，简洁直观
- **流畅动画**: 页面切换和交互动画

## 构建和运行

### 系统要求
- Android Studio Hedgehog 或更高版本
- JDK 11 或更高版本
- Android SDK 24+

### 构建步骤
1. 克隆项目到本地
2. 使用 Android Studio 打开项目
3. 等待 Gradle 同步完成
4. 运行到模拟器或真机

### 构建命令
```bash
# 调试构建
./gradlew assembleDebug

# 运行测试
./gradlew test

# 清理构建
./gradlew clean
```

## 后续可扩展功能

1. **设备管理页**: 添加/删除/控制智能设备
2. **能耗分析页**: 展示用电统计图表
3. **监控预览页**: 摄像头实时画面
4. **家庭成员页**: 管理家庭成员权限
5. **购物车页**: 商品结算流程
6. **支付页面**: 集成支付功能
7. **社区页**: 用户交流分享

## 数据模型

当前包含以下数据模型：
- `Device`: 设备信息
- `Scene`: 场景配置
- `Product`: 商品信息
- `User`: 用户信息
- `EnergyUsage`: 能耗数据
- `CommunityPost`: 社区动态

## 注意事项

1. 当前版本为 UI 展示版本，数据为硬编码
2. 实际项目需要集成后端 API
3. 建议添加本地数据库（Room）缓存数据
4. 需要实现用户认证系统
5. 建议添加单元测试和 UI 测试

## 颜色方案

主色调基于原型图提取：
- 主色：蓝色 (#3B82F6)
- 辅助色：紫色 (#8B5CF6)
- 成功色：绿色 (#22C55E)
- 背景：深色渐变 (#000000 → #1A0B2E)

## 版本信息

- 应用版本：v2.1.0
- 目标 SDK：Android 15 (API 36)
- 最低 SDK：Android 7.0 (API 24)
