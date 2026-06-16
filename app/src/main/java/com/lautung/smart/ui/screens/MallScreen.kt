package com.lautung.smart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
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
import com.lautung.smart.data.model.Product
import com.lautung.smart.ui.components.BottomNavigationBar
import com.lautung.smart.ui.viewmodel.MallViewModel
import com.lautung.smart.ui.viewmodel.UiState

@Composable
fun MallScreen(
    onNavigateBack: () -> Unit,
    onProductClick: (String) -> Unit,
    onNavigateToCart: () -> Unit,
    onNavigateToHome: () -> Unit,
    onNavigateToScene: () -> Unit,
    onNavigateToProfile: () -> Unit,
    viewModel: MallViewModel = hiltViewModel()
) {
    val productsState by viewModel.productsState.collectAsState()

    val bgColors = listOf(
        Color(0xFF1E3A8A).copy(alpha = 0.5f),
        Color(0xFF581C87).copy(alpha = 0.5f),
        Color(0xFF14532D).copy(alpha = 0.5f),
        Color(0xFF7C2D12).copy(alpha = 0.5f),
        Color(0xFF1E3A8A).copy(alpha = 0.5f),
        Color(0xFF581C87).copy(alpha = 0.5f)
    )

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

        when (val state = productsState) {
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
                val products = state.data
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize()
                        .navigationBarsPadding(),
                    contentPadding = PaddingValues(horizontal = 16.dp, vertical = 16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(products) { product ->
                        val index = products.indexOf(product)
                        ProductCard(
                            icon = product.displayIcon,
                            name = product.name,
                            description = product.description,
                            price = product.price.toInt(),
                            backgroundColor = bgColors.getOrElse(index) { bgColors[0] },
                            onClick = { onProductClick(product.name) },
                            onBuyClick = { onNavigateToCart() }
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
