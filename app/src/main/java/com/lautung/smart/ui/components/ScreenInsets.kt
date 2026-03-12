package com.lautung.smart.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

/**
 * 页面内容容器 - 自动处理状态栏和导航栏内边距
 *
 * 使用方式:
 * ContentBox(
 *     modifier = Modifier.fillMaxSize()
 * ) {
 *     // 你的内容
 * }
 *
 * 这样内容会自动避开状态栏和导航栏
 */
@Composable
fun ContentBox(
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .windowInsetsPadding(WindowInsets.statusBars)
            .windowInsetsPadding(WindowInsets.navigationBars),
        contentAlignment = Alignment.TopStart
    ) {
        content()
    }
}

/**
 * 页面模板 - 带顶部栏和底部栏
 *
 * 使用方式:
 * ScaffoldTemplate(
 *     topBar = {
 *         // 你的顶部栏
 *     },
 *     bottomBar = {
 *         // 你的底部栏
 *     }
 * ) {
 *     // 页面内容
 * }
 */
@Composable
fun ScaffoldTemplate(
    modifier: Modifier = Modifier,
    showStatusBarPadding: Boolean = true,
    showNavigationBarPadding: Boolean = true,
    topBar: @Composable (() -> Unit)? = null,
    bottomBar: @Composable (() -> Unit)? = null,
    content: @Composable (PaddingValues) -> Unit
) {
    val statusBarPadding = WindowInsets.statusBars.asPaddingValues()
    val navigationBarPadding = WindowInsets.navigationBars.asPaddingValues()

    Box(modifier = modifier.fillMaxSize()) {
        // 顶部栏
        topBar?.let { bar ->
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .then(
                        if (showStatusBarPadding) {
                            Modifier.windowInsetsPadding(WindowInsets.statusBars)
                        } else {
                            Modifier
                        }
                    )
            ) {
                bar()
            }
        }

        // 主内容
        Box(
            modifier = Modifier
                .fillMaxSize()
                .let { box ->
                    if (showStatusBarPadding && topBar != null) {
                        box.padding(top = statusBarPadding.calculateTopPadding())
                    } else if (showStatusBarPadding) {
                        box.windowInsetsPadding(WindowInsets.statusBars)
                    } else {
                        box
                    }
                }
                .let { box ->
                    if (showNavigationBarPadding && bottomBar != null) {
                        box.padding(bottom = navigationBarPadding.calculateBottomPadding())
                    } else {
                        box
                    }
                }
        ) {
            content(PaddingValues())
        }

        // 底部栏
        bottomBar?.let { bar ->
            Box(
                modifier = Modifier
                    .align(Alignment.BottomCenter)
                    .then(
                        if (showNavigationBarPadding) {
                            Modifier.windowInsetsPadding(WindowInsets.navigationBars)
                        } else {
                            Modifier
                        }
                    )
            ) {
                bar()
            }
        }
    }
}

/**
 * 获取状态栏高度
 */
@Composable
fun statusBarHeight(): PaddingValues {
    return WindowInsets.statusBars.asPaddingValues()
}

/**
 * 获取导航栏高度
 */
@Composable
fun navigationBarHeight(): PaddingValues {
    return WindowInsets.navigationBars.asPaddingValues()
}
