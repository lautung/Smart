package com.lautung.smart.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun BottomNavigationBar(
    modifier: Modifier = Modifier,
    selectedTab: Int,
    onTabSelected: (Int) -> Unit
) {
    val icons = listOf("🏠", "🔍", "⚡", "🛍️", "👤")
    val labels = listOf("首页", "搜索", "场景", "商城", "我的")

    Row(
        modifier = modifier
            .background(Color.Black.copy(alpha = 0.4f))
            .fillMaxWidth()
            .navigationBarsPadding()
            .padding(vertical = 8.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        icons.forEachIndexed { index, icon ->
            BottomNavItem(
                icon = { Text(icon, fontSize = 20.sp) },
                label = labels[index],
                isSelected = selectedTab == index,
                onClick = { onTabSelected(index) },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1.0f)
            )
        }
    }
}