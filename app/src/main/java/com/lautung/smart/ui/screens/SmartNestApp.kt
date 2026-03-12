package com.lautung.smart.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.lautung.smart.ui.theme.LanguageManager
import com.lautung.smart.ui.theme.ThemeManager

sealed class Screen(val route: String, val arguments: List<NamedNavArgument> = emptyList()) {
    object Splash : Screen("splash")
    object Home : Screen("home")
    object Scene : Screen("scene")
    object Mall : Screen("mall")
    object Profile : Screen("profile")
    object VoiceSearch : Screen("voice_search")
    object ProductDetail : Screen(
        route = "product_detail/{productName}",
        arguments = listOf(navArgument("productName") { type = NavType.StringType })
    ) {
        fun createRoute(productName: String) = "product_detail/$productName"
    }
    object DeviceManagement : Screen("device_management")
    object EnergyAnalysis : Screen("energy_analysis")
    object MonitorPreview : Screen("monitor_preview")
    object FamilyMember : Screen("family_member")
    object Cart : Screen("cart")
    object Payment : Screen("payment")
    object Community : Screen("community")
    object Settings : Screen("settings")
    object HomeModeConfig : Screen("home_mode_config")
    object Notification : Screen("notification")
    object AddScene : Screen("add_scene")
    object SceneConfig : Screen(
        route = "scene_config/{sceneName}",
        arguments = listOf(navArgument("sceneName") { type = NavType.StringType })
    ) {
        fun createRoute(sceneName: String) = "scene_config/$sceneName"
    }
    object Order : Screen("order")
    object Favorite : Screen("favorite")
}

@Composable
fun SmartNestApp(themeManager: ThemeManager, languageManager: LanguageManager) {
    var showSplash by remember { mutableStateOf(true) }
    
    if (showSplash) {
        SplashScreen(
            onTimeout = { showSplash = false }
        )
    } else {
        SmartNestNavHost(themeManager = themeManager, languageManager = languageManager)
    }
}

@Composable
fun SmartNestNavHost(themeManager: ThemeManager, languageManager: LanguageManager) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.Home.route
    ) {
        composable(Screen.Home.route) {
            HomeScreen(
                onNavigateToScene = {
                    navController.navigate(Screen.Scene.route)
                },
                onNavigateToMall = {
                    navController.navigate(Screen.Mall.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onNavigateToVoiceSearch = {
                    navController.navigate(Screen.VoiceSearch.route)
                },
                onNavigateToDeviceManagement = {
                    navController.navigate(Screen.DeviceManagement.route)
                },
                onNavigateToNotification = {
                    navController.navigate(Screen.Notification.route)
                }
            )
        }

        composable(Screen.Scene.route) {
            SceneScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHomeModeConfig = {
                    navController.navigate(Screen.HomeModeConfig.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToMall = {
                    navController.navigate(Screen.Mall.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                },
                onNavigateToAddScene = {
                    navController.navigate(Screen.AddScene.route)
                },
                onNavigateToSceneConfig = {
                    navController.navigate(Screen.SceneConfig.createRoute(it))
                }
            )
        }

        composable(Screen.Mall.route) {
            MallScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onProductClick = { product ->
                    navController.navigate(Screen.ProductDetail.createRoute(product))
                },
                onNavigateToCart = {
                    navController.navigate(Screen.Cart.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToScene = {
                    navController.navigate(Screen.Scene.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.Profile.route) {
            ProfileScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToSettings = {
                    navController.navigate(Screen.Settings.route)
                },
                onNavigateToCommunity = {
                    navController.navigate(Screen.Community.route)
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToMall = {
                    navController.navigate(Screen.Mall.route)
                },
                onNavigateToScene = {
                    navController.navigate(Screen.Scene.route)
                },
                onNavigateToOrder = {
                    navController.navigate(Screen.Order.route)
                },
                onNavigateToFavorite = {
                    navController.navigate(Screen.Favorite.route)
                }
            )
        }

        composable(Screen.VoiceSearch.route) {
            VoiceSearchScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.ProductDetail.route,
            arguments = Screen.ProductDetail.arguments
        ) { backStackEntry ->
            val productName = backStackEntry.arguments?.getString("productName") ?: "产品"
            ProductDetailScreen(
                productName = productName,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onAddToCart = {
                    // 添加到购物车逻辑
                    navController.navigate(Screen.Cart.route)
                }
            )
        }

        composable(Screen.DeviceManagement.route) {
            DeviceManagementScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToMall = {
                    navController.navigate(Screen.Mall.route)
                },
                onNavigateToScene = {
                    navController.navigate(Screen.Scene.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.EnergyAnalysis.route) {
            EnergyAnalysisScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToMall = {
                    navController.navigate(Screen.Mall.route)
                },
                onNavigateToScene = {
                    navController.navigate(Screen.Scene.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.MonitorPreview.route) {
            MonitorPreviewScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToMall = {
                    navController.navigate(Screen.Mall.route)
                },
                onNavigateToScene = {
                    navController.navigate(Screen.Scene.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.FamilyMember.route) {
            FamilyMemberScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToMall = {
                    navController.navigate(Screen.Mall.route)
                },
                onNavigateToScene = {
                    navController.navigate(Screen.Scene.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.Cart.route) {
            CartScreen(
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToPayment = {
                    navController.navigate(Screen.Payment.route)
                }
            )
        }

        composable(Screen.Payment.route) {
            PaymentScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Community.route) {
            CommunityScreen(
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToMall = {
                    navController.navigate(Screen.Mall.route)
                },
                onNavigateToScene = {
                    navController.navigate(Screen.Scene.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.Settings.route) {
            SettingsScreen(
                themeManager = themeManager,
                languageManager = languageManager,
                onNavigateBack = {
                    navController.popBackStack()
                },
                onNavigateToHome = {
                    navController.navigate(Screen.Home.route) {
                        popUpTo(Screen.Home.route) { inclusive = true }
                    }
                },
                onNavigateToMall = {
                    navController.navigate(Screen.Mall.route)
                },
                onNavigateToScene = {
                    navController.navigate(Screen.Scene.route)
                },
                onNavigateToProfile = {
                    navController.navigate(Screen.Profile.route)
                }
            )
        }

        composable(Screen.HomeModeConfig.route) {
            HomeModeConfigScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Notification.route) {
            NotificationScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.AddScene.route) {
            AddSceneScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(
            route = Screen.SceneConfig.route,
            arguments = Screen.SceneConfig.arguments
        ) { backStackEntry ->
            val sceneName = backStackEntry.arguments?.getString("sceneName") ?: "场景"
            SceneConfigScreen(
                sceneName = sceneName,
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Order.route) {
            OrderScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(Screen.Favorite.route) {
            FavoriteScreen(
                onNavigateBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}
