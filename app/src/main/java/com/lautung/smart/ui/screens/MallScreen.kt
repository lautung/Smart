package com.lautung.smart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
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
fun MallScreen(
    onNavigateBack: () -> Unit,
    onProductClick: (String) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToScene: () -> Unit,
    onNavigateToProfile: () -> Unit
) {
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
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "SmartNest 商城",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
        }
        
        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            modifier = Modifier
                .fillMaxSize()
                .navigationBarsPadding(),
            contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            // 智能氛围灯
            item {
                ProductCard(
                    icon = "💡",
                    name = "智能氛围灯",
                    description = "1600 万色，语音控制",
                    price = 299,
                    backgroundColor = Color(0xFF1E3A8A).copy(alpha = 0.5f),
                    onClick = { onProductClick("智能氛围灯") },
                    onBuyClick = { 
                        onNavigateToCart()
                    }
                )
            }
            
            // 智能温控器
            item {
                ProductCard(
                    icon = "🌡️",
                    name = "智能温控器",
                    description = "精准控温，节能省电",
                    price = 459,
                    backgroundColor = Color(0xFF581C87).copy(alpha = 0.5f),
                    onClick = { onProductClick("智能温控器") },
                    onBuyClick = { 
                        onNavigateToCart()
                    }
                )
            }
            
            // 智能门锁
            item {
                ProductCard(
                    icon = "🔒",
                    name = "智能门锁 Pro",
                    description = "指纹解锁，远程监控",
                    price = 1299,
                    backgroundColor = Color(0xFF14532D).copy(alpha = 0.5f),
                    onClick = { onProductClick("智能门锁 Pro") },
                    onBuyClick = { 
                        onNavigateToCart()
                    }
                )
            }
            
            // 智能摄像头
            item {
                ProductCard(
                    icon = "📷",
                    name = "智能摄像头",
                    description = "1080P 高清，夜视功能",
                    price = 399,
                    backgroundColor = Color(0xFF7C2D12).copy(alpha = 0.5f),
                    onClick = { onProductClick("智能摄像头") },
                    onBuyClick = { 
                        onNavigateToCart()
                    }
                )
            }
            
            item {
                Spacer(modifier = Modifier.height(100.dp))
            }
        }
        
        // 底部导航栏
        BottomNavigationBar(
            modifier = Modifier.fillMaxWidth(),
            selectedTab = 3,
            onTabSelected = { index ->
                when (index) {
                    0 -> onNavigateToHome()
                    2 -> onNavigateToScene()
                    4 -> onNavigateToProfile()
                }
            }
        )
    }
}

@Composable
fun ProductCard(
    icon: String,
    name: String,
    description: String,
    price: Int,
    backgroundColor: Color,
    onClick: () -> Unit,
    onBuyClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(20.dp))
            .background(Color(0xFF1F2937).copy(alpha = 0.4f))
            .clickable { onClick() }
            .padding(16.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(160.dp)
                .clip(RoundedCornerShape(16.dp))
                .background(backgroundColor),
            contentAlignment = Alignment.Center
        ) {
            Text(icon, fontSize = 64.sp)
        }
        Spacer(modifier = Modifier.height(12.dp))
        Text(
            text = name,
            color = MaterialTheme.colorScheme.onBackground,
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = description,
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            fontSize = 14.sp
        )
        Spacer(modifier = Modifier.height(12.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "¥$price",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
            Box(
                modifier = Modifier
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color(0xFF3B82F6))
                    .padding(horizontal = 12.dp, vertical = 6.dp)
                    .clickable { onBuyClick() }
            ) {
                Text(
                    text = "购买",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 12.sp
                )
            }
        }
    }
}
