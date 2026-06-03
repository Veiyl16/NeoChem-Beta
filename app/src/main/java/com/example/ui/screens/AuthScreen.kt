package com.example.ui.screens

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.draw.clip
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Science
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.viewmodel.AuthState
import com.example.ui.viewmodel.AuthViewModel

import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.graphics.vector.path
import androidx.compose.ui.graphics.PathFillType

val GoogleGIcon: ImageVector
    get() = ImageVector.Builder(
        name = "GoogleG",
        defaultWidth = 24.dp,
        defaultHeight = 24.dp,
        viewportWidth = 24f,
        viewportHeight = 24f
    ).apply {
        path(
            fill = androidx.compose.ui.graphics.SolidColor(Color(0xFFEA4335)),
            fillAlpha = 1.0f,
            strokeAlpha = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(12.0f, 5.04f)
            curveTo(13.84f, 5.04f, 15.5f, 5.67f, 16.8f, 6.91f)
            lineTo(20.37f, 3.34f)
            curveTo(18.2f, 1.32f, 15.36f, 0.0f, 12.0f, 0.0f)
            curveTo(7.31f, 0.0f, 3.25f, 2.68f, 1.21f, 6.6f)
            lineTo(5.27f, 9.75f)
            curveTo(6.23f, 6.93f, 8.89f, 5.04f, 12.0f, 5.04f)
            close()
        }
        path(
            fill = androidx.compose.ui.graphics.SolidColor(Color(0xFF4285F4)),
            fillAlpha = 1.0f,
            strokeAlpha = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(23.49f, 12.27f)
            curveTo(23.49f, 11.48f, 23.42f, 10.73f, 23.3f, 10.0f)
            horizontalLineTo(12.0f)
            verticalLineTo(14.51f)
            horizontalLineTo(18.47f)
            curveTo(18.18f, 15.99f, 17.34f, 17.24f, 16.08f, 18.09f)
            lineTo(20.08f, 21.19f)
            curveTo(22.43f, 19.03f, 23.49f, 15.83f, 23.49f, 12.27f)
            close()
        }
        path(
            fill = androidx.compose.ui.graphics.SolidColor(Color(0xFF34A853)),
            fillAlpha = 1.0f,
            strokeAlpha = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(12.0f, 23.0f)
            curveTo(15.24f, 23.0f, 17.96f, 21.92f, 19.96f, 20.06f)
            lineTo(16.08f, 17.06f)
            curveTo(14.99f, 17.78f, 13.62f, 18.23f, 12.0f, 18.23f)
            curveTo(8.89f, 18.23f, 6.23f, 16.34f, 5.27f, 13.52f)
            lineTo(1.21f, 16.67f)
            curveTo(3.25f, 20.6f, 7.31f, 23.27f, 12.0f, 23.0f)
            close()
        }
        path(
            fill = androidx.compose.ui.graphics.SolidColor(Color(0xFFFBBC05)),
            fillAlpha = 1.0f,
            strokeAlpha = 1.0f,
            pathFillType = PathFillType.NonZero
        ) {
            moveTo(5.27f, 13.52f)
            curveTo(5.03f, 12.8f, 4.89f, 12.03f, 4.89f, 11.23f)
            curveTo(4.89f, 10.43f, 5.03f, 9.66f, 5.27f, 8.94f)
            lineTo(1.21f, 5.79f)
            curveTo(0.44f, 7.42f, 0.0f, 9.27f, 0.0f, 11.23f)
            curveTo(0.0f, 13.19f, 0.44f, 15.04f, 1.21f, 16.67f)
            lineTo(5.27f, 13.52f)
            close()
        }
    }.build()

