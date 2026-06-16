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
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.lautung.smart.R
import com.lautung.smart.data.model.Scene
import com.lautung.smart.ui.components.BottomNavigationBar
import com.lautung.smart.ui.viewmodel.SceneViewModel
import com.lautung.smart.ui.viewmodel.UiState

@Composable
fun SceneScreen(
    onNavigateBack: () -> Unit,
    onNavigateToHomeModeConfig: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToMall: () -> Unit,
    onNavigateToProfile: () -> Unit,
    onNavigateToAddScene: () -> Unit,
    onNavigateToSceneConfig: (String) -> Unit,
    viewModel: SceneViewModel = hiltViewModel()
) {
    val scenesState by viewModel.scenesState.collectAsState()

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
                modifier = Modifier.clickable { onNavigateToAddScene() }
            )
        }

        when (val state = scenesState) {
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
                val scenes = state.data
                val backgroundColors = listOf(
                    Color(0xFF1E3A8A).copy(alpha = 0.3f),
                    Color(0xFF581C87).copy(alpha = 0.3f),
                    Color(0xFF14532D).copy(alpha = 0.3f)
                )
                val borderColors = listOf(
                    Color(0xFF1E40AF).copy(alpha = 0.3f),
                    Color(0xFF7C3AED).copy(alpha = 0.3f),
                    Color(0xFF22C55E).copy(alpha = 0.3f)
                )

                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    contentPadding = PaddingValues(horizontal = 24.dp, vertical = 16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(scenes) { scene ->
                        val index = scenes.indexOf(scene)
                        Box(
                            modifier = Modifier.clickable {
                                if (scene.triggerType == "geofence") {
                                    onNavigateToHomeModeConfig()
                                } else {
                                    onNavigateToSceneConfig(scene.name)
                                }
                            }
                        ) {
                            SceneItem(
                                icon = scene.icon,
                                title = scene.name,
                                subtitle = scene.schedule ?: scene.triggerType,
                                description = scene.description,
                                isEnabled = scene.isEnabled,
                                backgroundColor = backgroundColors.getOrElse(index) { backgroundColors[0] },
                                borderColor = borderColors.getOrElse(index) { borderColors[0] },
                                onToggle = {
                                    viewModel.executeScene(scene.id)
                                }
                            )
                        }
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
