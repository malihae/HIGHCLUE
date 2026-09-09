package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowForward
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.components.Screen
import com.example.ui.theme.ArialBlackFontFamily
import com.example.ui.theme.HighClueAmber
import com.example.ui.theme.HighClueGreyBg
import com.example.ui.theme.HighClueGreyBorder
import com.example.ui.theme.HighClueGreyNavbar
import com.example.ui.theme.HighClueGreySurface
import com.example.ui.theme.HighClueGreySurfaceElevated
import com.example.ui.theme.HighClueGreySurfaceVariant
import com.example.ui.theme.HighClueIndigo
import com.example.ui.theme.HighClueLogoGrey
import com.example.ui.theme.HighClueLogoGreyBorder
import com.example.ui.theme.HighClueLogoGreyDark
import com.example.ui.theme.HighClueTeal
import com.example.ui.theme.HighClueWhite
import com.example.ui.theme.HighClueWhiteMuted
import com.example.ui.theme.HighClueWhiteOff

@Composable
fun SplashScreen(
    onStartExploring: () -> Unit,
    onSignIn: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    listOf(
                        HighClueGreyBg,
                        HighClueGreyNavbar,
                        HighClueGreySurface
                    )
                )
            )
            .padding(24.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // HighClue Brand Logo: Grey Background, Crisp White Icon, Arial Black Typography
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .clip(RoundedCornerShape(26.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(HighClueLogoGrey, HighClueLogoGreyDark)
                        )
                    )
                    .border(2.dp, HighClueLogoGreyBorder, RoundedCornerShape(26.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = "HighClue Logo",
                    tint = HighClueWhite,
                    modifier = Modifier.size(56.dp)
                )
            }

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "HIGHCLUE",
                fontFamily = ArialBlackFontFamily,
                fontSize = 32.sp,
                fontWeight = FontWeight.Black,
                letterSpacing = 2.sp,
                color = HighClueWhite
            )

            Spacer(modifier = Modifier.height(10.dp))

            Surface(
                shape = RoundedCornerShape(16.dp),
                color = HighClueGreySurfaceVariant,
                border = androidx.compose.foundation.BorderStroke(1.dp, HighClueGreyBorder),
                modifier = Modifier.padding(vertical = 4.dp)
            ) {
                Text(
                    text = "Ages 11–17 • Career Discovery & Learning",
                    fontFamily = ArialBlackFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = HighClueWhite,
                    modifier = Modifier.padding(horizontal = 14.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Text(
                text = "Discover subjects, careers, and future pathways before choosing high school streams or college majors.",
                fontFamily = ArialBlackFontFamily,
                textAlign = TextAlign.Center,
                fontSize = 14.sp,
                lineHeight = 22.sp,
                color = HighClueWhiteOff,
                modifier = Modifier.padding(horizontal = 16.dp)
            )

            Spacer(modifier = Modifier.height(14.dp))

            Surface(
                shape = RoundedCornerShape(8.dp),
                color = HighClueGreySurfaceElevated,
                border = androidx.compose.foundation.BorderStroke(0.5.dp, HighClueGreyBorder)
            ) {
                Text(
                    text = "Career exploration, not career prediction.",
                    fontFamily = ArialBlackFontFamily,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Black,
                    color = HighClueWhite,
                    modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp)
                )
            }

            Spacer(modifier = Modifier.height(36.dp))

            Button(
                onClick = onStartExploring,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(54.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = HighClueGreySurfaceElevated,
                    contentColor = HighClueWhite
                ),
                border = androidx.compose.foundation.BorderStroke(1.5.dp, HighClueWhite)
            ) {
                Text(
                    text = "Start Exploring",
                    fontFamily = ArialBlackFontFamily,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Black,
                    color = HighClueWhite
                )
                Spacer(modifier = Modifier.width(8.dp))
                Icon(Icons.Default.ArrowForward, contentDescription = null, tint = HighClueWhite, modifier = Modifier.size(18.dp))
            }

            Spacer(modifier = Modifier.height(14.dp))

            OutlinedButton(
                onClick = onSignIn,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                shape = RoundedCornerShape(14.dp),
                colors = ButtonDefaults.outlinedButtonColors(
                    containerColor = HighClueGreySurface,
                    contentColor = HighClueWhite
                ),
                border = androidx.compose.foundation.BorderStroke(1.dp, HighClueGreyBorder)
            ) {
                Text(
                    text = "Sign In / Student Profile",
                    fontFamily = ArialBlackFontFamily,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Black,
                    color = HighClueWhite
                )
            }
        }
    }
}

