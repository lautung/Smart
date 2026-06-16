package com.lautung.smart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lautung.smart.data.model.Device
import com.lautung.smart.data.model.DeviceType
import com.lautung.smart.ui.components.BottomNavigationBar
import com.lautung.smart.ui.viewmodel.DeviceViewModel
import com.lautung.smart.ui.viewmodel.UiState

@Composable
fun DeviceManagementScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToMall: () -> Unit,
    onNavigateToScene: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: DeviceViewModel = hiltViewModel()
) {
    val devicesState by viewModel.devicesState.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
    ) {
        // 状态栏占位
        Spacer(modifier = Modifier.height(44.dp))

        // 顶部导航栏
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(56.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "←",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 24.sp,
                modifier = Modifier.clickable { onNavigateBack() }
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = "设备管理",
                color = MaterialTheme.colorScheme.onBackground,
                fontSize = 18.sp,
                fontWeight = FontWeight.Medium
            )
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentWidth(Alignment.End)
            ) {
                Text(
                    text = "+",
                    color = MaterialTheme.colorScheme.onBackground,
                    fontSize = 24.sp
                )
            }
        }

        when (val state = devicesState) {
            is UiState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(text = state.message, color = MaterialTheme.colorScheme.error)
                }
            }
            is UiState.Success -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(state.data) { device ->
                        DeviceItem(
                            name = device.name,
                            status = if (device.isOnline) "在线 · ${device.status}" else "离线",
                            icon = device.type.toEmoji()
                        )
                    }
                    item {
                        Spacer(modifier = Modifier.height(100.dp))
                    }
                }
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

private fun DeviceType.toEmoji(): String {
    return when (this) {
        DeviceType.AIR_CONDITIONER -> "❄️"
        DeviceType.LIGHT -> "💡"
        DeviceType.DOOR_LOCK -> "🔒"
        DeviceType.CAMERA -> "📷"
        DeviceType.THERMOSTAT -> "🌡️"
    }
}

@Composable
fun DeviceItem(name: String, status: String, icon: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(16.dp))
            .background(Color(0xFF1F2937).copy(alpha = 0.4f))
            .padding(16.dp),
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
                    text = name,
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Medium,
                    fontSize = 16.sp
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = status,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    fontSize = 14.sp
                )
            }
        }
        Text(
            text = "›",
            color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
            fontSize = 24.sp
        )
    }
}
