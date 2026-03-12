package com.lautung.smart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lautung.smart.ui.components.BottomNavItem
import com.lautung.smart.ui.components.BottomNavigationBar

@Composable
fun CommunityScreen(
    onNavigateToHome: () -> Unit,
    onNavigateToMall: () -> Unit,
    onNavigateToScene: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 状态栏占位
        Spacer(modifier = Modifier.height(44.dp))

        // 顶部标题
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp, vertical = 12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "智家社区",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }

        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 帖子1
            item {
                CommunityPost(
                    avatarGradient = listOf(Color(0xFF22C55E), Color(0xFF14B8A6)),
                    username = "智能家居爱好者",
                    time = "2小时前",
                    content = "分享一个超省电的空调使用场景：结合温湿度传感器，实现自动开关，冬天再也不用担心电费了！"
                )
            }

            // 帖子2
            item {
                CommunityPost(
                    avatarGradient = listOf(Color(0xFF8B5CF6), Color(0xFFEC4899)),
                    username = "场景配置达人",
                    time = "5小时前",
                    content = "最新版App支持自定义场景图标了，大家快去试试！"
                )
            }

            // 帖子3
            item {
                CommunityPost(
                    avatarGradient = listOf(Color(0xFF3B82F6), Color(0xFF06B6D4)),
                    username = "科技小白",
                    time = "1天前",
                    content = "刚入手了智能门锁，录入指纹只需要3步，太方便了！"
                )
            }

            // 帖子4
            item {
                CommunityPost(
                    avatarGradient = listOf(Color(0xFFF59E0B), Color(0xFFEF4444)),
                    username = "装修日记",
                    time = "2天前",
                    content = "全屋智能布线经验分享，正在装修的朋友可以参考。"
                )
            }

            // 底部占位
            item {
                Spacer(modifier = Modifier.height(100.dp))
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
fun CommunityPost(
    avatarGradient: List<Color>,
    username: String,
    time: String,
    content: String
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1F2937).copy(alpha = 0.4f))
            .padding(20.dp)
    ) {
        Column {
            // 用户信息
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .background(Brush.linearGradient(avatarGradient))
                )
                Spacer(modifier = Modifier.width(12.dp))
                Column {
                    Text(
                        text = username,
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Medium
                    )
                    Text(
                        text = time,
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        fontSize = 12.sp
                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // 内容
            Text(
                text = content,
                color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                fontSize = 14.sp,
                lineHeight = 22.sp
            )
        }
    }
}
