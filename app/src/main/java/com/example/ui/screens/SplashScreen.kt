package com.example.ui.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay

@Composable
fun SplashScreen(
    onFinished: () -> Unit
) {
    // Pulse animation representing atom vibrations
    val infiniteTransition = rememberInfiniteTransition(label = "pulse")
    val scaleFactor by infiniteTransition.animateFloat(
        initialValue = 0.96f,
        targetValue = 1.04f,
        animationSpec = infiniteRepeatable(
            animation = tween(1200, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "scale"
    )

    LaunchedEffect(Unit) {
        delay(1800)
        onFinished()
    }

    FuturisticBg {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier
                    .size(120.dp)
                    .scale(scaleFactor)
            ) {
                AtomAnimationLogo(modifier = Modifier.fillMaxSize())
            }

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = "NeoChem",
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold,
                fontSize = 36.sp,
                letterSpacing = 2.sp,
                style = MaterialTheme.typography.displaySmall
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Belajar Kimia Lebih Mudah",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyMedium,
                fontWeight = FontWeight.Medium,
                fontSize = 14.sp,
                letterSpacing = 1.sp
            )
        }
    }
}

@Composable
fun AtomAnimationLogo(modifier: Modifier = Modifier) {
    val infiniteTransition = rememberInfiniteTransition(label = "atom_anim")
    
    // Rotating orbit animation angles
    val orbitAngle1 by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit1"
    )
    val orbitAngle2 by infiniteTransition.animateFloat(
        initialValue = 120f,
        targetValue = 480f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit2"
    )
    val orbitAngle3 by infiniteTransition.animateFloat(
        initialValue = 240f,
        targetValue = 600f,
        animationSpec = infiniteRepeatable(
            animation = tween(6000, easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ),
        label = "orbit3"
    )

    // Nucleus pulse scale
    val nucleusScale by infiniteTransition.animateFloat(
        initialValue = 8f,
        targetValue = 11f,
        animationSpec = infiniteRepeatable(
            animation = tween(1000, easing = FastOutSlowInEasing),
            repeatMode = RepeatMode.Reverse
        ),
        label = "nucleus"
    )

    val pColor = MaterialTheme.colorScheme.primary
    val sColor = MaterialTheme.colorScheme.secondary
    val tColor = MaterialTheme.colorScheme.tertiary

    Canvas(modifier = modifier) {
        val center = Offset(size.width / 2f, size.height / 2f)
        
        // Define viewport mapping coordinates based on 108x108 design proportions
        val w = size.width
        val h = size.height
        val x1 = w * (32f / 108f)
        val x2 = w * (76f / 108f)
        val y1 = h * (36f / 108f)
        val y2 = h * (72f / 108f)

        // 1. Draw Molecular N's Connection Bonds (connecting rods)
        // Left vertical bond
        drawLine(
            color = Color(0x6060A5FA),
            start = Offset(x1, y2),
            end = Offset(x1, y1),
            strokeWidth = 5.dp.toPx(),
            cap = androidx.compose.ui.graphics.StrokeCap.Round
        )
        // Diagonal bond
        drawLine(
            color = Color(0x602DD4BF),
            start = Offset(x1, y1),
            end = Offset(x2, y2),
            strokeWidth = 5.dp.toPx(),
            cap = androidx.compose.ui.graphics.StrokeCap.Round
        )
        // Right vertical bond
        drawLine(
            color = Color(0x60C084FC),
            start = Offset(x2, y2),
            end = Offset(x2, y1),
            strokeWidth = 5.dp.toPx(),
            cap = androidx.compose.ui.graphics.StrokeCap.Round
        )

        // 2. Draw Atom Nodes (Outer Colorful Sphere + Inner White core)
        // Element A: Left Bottom - Blue (#60A5FA)
        drawCircle(
            color = Color(0xFF60A5FA),
            radius = 8.dp.toPx(),
            center = Offset(x1, y2)
        )
        drawCircle(
            color = Color.White,
            radius = 3.dp.toPx(),
            center = Offset(x1, y2)
        )

        // Element B: Left Top - Teal (#2DD4BF)
        drawCircle(
            color = Color(0xFF2DD4BF),
            radius = 8.dp.toPx(),
            center = Offset(x1, y1)
        )
        drawCircle(
            color = Color.White,
            radius = 3.dp.toPx(),
            center = Offset(x1, y1)
        )

        // Element C: Right Bottom - Purple (#C084FC)
        drawCircle(
            color = Color(0xFFC084FC),
            radius = 8.dp.toPx(),
            center = Offset(x2, y2)
        )
        drawCircle(
            color = Color.White,
            radius = 3.dp.toPx(),
            center = Offset(x2, y2)
        )

        // Element D: Right Top - Amber (#FBBF24)
        drawCircle(
            color = Color(0xFFFBBF24),
            radius = 8.dp.toPx(),
            center = Offset(x2, y1)
        )
        drawCircle(
            color = Color.White,
            radius = 3.dp.toPx(),
            center = Offset(x2, y1)
        )

        // Helper to draw orbital ring containing moving electrons
        fun drawOrbitRing(angle: Float, orbitColor: Color, radiusX: Float, radiusY: Float) {
            val path = androidx.compose.ui.graphics.Path().apply {
                addOval(androidx.compose.ui.geometry.Rect(center.x - radiusX, center.y - radiusY, center.x + radiusX, center.y + radiusY))
            }
            
            // Apply standard Compose DrawScope rotate block
            rotate(degrees = angle, pivot = center) {
                drawPath(
                    path = path,
                    color = orbitColor.copy(alpha = 0.25f),
                    style = Stroke(width = 1.5.dp.toPx())
                )
                
                // Draw a cute tiny moving electron node on the path
                drawCircle(
                    color = orbitColor,
                    radius = 4.dp.toPx(),
                    center = Offset(center.x, center.y - radiusY)
                )
            }
        }

        // 3 Orbits rotated 60 degrees apart from each other utilizing theme adaptive colors
        drawOrbitRing(orbitAngle1, pColor, 50.dp.toPx(), 18.dp.toPx())
        drawOrbitRing(orbitAngle2, sColor, 50.dp.toPx(), 18.dp.toPx())
        drawOrbitRing(orbitAngle3, tColor, 50.dp.toPx(), 18.dp.toPx())
    }
}
