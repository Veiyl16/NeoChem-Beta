package com.example.ui.screens

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.Text
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.*
import com.example.data.local.NeoChemSettings
import com.example.util.SoundManager
import kotlin.math.cos
import kotlin.math.sin
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.BaselineShift
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.text.style.TextAlign

@Composable
fun FuturisticBg(
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    val isDark = MaterialTheme.colorScheme.background.value == Color(0xFF000000).value
    
    // Determine refined background hues: Alabaster clean paper / OLED Sleek black
    val bgColor = if (isDark) Color(0xFF000000) else Color(0xFFF9FBF9)
    
    val lineAccentColor = if (isDark) Color(0xFF1E3529).copy(alpha = 0.28f) else Color(0xFF2C6B4E).copy(alpha = 0.04f)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(bgColor)
    ) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val w = size.width
            val h = size.height

            // Notebook-ruled lines for organic student/lecture journal feeling
            if (!isDark) {
                val rowHeight = 44.dp.toPx()
                var y = rowHeight
                val notebookLineColor = Color(0xFF2C6B4E).copy(alpha = 0.015f)
                while (y < h) {
                    drawLine(
                        color = notebookLineColor,
                        start = Offset(0f, y),
                        end = Offset(w, y),
                        strokeWidth = 1f
                    )
                    y += rowHeight
                }
            }

            // Scatter hand-constructed chemistry blueprints
            drawBenzeneDecoration(Offset(w * 0.85f, h * 0.15f), 45.dp.toPx(), lineAccentColor)
            drawBenzeneDecoration(Offset(w * 0.15f, h * 0.48f), 35.dp.toPx(), lineAccentColor)
            drawCarbonChainDecoration(Offset(w * 0.72f, h * 0.72f), 28.dp.toPx(), lineAccentColor)
            drawH2ODecoration(Offset(w * 0.22f, h * 0.18f), 15.dp.toPx(), lineAccentColor)
            drawBenzeneDecoration(Offset(w * 0.43f, h * 0.86f), 40.dp.toPx(), lineAccentColor)
        }

        if (isDark) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color(0xFF0E1116).copy(alpha = 0.45f))
            )
        }

        Box(modifier = Modifier.fillMaxSize()) {
            content()
        }
    }
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawBenzeneDecoration(
    center: Offset,
    size: Float,
    color: Color
) {
    val points = mutableListOf<Offset>()
    for (i in 0 until 6) {
        val angle = Math.toRadians((i * 60).toDouble() - 30.0)
        val px = center.x + size * cos(angle).toFloat()
        val py = center.y + size * sin(angle).toFloat()
        points.add(Offset(px, py))
    }
    for (i in 0 until 6) {
        drawLine(
            color = color,
            start = points[i],
            end = points[(i + 1) % 6],
            strokeWidth = 1.25.dp.toPx()
        )
    }
    drawCircle(
        color = color,
        radius = size * 0.58f,
        center = center,
        style = Stroke(width = 1.dp.toPx())
    )
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawCarbonChainDecoration(
    start: Offset,
    length: Float,
    color: Color
) {
    val strokePx = 1.25.dp.toPx()
    val p1 = start
    val p2 = Offset(start.x + length, start.y - length * 0.5f)
    val p3 = Offset(p2.x + length, p2.y + length * 0.5f)
    val p4 = Offset(p3.x + length, p3.y - length * 0.5f)

    drawLine(color = color, start = p1, end = p2, strokeWidth = strokePx)
    drawLine(color = color, start = p2, end = p3, strokeWidth = strokePx)
    drawLine(
        color = color,
        start = Offset(p2.x, p2.y + 4.dp.toPx()),
        end = Offset(p3.x, p3.y + 4.dp.toPx()),
        strokeWidth = strokePx
    )
    drawLine(color = color, start = p3, end = p4, strokeWidth = strokePx)
}

private fun androidx.compose.ui.graphics.drawscope.DrawScope.drawH2ODecoration(
    center: Offset,
    size: Float,
    color: Color
) {
    drawCircle(
        color = color,
        radius = size * 0.45f,
        center = center,
        style = Stroke(width = 1.25.dp.toPx())
    )
    val angle1 = Math.toRadians(40.0)
    val angle2 = Math.toRadians(140.0)
    
    val h1 = Offset(
        (center.x + size * 1.15f * cos(angle1)).toFloat(),
        (center.y + size * 1.15f * sin(angle1)).toFloat()
    )
    val h2 = Offset(
        (center.x + size * 1.15f * cos(angle2)).toFloat(),
        (center.y + size * 1.15f * sin(angle2)).toFloat()
    )

    drawLine(color = color, start = center, end = h1, strokeWidth = 1.dp.toPx())
    drawLine(color = color, start = center, end = h2, strokeWidth = 1.dp.toPx())

    drawCircle(
        color = color,
        radius = size * 0.22f,
        center = h1,
        style = Stroke(width = 1.dp.toPx())
    )
    drawCircle(
        color = color,
        radius = size * 0.22f,
        center = h2,
        style = Stroke(width = 1.dp.toPx())
    )
}

@Composable
fun GlassCard(
    modifier: Modifier = Modifier,
    borderColor: Color? = null,
    backgroundColor: Color? = null,
    cornerRadius: Dp = 16.dp,
    content: @Composable ColumnScope.() -> Unit
) {
    val themeBg = MaterialTheme.colorScheme.surface
    val themeBorder = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.08f)

    val finalBg = backgroundColor ?: themeBg
    val finalBorder = borderColor ?: themeBorder

    Column(
        modifier = modifier
            .clip(RoundedCornerShape(cornerRadius))
            .background(finalBg)
            .border(
                width = 1.dp,
                color = finalBorder,
                shape = RoundedCornerShape(cornerRadius)
            )
            .padding(16.dp),
        content = content
    )
}