@Composable
fun AuthScreen(
    onAuthSuccess: (displayName: String) -> Unit,
    onGuestContinue: () -> Unit
) {
    var isSignUp by remember { mutableStateOf(false) }
    var name by remember { mutableStateOf("") }
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var selectedAgeGroup by remember { mutableStateOf("14–17") }
    var errorMessage by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HighClueGreyBg)
            .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // HighClue Logo Header
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.padding(bottom = 16.dp)
        ) {
            Box(
                modifier = Modifier
                    .size(42.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(
                        Brush.verticalGradient(
                            listOf(HighClueLogoGrey, HighClueLogoGreyDark)
                        )
                    )
                    .border(1.dp, HighClueLogoGreyBorder, RoundedCornerShape(12.dp)),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Default.Explore,
                    contentDescription = "Logo",
                    tint = HighClueWhite,
                    modifier = Modifier.size(24.dp)
                )
            }
            Spacer(modifier = Modifier.width(10.dp))
            Text(
                text = "HIGHCLUE",
                fontFamily = ArialBlackFontFamily,
                fontWeight = FontWeight.Black,
                fontSize = 22.sp,
                color = HighClueWhite
            )
        }

        Text(
            text = if (isSignUp) "Create Student Account" else "Welcome Back",
            fontFamily = ArialBlackFontFamily,
            fontSize = 22.sp,
            fontWeight = FontWeight.Black,
            color = HighClueWhite
        )

        Spacer(modifier = Modifier.height(6.dp))

        Text(
            text = if (isSignUp) "Join HighClue to save progress and bookmarks" else "Sign in to resume your career exploration journey",
            fontFamily = ArialBlackFontFamily,
            fontSize = 12.sp,
            color = HighClueWhiteOff,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.height(18.dp))

        // Minor Privacy Notice (COPPA / FERPA Aligned)
        Card(
            shape = RoundedCornerShape(10.dp),
            colors = CardDefaults.cardColors(containerColor = HighClueGreySurfaceVariant),
            border = androidx.compose.foundation.BorderStroke(1.dp, HighClueGreyBorder),
            modifier = Modifier.fillMaxWidth()
        ) {
            Row(
                modifier = Modifier.padding(12.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    imageVector = Icons.Default.Shield,
                    contentDescription = null,
                    tint = HighClueWhite,
                    modifier = Modifier.size(20.dp)
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "Youth Privacy Guarantee: We do not collect real names, phone numbers, or track minors.",
                    fontFamily = ArialBlackFontFamily,
                    fontSize = 11.sp,
                    lineHeight = 15.sp,
                    color = HighClueWhiteOff
                )
            }
        }

        Spacer(modifier = Modifier.height(18.dp))

        val textFieldColors = OutlinedTextFieldDefaults.colors(
            focusedTextColor = HighClueWhite,
            unfocusedTextColor = HighClueWhite,
            focusedContainerColor = HighClueGreySurface,
            unfocusedContainerColor = HighClueGreySurface,
            focusedBorderColor = HighClueWhite,
            unfocusedBorderColor = HighClueGreyBorder,
            cursorColor = HighClueWhite,
            focusedLabelColor = HighClueWhite,
            unfocusedLabelColor = HighClueWhiteMuted,
            focusedLeadingIconColor = HighClueWhite,
            unfocusedLeadingIconColor = HighClueWhiteMuted
        )

        if (isSignUp) {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text("Student Nickname", fontFamily = ArialBlackFontFamily) },
                placeholder = { Text("e.g. Alex, CuriousMind42", fontFamily = ArialBlackFontFamily, color = HighClueWhiteMuted) },
                leadingIcon = { Icon(Icons.Default.Person, contentDescription = null) },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(12.dp),
                colors = textFieldColors
            )
            Spacer(modifier = Modifier.height(10.dp))

            // Age Bracket Selector
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = "Age Group:",
                    fontFamily = ArialBlackFontFamily,
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Black,
                    color = HighClueWhite
                )
                Row {
                    listOf("11–13", "14–17").forEach { age ->
                        val isSelected = selectedAgeGroup == age
                        Surface(
                            shape = RoundedCornerShape(8.dp),
                            color = if (isSelected) HighClueGreySurfaceElevated else HighClueGreySurface,
                            border = androidx.compose.foundation.BorderStroke(
                                1.dp,
                                if (isSelected) HighClueWhite else HighClueGreyBorder
                            ),
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .clip(RoundedCornerShape(8.dp))
                                .clickable { selectedAgeGroup = age }
                        ) {
                            Text(
                                text = "$age yrs",
                                fontFamily = ArialBlackFontFamily,
                                fontSize = 12.sp,
                                fontWeight = FontWeight.Black,
                                color = HighClueWhite,
                                modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                            )
                        }
                    }
                }
            }
            Spacer(modifier = Modifier.height(10.dp))
        }

        OutlinedTextField(
            value = email,
            onValueChange = { email = it },
            label = { Text("School or Student Email", fontFamily = ArialBlackFontFamily) },
            placeholder = { Text("student@school.edu", fontFamily = ArialBlackFontFamily, color = HighClueWhiteMuted) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors
        )

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password", fontFamily = ArialBlackFontFamily) },
            visualTransformation = PasswordVisualTransformation(),
            leadingIcon = { Icon(Icons.Default.Lock, contentDescription = null) },
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            shape = RoundedCornerShape(12.dp),
            colors = textFieldColors
        )

        if (errorMessage != null) {
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = errorMessage!!,
                fontFamily = ArialBlackFontFamily,
                color = Color(0xFFF87171),
                fontSize = 12.sp
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        Button(
            onClick = {
                if (email.isBlank() || password.isBlank()) {
                    errorMessage = "Please enter an email and password."
                } else {
                    onAuthSuccess(if (name.isNotBlank()) name else "Student Explorer")
                }
            },
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.buttonColors(
                containerColor = HighClueGreySurfaceElevated,
                contentColor = HighClueWhite
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, HighClueWhite)
        ) {
            Text(
                text = if (isSignUp) "Create Account" else "Sign In",
                fontFamily = ArialBlackFontFamily,
                fontSize = 15.sp,
                fontWeight = FontWeight.Black,
                color = HighClueWhite
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        OutlinedButton(
            onClick = onGuestContinue,
            modifier = Modifier
                .fillMaxWidth()
                .height(48.dp),
            shape = RoundedCornerShape(12.dp),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = HighClueGreySurface,
                contentColor = HighClueWhite
            ),
            border = androidx.compose.foundation.BorderStroke(1.dp, HighClueGreyBorder)
        ) {
            Text(
                text = "Explore as Guest Student",
                fontFamily = ArialBlackFontFamily,
                fontSize = 13.sp,
                fontWeight = FontWeight.Black,
                color = HighClueWhite
            )
        }

        Spacer(modifier = Modifier.height(12.dp))

        TextButton(onClick = { isSignUp = !isSignUp }) {
            Text(
                text = if (isSignUp) "Already have an account? Sign In" else "New to HighClue? Create an Account",
                fontFamily = ArialBlackFontFamily,
                fontSize = 12.sp,
                fontWeight = FontWeight.Black,
                color = HighClueWhite
            )
        }
    }
}