@Composable
fun AuthScreen(
    authViewModel: AuthViewModel,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier
) {
    var isRegisterState by remember { mutableStateOf(false) }
    var username by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedAvatarIndex by remember { mutableStateOf(0) }
    var validationError by remember { mutableStateOf<String?>(null) }
    var showForgotPasswordDialog by remember { mutableStateOf(false) }
    var forgotEmail by remember { mutableStateOf("") }

    val authState by authViewModel.authState.collectAsState()
    val context = LocalContext.current
    
    val primaryColor = MaterialTheme.colorScheme.primary
    val secondaryColor = MaterialTheme.colorScheme.secondary
    val onBgColor = MaterialTheme.colorScheme.onBackground
    val cardBg = MaterialTheme.colorScheme.surface

    LaunchedEffect(authState) {
        if (authState is AuthState.Success) {
            onLoginSuccess()
        }
    }

    if (showForgotPasswordDialog) {
        AlertDialog(
            onDismissRequest = { showForgotPasswordDialog = false },
            title = { Text("Forgot Password?", color = onBgColor, fontWeight = FontWeight.Bold) },
            text = {
                Column {
                    Text(
                        "Enter your email to receive recovery instructions.",
                        color = onBgColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = forgotEmail,
                        onValueChange = { forgotEmail = it },
                        label = { Text("Email Address", color = onBgColor.copy(alpha = 0.8f)) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = onBgColor,
                            unfocusedTextColor = onBgColor,
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = onBgColor.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            },
            confirmButton = {
                TextButton(
                    onClick = {
                        if (forgotEmail.isNotBlank()) {
                            authViewModel.forgotPassword(forgotEmail.trim())
                            showForgotPasswordDialog = false
                        }
                    }
                ) {
                    Text("Send", color = primaryColor, fontWeight = FontWeight.Bold)
                }
            },
            dismissButton = {
                TextButton(onClick = { showForgotPasswordDialog = false }) {
                    Text("Cancel", color = onBgColor.copy(alpha = 0.5f))
                }
            },
            containerColor = cardBg
        )
    }

    FuturisticBg(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp)
                .windowInsetsPadding(WindowInsets.safeDrawing),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "NeoChem",
                color = onBgColor,
                style = MaterialTheme.typography.headlineMedium,
                fontSize = 32.sp,
                fontWeight = FontWeight.Bold
            )

            Text(
                text = "Interactive Chemistry Lab",
                color = secondaryColor,
                style = MaterialTheme.typography.bodyLarge,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium
            )

            Spacer(modifier = Modifier.height(24.dp))

            GlassCard(
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = if (isRegisterState) "Create Account" else "Sign In",
                    color = onBgColor,
                    fontWeight = FontWeight.Bold,
                    fontSize = 20.sp,
                )

                Spacer(modifier = Modifier.height(14.dp))

                val displayError = validationError ?: (authState as? AuthState.Error)?.message
                if (displayError != null) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .border(1.dp, Color(0xFFEF4444), RoundedCornerShape(8.dp))
                            .background(Color(0x1AEF4444))
                            .padding(10.dp)
                    ) {
                        Text(
                            text = displayError,
                            color = Color(0xFFEF4444),
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                }



                OutlinedTextField(
                    value = email,
                    onValueChange = { 
                        email = it
                        validationError = null 
                    },
                    label = { Text("Email", color = onBgColor.copy(alpha = 0.6f)) },
                    leadingIcon = { Icon(Icons.Default.Email, contentDescription = "Email", tint = primaryColor) },
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = onBgColor,
                        unfocusedTextColor = onBgColor,
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = onBgColor.copy(alpha = 0.15f),
                        focusedLabelColor = primaryColor
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("email_input")
                )

                Spacer(modifier = Modifier.height(12.dp))

                if (isRegisterState) {
                    OutlinedTextField(
                        value = username,
                        onValueChange = { 
                            username = it
                            validationError = null
                        },
                        label = { Text("Full Name", color = onBgColor.copy(alpha = 0.6f)) },
                        leadingIcon = { Icon(Icons.Default.Person, contentDescription = "User", tint = primaryColor) },
                        colors = OutlinedTextFieldDefaults.colors(
                            focusedTextColor = onBgColor,
                            unfocusedTextColor = onBgColor,
                            focusedBorderColor = primaryColor,
                            unfocusedBorderColor = onBgColor.copy(alpha = 0.15f)
                        ),
                        modifier = Modifier.fillMaxWidth().testTag("username_input")
                    )

                    Spacer(modifier = Modifier.height(14.dp))

                    Text(
                        text = "Choose your avatar:",
                        color = onBgColor,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(modifier = Modifier.height(8.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        for (i in 0..5) {
                            Box(
                                modifier = Modifier
                                    .clickable { selectedAvatarIndex = i }
                            ) {
                                AvatarView(
                                    avatarIndex = i,
                                    size = 40.dp,
                                    modifier = Modifier.padding(2.dp)
                                )
                                if (selectedAvatarIndex == i) {
                                    Box(
                                        modifier = Modifier
                                            .size(44.dp)
                                            .border(2.dp, primaryColor, RoundedCornerShape(22.dp))
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(14.dp))
                }

                OutlinedTextField(
                    value = password,
                    onValueChange = { 
                        password = it
                        validationError = null
                    },
                    label = { Text("Password", color = onBgColor.copy(alpha = 0.6f)) },
                    leadingIcon = { Icon(Icons.Default.Lock, contentDescription = "Password", tint = primaryColor) },
                    visualTransformation = PasswordVisualTransformation(),
                    colors = OutlinedTextFieldDefaults.colors(
                        focusedTextColor = onBgColor,
                        unfocusedTextColor = onBgColor,
                        focusedBorderColor = primaryColor,
                        unfocusedBorderColor = onBgColor.copy(alpha = 0.15f)
                    ),
                    modifier = Modifier.fillMaxWidth().testTag("password_input")
                )

                if (!isRegisterState) {
                    Spacer(modifier = Modifier.height(6.dp))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.End
                    ) {
                        Text(
                            text = "Forgot password?",
                            color = primaryColor,
                            fontSize = 12.sp,
                            fontWeight = FontWeight.Bold,
                            modifier = Modifier
                                .clickable { showForgotPasswordDialog = true }
                                .padding(vertical = 4.dp)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (authState is AuthState.Loading) {
                    CircularProgressIndicator(
                        color = primaryColor,
                        modifier = Modifier
                            .align(Alignment.CenterHorizontally)
                            .size(36.dp)
                    )
                } else {
                    GlassButton(
                        text = if (isRegisterState) "Sign Up" else "Sign In",
                        modifier = Modifier.fillMaxWidth().testTag("submit_button"),
                        onClick = {
                            val trimmedEmail = email.trim()
                            val trimmedPass = password.trim()
                            
                            if (trimmedEmail.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(trimmedEmail).matches()) {
                                validationError = "Please enter a valid email address."
                                return@GlassButton
                            }
                            
                            if (trimmedPass.length < 6) {
                                validationError = "Password must be at least 6 characters."
                                return@GlassButton
                            }

                            if (isRegisterState) {
                                if (username.isBlank()) {
                                    validationError = "Please enter your full name."
                                    return@GlassButton
                                }
                                authViewModel.register(trimmedEmail, trimmedPass, username.trim(), selectedAvatarIndex)
                            } else {
                                authViewModel.login(trimmedEmail, trimmedPass)
                            }
                        }
                    )

                    Spacer(modifier = Modifier.height(12.dp))
                }

                Spacer(modifier = Modifier.height(16.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = if (isRegisterState) "Already have an account? " else "Don't have an account? ",
                        color = onBgColor.copy(alpha = 0.7f),
                        fontSize = 14.sp
                    )
                    Text(
                        text = if (isRegisterState) "Sign In" else "Sign Up",
                        color = primaryColor,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp,
                        modifier = Modifier
                            .clickable { 
                                isRegisterState = !isRegisterState 
                                validationError = null
                            }
                            .padding(horizontal = 4.dp)
                    )
                }
            }
        }
    }
}