@Composable
fun GlassButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    isPrimary: Boolean = true,
    enabled: Boolean = true
) {
    val primColor = MaterialTheme.colorScheme.primary
    val secColor = MaterialTheme.colorScheme.secondary
    val surfColor = MaterialTheme.colorScheme.surfaceVariant
    val onSurfColor = MaterialTheme.colorScheme.onSurfaceVariant

    val bgBrush = if (isPrimary) {
        Brush.horizontalGradient(
            colors = if (enabled) listOf(primColor, secColor) else listOf(surfColor.copy(alpha = 0.5f), surfColor)
        )
    } else {
        Brush.horizontalGradient(
            colors = if (enabled) listOf(primColor.copy(alpha = 0.12f), secColor.copy(alpha = 0.08f)) else listOf(Color.Transparent, Color.Transparent)
        )
    }

    val finalBorderColor = if (isPrimary) {
        if (enabled) secColor.copy(alpha = 0.3f) else surfColor.copy(alpha = 0.2f)
    } else {
        if (enabled) primColor.copy(alpha = 0.2f) else Color.Transparent
    }

    Box(
        modifier = modifier
            .clip(RoundedCornerShape(12.dp))
            .background(bgBrush)
            .border(
                width = 1.dp,
                color = finalBorderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clickable(
                enabled = enabled,
                onClick = {
                    SoundManager.playClick()
                    onClick()
                }
            )
            .padding(vertical = 12.dp, horizontal = 20.dp),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = text,
            color = if (isPrimary) {
                if (enabled) Color.White else onSurfColor.copy(alpha = 0.5f)
            } else {
                if (enabled) primColor else onSurfColor.copy(alpha = 0.5f)
            },
            style = MaterialTheme.typography.labelLarge,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp
        )
    }
}

