package com.lautung.smart.ui.screens

import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import kotlinx.coroutines.launch
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lautung.smart.R
import com.lautung.smart.ui.components.BottomNavigationBar
import com.lautung.smart.ui.theme.Language
import com.lautung.smart.ui.theme.LanguageManager
import com.lautung.smart.ui.theme.ThemeManager
import com.lautung.smart.ui.theme.ThemeMode
import com.lautung.smart.ui.viewmodel.AuthViewModel

@Composable
fun SettingsScreen(
    themeManager: ThemeManager,
    languageManager: LanguageManager,
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToMall: () -> Unit,
    onNavigateToScene: () -> Unit,
    onNavigateToProfile: () -> Unit,
    authViewModel: AuthViewModel = hiltViewModel()
) {
    // 本地状态用于即时更新 UI
    var selectedThemeMode by remember { mutableStateOf(ThemeMode.SYSTEM) }
    val currentLanguage by languageManager.language.collectAsState()
    val context = LocalContext.current
    val scope = rememberCoroutineScope()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 顶部导航（包含状态栏 padding）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .padding(horizontal = 16.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .clip(RoundedCornerShape(50))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = true)
                    ) { onNavigateBack() }
                    .background(Color.White.copy(alpha = 0.1f))
                    .semantics { contentDescription = "返回上一页" },
                contentAlignment = Alignment.Center
            ) {
                Text("←", color = MaterialTheme.colorScheme.onBackground, fontSize = 20.sp)
            }
            Spacer(modifier = Modifier.width(16.dp))
            Text(
                text = "系统设置",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // 外观预览区
            item {
                Column {
                    Text(
                        text = "外观预览",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        // 浅色
                        val lightBorderWidth by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.LIGHT) 3.dp else 0.dp,
                            animationSpec = tween(300),
                            label = "lightBorder"
                        )
                        val lightShadow by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.LIGHT) 8.dp else 0.dp,
                            animationSpec = tween(300),
                            label = "lightShadow"
                        )
                        val lightWidth by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.LIGHT) 96.dp else 84.dp,
                            animationSpec = tween(300),
                            label = "lightWidth"
                        )
                        val lightHeight by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.LIGHT) 124.dp else 112.dp,
                            animationSpec = tween(300),
                            label = "lightHeight"
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(bounded = true)
                            ) {
                                selectedThemeMode = ThemeMode.LIGHT
                                scope.launch { themeManager.setThemeMode(ThemeMode.LIGHT) }
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(lightWidth)
                                    .height(lightHeight)
                                    .shadow(lightShadow, RoundedCornerShape(12.dp), spotColor = Color(0xFF3B82F6))
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color.White)
                                    .border(lightBorderWidth, Color(0xFF3B82F6), RoundedCornerShape(12.dp))
                                    .padding(8.dp)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(16.dp)
                                            .background(Color(0xFFE5E7EB), RoundedCornerShape(4.dp))
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.6f)
                                            .height(8.dp)
                                            .background(Color(0xFFF3F4F6), RoundedCornerShape(4.dp))
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("☀️", fontSize = 16.sp)
                            Text(
                                text = "浅色",
                                color = if (selectedThemeMode == ThemeMode.LIGHT) Color(0xFF3B82F6) else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                        }

                        // 深色
                        val darkBorderWidth by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.DARK) 3.dp else 0.dp,
                            animationSpec = tween(300),
                            label = "darkBorder"
                        )
                        val darkShadow by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.DARK) 8.dp else 0.dp,
                            animationSpec = tween(300),
                            label = "darkShadow"
                        )
                        val darkWidth by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.DARK) 96.dp else 84.dp,
                            animationSpec = tween(300),
                            label = "darkWidth"
                        )
                        val darkHeight by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.DARK) 124.dp else 112.dp,
                            animationSpec = tween(300),
                            label = "darkHeight"
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(bounded = true)
                            ) {
                                selectedThemeMode = ThemeMode.DARK
                                scope.launch { themeManager.setThemeMode(ThemeMode.DARK) }
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(darkWidth)
                                    .height(darkHeight)
                                    .shadow(darkShadow, RoundedCornerShape(12.dp), spotColor = Color(0xFF3B82F6))
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(Color(0xFF1F2937))
                                    .border(darkBorderWidth, Color(0xFF3B82F6), RoundedCornerShape(12.dp))
                                    .padding(8.dp)
                            ) {
                                Column(
                                    verticalArrangement = Arrangement.spacedBy(8.dp)
                                ) {
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .height(16.dp)
                                            .background(Color(0xFF374151), RoundedCornerShape(4.dp))
                                    )
                                    Box(
                                        modifier = Modifier
                                            .fillMaxWidth(0.6f)
                                            .height(8.dp)
                                            .background(Color(0xFF4B5563), RoundedCornerShape(4.dp))
                                    )
                                }
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("🌙", fontSize = 16.sp)
                            Text(
                                text = "深色",
                                color = if (selectedThemeMode == ThemeMode.DARK) Color(0xFF3B82F6) else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                        }

                        // 跟随系统
                        val systemBorderWidth by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.SYSTEM) 3.dp else 0.dp,
                            animationSpec = tween(300),
                            label = "systemBorder"
                        )
                        val systemShadow by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.SYSTEM) 8.dp else 0.dp,
                            animationSpec = tween(300),
                            label = "systemShadow"
                        )
                        val systemWidth by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.SYSTEM) 96.dp else 84.dp,
                            animationSpec = tween(300),
                            label = "systemWidth"
                        )
                        val systemHeight by animateDpAsState(
                            targetValue = if (selectedThemeMode == ThemeMode.SYSTEM) 124.dp else 112.dp,
                            animationSpec = tween(300),
                            label = "systemHeight"
                        )
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            modifier = Modifier.clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = ripple(bounded = true)
                            ) {
                                selectedThemeMode = ThemeMode.SYSTEM
                                scope.launch { themeManager.setThemeMode(ThemeMode.SYSTEM) }
                            }
                        ) {
                            Box(
                                modifier = Modifier
                                    .width(systemWidth)
                                    .height(systemHeight)
                                    .shadow(systemShadow, RoundedCornerShape(12.dp), spotColor = Color(0xFF3B82F6))
                                    .clip(RoundedCornerShape(12.dp))
                                    .background(
                                        Brush.verticalGradient(
                                            listOf(Color(0xFFD1D5DB), Color(0xFF1F2937))
                                        )
                                    )
                                    .border(systemBorderWidth, Color(0xFF3B82F6), RoundedCornerShape(12.dp)),
                                contentAlignment = Alignment.Center
                            ) {
                                Text("📱", fontSize = 32.sp, color = Color.White.copy(alpha = 0.5f))
                            }
                            Spacer(modifier = Modifier.height(8.dp))
                            Text("📱", fontSize = 16.sp)
                            Text(
                                text = "跟随系统",
                                color = if (selectedThemeMode == ThemeMode.SYSTEM) Color(0xFF3B82F6) else MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                                fontSize = 12.sp
                            )
                        }
                    }
                }
            }

            // 通知设置
            item {
                Column {
                    Text(
                        text = "通知",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    SettingsItem(
                        icon = "🔔",
                        title = "推送通知",
                        hasToggle = true,
                        onClick = { }
                    )

                    SettingsItem(
                        icon = "⚠️",
                        title = "设备告警",
                        hasToggle = true,
                        onClick = { }
                    )
                }
            }

            // 网络与语言
            item {
                Column {
                    Text(
                        text = "网络",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    SettingsItem(
                        icon = "📶",
                        title = "网络设置",
                        subtitle = "Home_5G",
                        hasArrow = true,
                        onClick = { }
                    )

                    SettingsItem(
                        icon = "🌐",
                        title = stringResource(R.string.language),
                        subtitle = currentLanguage.displayName,
                        hasArrow = true,
                        onClick = { 
                            languageManager.setLanguage(
                                if (currentLanguage == Language.CHINESE) Language.ENGLISH 
                                else Language.CHINESE
                            )
                        }
                    )
                }
            }

            // 安全设置
            item {
                Column {
                    Text(
                        text = "安全",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    SettingsItem(
                        icon = "🛡️",
                        title = "隐私设置",
                        hasArrow = true,
                        onClick = { }
                    )
                }
            }

            // 关于
            item {
                Column {
                    Text(
                        text = "关于",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))

                    SettingsItem(
                        icon = "ℹ️",
                        title = "版本信息",
                        subtitle = "v2.1.0",
                        hasArrow = true,
                        onClick = { }
                    )
                }
            }

            // 退出登录按钮
            item {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(Color(0xFFEF4444).copy(alpha = 0.1f))
                        .clickable { authViewModel.logout() }
                        .padding(vertical = 16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "退出登录",
                        color = Color(0xFFEF4444),
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }

            // 底部占位
            item {
                Spacer(modifier = Modifier.height(84.dp))
            }
        }

        // 底部导航栏
        BottomNavigationBar(
            modifier = Modifier.fillMaxWidth(),
            selectedTab = 0,
            onTabSelected = { index ->
                when (index) {
                    0 -> onNavigateToHome()
                    2 -> onNavigateToScene()
                    3 -> onNavigateToMall()
                    4 -> onNavigateToProfile()
                }
            }
        )
    }
}

@Composable
fun SettingsItem(
    icon: String,
    title: String,
    subtitle: String? = null,
    hasToggle: Boolean = false,
    hasArrow: Boolean = false,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(icon, fontSize = 20.sp)
            Spacer(modifier = Modifier.width(12.dp))
            Column {
                Text(
                    text = title,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 16.sp
                )
                if (subtitle != null) {
                    Text(
                        text = subtitle,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                }
            }
        }

        if (hasToggle) {
            Box(
                modifier = Modifier
                    .width(48.dp)
                    .height(24.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color(0xFF3B82F6))
                    .clickable(
                        interactionSource = remember { MutableInteractionSource() },
                        indication = ripple(bounded = true)
                    ) { onClick() }
            ) {
                Box(
                    modifier = Modifier
                        .offset(x = 24.dp, y = 2.dp)
                        .size(20.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(Color.White)
                )
            }
        } else if (hasArrow) {
            Text("›", color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.7f), fontSize = 20.sp)
        }
    }
}
