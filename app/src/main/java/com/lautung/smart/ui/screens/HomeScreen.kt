package com.lautung.smart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.lautung.smart.R
import com.lautung.smart.data.model.Device
import com.lautung.smart.data.model.DeviceType
import com.lautung.smart.ui.components.BottomNavigationBar
import com.lautung.smart.ui.theme.MaterialDimens
import com.lautung.smart.ui.viewmodel.HomeViewModel
import com.lautung.smart.ui.viewmodel.UiState
import org.koin.androidx.compose.koinViewModel

/**
 * 首页 - 使用可折叠标题栏
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = koinViewModel(),
    onNavigateToScene: () -> Unit,
    onNavigateToMall: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToVoiceSearch: () -> Unit,
    onNavigateToDeviceManagement: () -> Unit,
    onNavigateToNotification: () -> Unit
) {
    var isMorningModeEnabled by remember { mutableStateOf(true) }
    var isSleepModeEnabled by remember { mutableStateOf(false) }

    val devicesState by viewModel.devicesState.collectAsState()

    // 可折叠标题栏滚动行为
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(
        rememberTopAppBarState()
    )

    // 使用 Scaffold + LargeTopAppBar 实现可折叠标题栏
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = {
                    Column {
                        Text(
                            text = stringResource(R.string.greeting),
                            style = MaterialTheme.typography.headlineMedium,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            text = stringResource(R.string.date_format),
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                        )
                    }
                },
                navigationIcon = {
                    // 用户头像
                    Box(
                        modifier = Modifier
                            .padding(start = MaterialDimens.small)
                            .size(MaterialDimens.iconLarge)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.surfaceVariant)
                            .clickable { },
                        contentAlignment = Alignment.Center
                    ) {
                        Text("👤", fontSize = 20.sp)
                    }
                },
                actions = {
                    // 搜索图标
                    Text(
                        text = "🔍",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable { }
                            .padding(8.dp)
                    )
                    // 通知图标
                    Text(
                        text = "🔔",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .clickable { onNavigateToNotification() }
                            .padding(end = 16.dp, start = 8.dp)
                    )
                },
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.background,
                    scrolledContainerColor = MaterialTheme.colorScheme.surface
                ),
                scrollBehavior = scrollBehavior
            )
        },
        bottomBar = {
            BottomNavigationBar(
                modifier = Modifier.fillMaxWidth(),
                selectedTab = 0,
                onTabSelected = { index ->
                    when (index) {
                        0 -> {} // Home
                        1 -> onNavigateToVoiceSearch()
                        2 -> onNavigateToScene()
                        3 -> onNavigateToMall()
                        4 -> onNavigateToProfile()
                    }
                }
            )
        }
    ) { innerPadding ->
        // 页面内容
        when (val state = devicesState) {
            is UiState.Loading -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }
            is UiState.Error -> {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(innerPadding),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = state.message,
                        color = Color.Red
                    )
                }
            }
            is UiState.Success -> {
                val devices = state.data
                HomeContent(
                    innerPadding = innerPadding,
                    devices = devices,
                    isMorningModeEnabled = isMorningModeEnabled,
                    isSleepModeEnabled = isSleepModeEnabled,
                    onMorningModeToggle = { isMorningModeEnabled = !isMorningModeEnabled },
                    onSleepModeToggle = { isSleepModeEnabled = !isSleepModeEnabled },
                    onNavigateToDeviceManagement = onNavigateToDeviceManagement
                )
            }
        }
    }
}

@Composable
private fun HomeContent(
    innerPadding: PaddingValues,
    devices: List<Device>,
    isMorningModeEnabled: Boolean,
    isSleepModeEnabled: Boolean,
    onMorningModeToggle: () -> Unit,
    onSleepModeToggle: () -> Unit,
    onNavigateToDeviceManagement: () -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentPadding = PaddingValues(
            start = MaterialDimens.medium,
            end = MaterialDimens.medium,
            top = innerPadding.calculateTopPadding(),
            bottom = MaterialDimens.small
        ),
        verticalArrangement = Arrangement.spacedBy(MaterialDimens.medium)
    ) {
        // 快捷场景标题
        item {
            Text(
                text = stringResource(R.string.quick_scenes),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.SemiBold,
                color = MaterialTheme.colorScheme.onBackground
            )
        }

        // 场景卡片 - 响应式
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialDimens.small)
            ) {
                SceneCard(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.morning_mode),
                    subtitle = if (isMorningModeEnabled) stringResource(R.string.enabled) else stringResource(R.string.disabled),
                    icon = "🌅",
                    backgroundColor = Color(0xFFFF9500),
                    onClick = onMorningModeToggle
                )

                SceneCard(
                    modifier = Modifier.weight(1f),
                    title = stringResource(R.string.sleep_mode),
                    subtitle = if (isSleepModeEnabled) stringResource(R.string.enabled) else stringResource(R.string.disabled),
                    icon = "🌙",
                    backgroundColor = Color(0xFF5856D6),
                    onClick = onSleepModeToggle
                )
            }
        }

        // 设备状态标题
        item {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialDimens.small),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stringResource(R.string.device_status),
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.SemiBold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = stringResource(R.string.all_devices, devices.size),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.6f),
                    modifier = Modifier.clickable { onNavigateToDeviceManagement() }
                )
            }
        }

        // 设备列表 - 每行2个
        items(devices.chunked(2)) { rowDevices ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialDimens.small)
            ) {
                rowDevices.forEach { device ->
                    DeviceCard(
                        modifier = Modifier.weight(1f),
                        name = device.name,
                        status = device.status,
                        type = device.type
                    )
                }
                if (rowDevices.size == 1) {
                    Spacer(modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Composable
private fun SceneCard(
    modifier: Modifier = Modifier,
    title: String,
    subtitle: String,
    icon: String,
    backgroundColor: Color,
    onClick: () -> Unit
) {
    Surface(
        modifier = modifier
            .aspectRatio(1.5f)
            .clickable { onClick() },
        shape = RoundedCornerShape(MaterialDimens.small),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialDimens.medium),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            Column {
                Text(
                    text = title,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = Color.White
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall,
                    color = Color.White.copy(alpha = 0.7f)
                )
            }
        }
    }
}

@Composable
private fun DeviceCard(
    modifier: Modifier = Modifier,
    name: String,
    status: String,
    type: DeviceType
) {
    val icon = when (type) {
        DeviceType.AIR_CONDITIONER -> "❄️"
        DeviceType.LIGHT -> "💡"
        DeviceType.DOOR_LOCK -> "🔒"
        DeviceType.CAMERA -> "📷"
        DeviceType.THERMOSTAT -> "🌡️"
    }

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(MaterialDimens.small),
        color = MaterialTheme.colorScheme.surfaceVariant
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialDimens.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = icon,
                style = MaterialTheme.typography.headlineMedium
            )
            Spacer(modifier = Modifier.width(MaterialDimens.small))
            Column {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyMedium,
                    fontWeight = FontWeight.Medium,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Text(
                    text = status,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                )
            }
        }
    }
}