@Composable
fun AvatarView(
    avatarIndex: Int,
    modifier: Modifier = Modifier,
    size: Dp = 64.dp,
    photoUrl: String? = null
) {
    val icons = listOf(
        Icons.Default.Science,
        Icons.Default.School,
        Icons.Default.WorkspacePremium,
        Icons.Default.MenuBook,
        Icons.Default.Psychology,
        Icons.Default.Person
    )
    val vectorIcon = icons.getOrNull(avatarIndex) ?: Icons.Default.Science
    val ringColor = MaterialTheme.colorScheme.primary

    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(ringColor.copy(alpha = 0.1f))
            .border(2.dp, ringColor.copy(alpha = 0.4f), CircleShape),
        contentAlignment = Alignment.Center
    ) {
        if (!photoUrl.isNullOrEmpty() && (photoUrl.startsWith("http://") || photoUrl.startsWith("https://"))) {
            coil.compose.AsyncImage(
                model = photoUrl,
                contentDescription = "Avatar Character Remote",
                contentScale = androidx.compose.ui.layout.ContentScale.Crop,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(CircleShape)
            )
        } else {
            Icon(
                imageVector = vectorIcon,
                contentDescription = "Avatar Character",
                tint = ringColor,
                modifier = Modifier.size((size.value * 0.5f).dp)
            )
        }
    }
}

@Composable
fun NeoChemBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val primaryColor = MaterialTheme.colorScheme.primary
    val surfColor = MaterialTheme.colorScheme.surfaceVariant

    Box(
        modifier = modifier
            .size(40.dp)
            .clip(RoundedCornerShape(12.dp))
            .background(surfColor.copy(alpha = 0.5f))
            .border(
                width = 1.dp,
                color = primaryColor.copy(alpha = 0.15f),
                shape = RoundedCornerShape(12.dp)
            )
            .clickable {
                SoundManager.playClick()
                onClick()
            },
        contentAlignment = Alignment.Center
    ) {
        Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Kembali",
            tint = primaryColor,
            modifier = Modifier.size(20.dp)
        )
    }
}

@Composable
fun ChemistryText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified,
    fontSize: TextUnit = TextUnit.Unspecified,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    lineHeight: TextUnit = TextUnit.Unspecified
) {
    val annotatedString = remember(text) {
        buildChemistryAnnotatedString(text)
    }
    Text(
        text = annotatedString,
        modifier = modifier,
        color = color,
        fontSize = fontSize,
        fontWeight = fontWeight,
        fontFamily = FontFamily.SansSerif,
        textAlign = textAlign,
        lineHeight = lineHeight
    )
}

fun buildChemistryAnnotatedString(rawText: String): AnnotatedString {
    val cleanText = rawText
        .replace("->", " → ")
        .replace("-->", " → ")
        .replace(" <-> ", " ⇌ ")
        .replace("<=>", " ⇌ ")
    
    return buildAnnotatedString {
        var i = 0
        while (i < cleanText.length) {
            val char = cleanText[i]
            
            if (char.isDigit()) {
                val hasLetterOffset = i > 0 && (
                    cleanText[i - 1].isLetter() || 
                    cleanText[i - 1] == ')' || 
                    cleanText[i - 1] == ']' ||
                    cleanText[i - 1] == '₂' ||
                    cleanText[i - 1] == '₃' ||
                    cleanText[i - 1] == '₄' ||
                    cleanText[i - 1] == '₅' ||
                    cleanText[i - 1] == '₆'
                )
                
                if (hasLetterOffset) {
                    pushStyle(
                        SpanStyle(
                            baselineShift = BaselineShift.Subscript,
                            fontSize = 0.75.em,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    append(char)
                    pop()
                } else {
                    append(char)
                }
            } else if (char.isLetter()) {
                pushStyle(
                    SpanStyle(
                        fontFamily = FontFamily.Serif,
                        fontStyle = FontStyle.Italic,
                        fontWeight = FontWeight.Bold
                    )
                )
                append(char)
                pop()
            } else {
                append(char)
            }
            i++
        }
    }
}
