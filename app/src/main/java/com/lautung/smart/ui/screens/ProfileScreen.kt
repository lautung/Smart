package com.lautung.smart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lautung.smart.ui.components.BottomNavigationBar

@Composable
fun ProfileScreen(
    onNavigateBack: () -> Unit,
    onNavigateToSettings: () -> Unit,
    onNavigateToCommunity: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToMall: () -> Unit,
    onNavigateToScene: () -> Unit,
    onNavigateToOrder: () -> Unit,
    onNavigateToFavorite: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .navigationBarsPadding()
        ) {
            // 用户信息
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(24.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(96.dp)
                            .clip(RoundedCornerShape(48.dp))
                            .background(
                                androidx.compose.ui.graphics.Brush.linearGradient(
                                    listOf(Color(0xFF06B6D4), Color(0xFF3B82F6))
                                )
                            )
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                    Text(
                        text = "Alex Chen",
                        color = MaterialTheme.colorScheme.onBackground,
                        fontSize = 24.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "alex@smartnest.com",
                        color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                        fontSize = 14.sp
                    )
                }
            }
            
            // 菜单项
            item {
                Spacer(modifier = Modifier.height(16.dp))
                ProfileMenuItem(
                    icon = "📦",
                    title = "我的订单",
                    onClick = { 
                        onNavigateToOrder()
                    }
                )
            }

            item {
                ProfileMenuItem(
                    icon = "❤️",
                    title = "我的收藏",
                    onClick = { 
                        onNavigateToFavorite()
                    }
                )
            }

            item {
                ProfileMenuItem(
                    icon = "⚙️",
                    title = "系统设置",
                    onClick = onNavigateToSettings
                )
            }

            item {
                ProfileMenuItem(
                    icon = "💬",
                    title = "智家社区",
                    onClick = onNavigateToCommunity
                )
            }
            
            // 退出登录按钮
            item {
                Spacer(modifier = Modifier.height(32.dp))
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 24.dp)
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(56.dp)
                            .clip(RoundedCornerShape(20.dp))
                            .background(Color(0xFFEF4444).copy(alpha = 0.1f))
                            .clickable { },
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
            }
            
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        
        // 底部导航栏
        BottomNavigationBar(
            modifier = Modifier.fillMaxWidth(),
            selectedTab = 4,
            onTabSelected = { index ->
                when (index) {
                    0 -> onNavigateToHome()
                    2 -> onNavigateToScene()
                    3 -> onNavigateToMall()
                }
            }
        )
    }
}

@Composable
fun ProfileMenuItem(
    icon: String,
    title: String,
    onClick: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 24.dp, vertical = 16.dp)
            .clickable { onClick() },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(icon, fontSize = 24.sp)
            Text(
                text = title,
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 16.sp
            )
        }
        Text(
            text = "›",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            fontSize = 24.sp
        )
    }
}
