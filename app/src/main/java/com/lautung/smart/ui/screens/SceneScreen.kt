package com.lautung.smart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lautung.smart.R
import com.lautung.smart.ui.components.BottomNavigationBar

@Composable
fun SceneScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHomeModeConfig: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToMall: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAddScene: () -> Unit,
    onNavigateToSceneConfig: (String) -> Unit
) {
    // 状态管理
    var isMorningModeEnabled by remember { mutableStateOf(true) }
    var isSleepModeEnabled by remember { mutableStateOf(false) }
    var isHomeModeEnabled by remember { mutableStateOf(true) }
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 顶部导航栏（包含状态栏 padding）
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .statusBarsPadding()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                modifier = Modifier.clickable { onNavigateBack() }
            )
            Text(
                text = stringResource(R.string.smart_scenes),
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Text(
                text = "+",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                modifier = Modifier.clickable { 
                    onNavigateToAddScene()
                }
            )
        }
        
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 起床模式
            item {
                Box(
                    modifier = Modifier.clickable { 
                        onNavigateToSceneConfig("起床模式")
                    }
                ) {
                    SceneItem(
                        icon = "☀️",
                        title = "起床模式",
                        subtitle = "工作日 7:00",
                        description = "开启窗帘，播放音乐，咖啡机启动。",
                        isEnabled = isMorningModeEnabled,
                        backgroundColor = Color(0xFF1E3A8A).copy(alpha = 0.3f),
                        borderColor = Color(0xFF1E40AF).copy(alpha = 0.3f),
                        onToggle = { 
                            isMorningModeEnabled = !isMorningModeEnabled
                        }
                    )
                }
            }
            
            // 睡眠模式
            item {
                Box(
                    modifier = Modifier.clickable { 
                        onNavigateToSceneConfig("睡眠模式")
                    }
                ) {
                    SceneItem(
                        icon = "🌙",
                        title = "睡眠模式",
                        subtitle = "每天 23:00",
                        description = "关闭所有灯，空调调至睡眠温度。",
                        isEnabled = isSleepModeEnabled,
                        backgroundColor = Color(0xFF581C87).copy(alpha = 0.3f),
                        borderColor = Color(0xFF7C3AED).copy(alpha = 0.3f),
                        onToggle = { 
                            isSleepModeEnabled = !isSleepModeEnabled
                        }
                    )
                }
            }
            
            // 回家模式 - 点击进入配置页面
            item {
                Box(
                    modifier = Modifier.clickable { onNavigateToHomeModeConfig() }
                ) {
                    SceneItem(
                        icon = "🏠",
                        title = "回家模式",
                        subtitle = "地理位置触发",
                        description = "开启门厅灯，空调启动，播放欢迎语音。",
                        isEnabled = isHomeModeEnabled,
                        backgroundColor = Color(0xFF14532D).copy(alpha = 0.3f),
                        borderColor = Color(0xFF22C55E).copy(alpha = 0.3f),
                        onToggle = { 
                            isHomeModeEnabled = !isHomeModeEnabled
                        }
                    )
                }
            }
            
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        
        // 底部导航栏
        BottomNavigationBar(
            modifier = Modifier.fillMaxWidth(),
            selectedTab = 2,
            onTabSelected = { index ->
                when (index) {
                    0 -> onNavigateToHome()
                    3 -> onNavigateToMall()
                    4 -> onNavigateToProfile()
                }
            }
        )
    }
}

@Composable
fun SceneItem(
    icon: String,
    title: String,
    subtitle: String,
    description: String,
    isEnabled: Boolean,
    backgroundColor: Color,
    borderColor: Color,
    onToggle: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(backgroundColor)
            .padding(20.dp)
    ) {
        Column {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    Text(icon, fontSize = 32.sp)
                    Column {
                        Text(
                            text = title,
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                        Text(
                            text = subtitle,
                            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                            fontSize = 14.sp
                        )
                    }
                }
                // 开关
                Box(
                    modifier = Modifier
                        .size(48.dp, 24.dp)
                        .clip(RoundedCornerShape(12.dp))
                        .background(if (isEnabled) Color(0xFF3B82F6) else MaterialTheme.colorScheme.surfaceVariant)
                        .clickable { onToggle() },
                    contentAlignment = if (isEnabled) Alignment.CenterEnd else Alignment.CenterStart
                ) {
                    Box(
                        modifier = Modifier
                            .size(20.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(Color.White)
                            .padding(2.dp)
                    )
                }
            }
            Spacer(modifier = Modifier.height(12.dp))
            Text(
                text = description,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                fontSize = 14.sp
            )
        }
    }
}
