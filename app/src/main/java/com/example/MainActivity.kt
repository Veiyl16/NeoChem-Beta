package com.example

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import com.example.ui.screens.*
import com.example.ui.theme.MyApplicationTheme
import com.example.ui.theme.SecondaryNeonCyan
import com.example.ui.viewmodel.AuthViewModel
import com.example.ui.viewmodel.PeriodicViewModel
import com.example.ui.viewmodel.QuizViewModel
import com.example.ui.viewmodel.SimulationViewModel
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                NeoChemApp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        com.example.util.SoundManager.release()
    }
}

@Composable
fun NeoChemApp() {
    val navController = rememberNavController()

    // Initialize ViewModels globally
    val authViewModel: AuthViewModel = viewModel()
    val periodicViewModel: PeriodicViewModel = viewModel()
    val quizViewModel: QuizViewModel = viewModel()
    val simulationViewModel: SimulationViewModel = viewModel()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val showBottomBar = currentRoute in listOf("dashboard", "materi", "simulation", "periodic")

    var activeCoinToast by remember { mutableStateOf<Int?>(null) }
    var showToastTrigger by remember { mutableStateOf(0) }

    Box(modifier = Modifier.fillMaxSize()) {
        Scaffold(
            bottomBar = {
                if (showBottomBar) {
                    NeoChemBottomBar(currentRoute = currentRoute) { targetRoute ->
                        if (currentRoute != targetRoute) {
                            navController.navigate(targetRoute) {
                                popUpTo("dashboard") {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    }
                }
            },
            containerColor = Color.Transparent,
            modifier = Modifier.fillMaxSize()
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = "splash",
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
            ) {
            // 1. SPLASH SCREEN
            composable("splash") {
                SplashScreen {
                    val nextDest = if (authViewModel.firebaseRepository.getCurrentUser() != null) "dashboard" else "auth"
                    navController.navigate(nextDest) {
                        popUpTo("splash") { inclusive = true }
                    }
                }
            }

            // 2. AUTH SCREEN
            composable("auth") {
                AuthScreen(
                    authViewModel = authViewModel,
                    onLoginSuccess = {
                        navController.navigate("dashboard") {
                            popUpTo("auth") { inclusive = true }
                        }
                    }
                )
            }

            // 3. HOME DASHBOARD SCREEN
            composable("dashboard") {
                HomeDashboardScreen(
                    authViewModel = authViewModel,
                    onNavigateToMateri = { navController.navigate("materi") },
                    onNavigateToPeriodicTable = { navController.navigate("periodic") },
                    onNavigateToSimulation = { navController.navigate("simulation") },
                    onNavigateToLeaderboard = { navController.navigate("leaderboard") },
                    onNavigateToProfile = { navController.navigate("profile") },
                    onNavigateToFlashcards = { navController.navigate("flashcards") },
                    onNavigateToKamus = { navController.navigate("kamus") },
                    onNavigateToJikaDicampur = { navController.navigate("jika_dicampur") },
                    onNavigateToWrapped = { navController.navigate("wrapped") }
                )
            }

            // NEW LEARNING FEATURES DESTINATIONS
            composable("flashcards") {
                FlashcardScreen(
                    authViewModel = authViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            composable("kamus") {
                KamusKimiaScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable("jika_dicampur") {
                JikaDicampurScreen(
                    onBack = { navController.popBackStack() }
                )
            }

            composable("wrapped") {
                WrappedScreen(
                    authViewModel = authViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // 4. MODULE LEARN MATERI SCREEN
            composable("materi") {
                MateriScreen(
                    authViewModel = authViewModel,
                    onNavigateToQuiz = { categoryId ->
                        navController.navigate("quiz/$categoryId")
                    },
                    onBack = { navController.popBackStack() }
                )
            }

            // 5. INTERACTIVE TIMED QUIZ SESSION
            composable(
                route = "quiz/{materiId}",
                arguments = listOf(navArgument("materiId") { type = NavType.StringType })
            ) { backStackEntry ->
                val id = backStackEntry.arguments?.getString("materiId") ?: ""
                QuizScreen(
                    quizViewModel = quizViewModel,
                    authViewModel = authViewModel,
                    materiId = id,
                    onQuizClose = { navController.popBackStack() }
                )
            }

            // 6. SANDBOX EXPERIMENT LAB
            composable("simulation") {
                SimulationScreen(
                    simulationViewModel = simulationViewModel,
                    onBack = { navController.popBackStack() },
                    onSimulationComplete = { topic -> authViewModel.completeSimulation(topic) }
                )
            }

            // 7. PERIODIC TABLE SCREEN
            composable("periodic") {
                PeriodicTableScreen(
                    periodicViewModel = periodicViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // 8. GLOBAL LEADERBOARD & STATS RANKINGS
            composable("leaderboard") {
                LeaderboardScreen(
                    authViewModel = authViewModel,
                    onBack = { navController.popBackStack() }
                )
            }

            // 9. PROFILE & SYSTEM SETTINGS SCREEN
            composable("profile") {
                ProfileScreen(
                    authViewModel = authViewModel,
                    onBack = { navController.popBackStack() },
                    onLogout = {
                        navController.navigate("auth") {
                            popUpTo(0) { inclusive = true }
                        }
                    }
                )
            }
        }
    }
}
}

@Composable
fun NeoChemBottomBar(
    currentRoute: String?,
    onNavigate: (String) -> Unit
) {
    val items = listOf(
        NeoChemNavigationItem("dashboard", "Home", Icons.Default.Home),
        NeoChemNavigationItem("materi", "Materi", Icons.Default.MenuBook),
        NeoChemNavigationItem("simulation", "Simulasi", Icons.Default.Science),
        NeoChemNavigationItem("periodic", "Tabel", Icons.Default.GridOn)
    )

    val surfaceColor = MaterialTheme.colorScheme.surface
    val onSurfaceColor = MaterialTheme.colorScheme.onSurface
    val primaryColor = MaterialTheme.colorScheme.primary

    Surface(
        color = surfaceColor,
        modifier = Modifier
            .fillMaxWidth()
            .navigationBarsPadding(),
        border = BorderStroke(1.dp, onSurfaceColor.copy(alpha = 0.08f)),
        shadowElevation = 8.dp
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp, horizontal = 12.dp),
            horizontalArrangement = Arrangement.SpaceAround,
            verticalAlignment = Alignment.CenterVertically
        ) {
            items.forEach { item ->
                val isSelected = currentRoute == item.route
                val activeColor = primaryColor
                val inactiveColor = onSurfaceColor.copy(alpha = 0.45f)
                
                val capsuleScale by animateFloatAsState(if (isSelected) 1.0f else 0.8f, label = "capsule_scale")
                val capsuleAlpha by animateFloatAsState(if (isSelected) 1.0f else 0.0f, label = "capsule_alpha")

                Column(
                    modifier = Modifier
                        .weight(1f)
                        .clip(RoundedCornerShape(12.dp))
                        .clickable {
                            com.example.util.SoundManager.playClick()
                            onNavigate(item.route)
                        }
                        .padding(vertical = 6.dp),
                    horizontalAlignment = Alignment.CenterHorizontally,
                    verticalArrangement = Arrangement.Center
                ) {
                    Box(
                        contentAlignment = Alignment.Center,
                        modifier = Modifier
                            .height(28.dp)
                            .width(52.dp)
                    ) {
                        if (capsuleAlpha > 0f) {
                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .graphicsLayer {
                                        scaleX = capsuleScale
                                        scaleY = capsuleScale
                                        alpha = capsuleAlpha
                                    }
                                    .background(
                                        activeColor.copy(alpha = 0.1f),
                                        shape = RoundedCornerShape(14.dp)
                                    )
                             )
                        }
                        Icon(
                            imageVector = item.icon,
                            contentDescription = item.label,
                            tint = if (isSelected) activeColor else inactiveColor,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = item.label,
                        color = if (isSelected) activeColor else inactiveColor,
                        fontSize = 10.sp,
                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium
                    )
                }
            }
        }
    }
}

data class NeoChemNavigationItem(
    val route: String,
    val label: String,
    val icon: ImageVector
)
