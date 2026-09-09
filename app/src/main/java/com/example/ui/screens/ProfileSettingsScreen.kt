package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ExitToApp
import androidx.compose.material.icons.filled.Link
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Security
import androidx.compose.material.icons.filled.Shield
import androidx.compose.material.icons.filled.TouchApp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.data.firebase.FirebaseManager
import com.example.model.UserProfile
import com.example.ui.theme.HighClueAmber
import com.example.ui.theme.HighClueEmerald
import com.example.ui.theme.HighClueIndigo
import com.example.ui.theme.HighClueRose
import com.example.ui.theme.HighClueTeal

@Composable
fun ProfileSettingsScreen(
    userProfile: UserProfile,
    onUpdateProfile: (name: String, ageGroup: String, stream: String) -> Unit,
    onConnectGenially: (email: String, token: String) -> Result<String>,
    onLogout: () -> Unit
) {
    var editName by remember { mutableStateOf(userProfile.displayName) }
    var editAgeGroup by remember { mutableStateOf(userProfile.ageGroup) }
    var editStream by remember { mutableStateOf(userProfile.preferredStream) }
    var profileSavedNotice by remember { mutableStateOf(false) }

    var showGeniallyModal by remember { mutableStateOf(false) }
    var showRulesModal by remember { mutableStateOf(false) }
    var showLogoutConfirm by remember { mutableStateOf(false) }

    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background)
            .padding(horizontal = 16.dp),
        contentPadding = PaddingValues(top = 12.dp, bottom = 32.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        // User Profile Card
        item {
            Card(
                shape = RoundedCornerShape(18.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(18.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Box(
                            modifier = Modifier
                                .size(56.dp)
                                .clip(CircleShape)
                                .background(HighClueIndigo.copy(alpha = 0.15f)),
                            contentAlignment = Alignment.Center
                        ) {
                            Icon(
                                imageVector = Icons.Default.Person,
                                contentDescription = null,
                                tint = HighClueIndigo,
                                modifier = Modifier.size(32.dp)
                            )
                        }

                        Spacer(modifier = Modifier.width(14.dp))

                        Column {
                            Text(
                                text = userProfile.displayName,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.Bold
                            )
                            Text(
                                text = userProfile.email.ifBlank { "Guest Student Account" },
                                fontSize = 12.sp,
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Surface(
                                shape = RoundedCornerShape(6.dp),
                                color = HighClueIndigo.copy(alpha = 0.12f)
                            ) {
                                Text(
                                    text = "Age Group: ${userProfile.ageGroup} years",
                                    fontSize = 11.sp,
                                    fontWeight = FontWeight.Bold,
                                    color = HighClueIndigo,
                                    modifier = Modifier.padding(horizontal = 6.dp, vertical = 2.dp)
                                )
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    OutlinedTextField(
                        value = editName,
                        onValueChange = { editName = it },
                        label = { Text("Display / Nickname") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    // Age Bracket Selector
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("Age Bracket:", fontSize = 13.sp, fontWeight = FontWeight.Medium)
                        Row {
                            listOf("11–13", "14–17").forEach { age ->
                                val isSelected = editAgeGroup == age
                                Surface(
                                    shape = RoundedCornerShape(8.dp),
                                    color = if (isSelected) HighClueIndigo else MaterialTheme.colorScheme.surfaceVariant,
                                    modifier = Modifier
                                        .padding(horizontal = 4.dp)
                                        .clip(RoundedCornerShape(8.dp))
                                        .clickable { editAgeGroup = age }
                                ) {
                                    Text(
                                        text = "$age yrs",
                                        fontSize = 12.sp,
                                        fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                                        color = if (isSelected) Color.White else MaterialTheme.colorScheme.onSurface,
                                        modifier = Modifier.padding(horizontal = 10.dp, vertical = 6.dp)
                                    )
                                }
                            }
                        }
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedTextField(
                        value = editStream,
                        onValueChange = { editStream = it },
                        label = { Text("Target School Stream / Area of Interest") },
                        placeholder = { Text("e.g. Science & Computing, Arts & Law") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    Spacer(modifier = Modifier.height(12.dp))

                    Button(
                        onClick = {
                            onUpdateProfile(editName, editAgeGroup, editStream)
                            profileSavedNotice = true
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = HighClueIndigo),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text("Save Profile Changes")
                    }

                    if (profileSavedNotice) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(
                            text = "✓ Profile updated successfully",
                            fontSize = 12.sp,
                            color = HighClueEmerald,
                            fontWeight = FontWeight.SemiBold
                        )
                    }
                }
            }
        }

        // Genially Integration Status Card
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Icon(Icons.Default.TouchApp, contentDescription = null, tint = HighClueTeal)
                            Spacer(modifier = Modifier.width(8.dp))
                            Text(
                                text = "Genially Integration",
                                fontSize = 15.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        Surface(
                            shape = RoundedCornerShape(6.dp),
                            color = if (userProfile.geniallyConnected) HighClueEmerald.copy(alpha = 0.15f) else MaterialTheme.colorScheme.surfaceVariant
                        ) {
                            Text(
                                text = if (userProfile.geniallyConnected) "Connected" else "Not Connected",
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold,
                                color = if (userProfile.geniallyConnected) HighClueEmerald else MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f),
                                modifier = Modifier.padding(horizontal = 8.dp, vertical = 3.dp)
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = if (userProfile.geniallyConnected)
                            "Connected account: ${userProfile.geniallyAccountEmail}. Interactive lessons and modules are synced."
                        else
                            "Connect your official Genially account to sync custom interactive learning lessons, clickable elements, and branching career scenarios.",
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    OutlinedButton(
                        onClick = { showGeniallyModal = true },
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Icon(Icons.Default.Link, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(if (userProfile.geniallyConnected) "Manage Connection" else "Connect Genially Account")
                    }
                }
            }
        }

        // Security & Minor Privacy Policy (COPPA / FERPA Compliance)
        item {
            Card(
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                modifier = Modifier.fillMaxWidth()
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(Icons.Default.Shield, contentDescription = null, tint = HighClueIndigo)
                        Spacer(modifier = Modifier.width(8.dp))
                        Text(
                            text = "Minor Privacy & Data Protection",
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Bold
                        )
                    }

                    Spacer(modifier = Modifier.height(8.dp))

                    Text(
                        text = "HighClue is built for youth aged 11–17. We collect zero invasive personal data. Your notes, assessment responses, and progress are stored privately and accessible strictly by your account.",
                        fontSize = 12.sp,
                        lineHeight = 17.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    TextButton(onClick = { showRulesModal = true }) {
                        Icon(Icons.Default.Security, contentDescription = null, modifier = Modifier.size(16.dp))
                        Spacer(modifier = Modifier.width(6.dp))
                        Text("View Firebase Security Rules Specification", fontSize = 12.sp, color = HighClueIndigo)
                    }
                }
            }
        }

        // Logout Button
        item {
            Button(
                onClick = { showLogoutConfirm = true },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(48.dp),
                colors = ButtonDefaults.buttonColors(containerColor = HighClueRose.copy(alpha = 0.12f)),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(Icons.Default.ExitToApp, contentDescription = null, tint = HighClueRose, modifier = Modifier.size(18.dp))
                Spacer(modifier = Modifier.width(8.dp))
                Text(
                    text = "Log Out / Switch Student",
                    color = HighClueRose,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }

    // Genially Connect Dialog
    if (showGeniallyModal) {
        var emailInput by remember { mutableStateOf(userProfile.geniallyAccountEmail ?: "") }
        var tokenInput by remember { mutableStateOf("") }
        var connectError by remember { mutableStateOf<String?>(null) }
        var connectSuccess by remember { mutableStateOf(false) }

        AlertDialog(
            onDismissRequest = { showGeniallyModal = false },
            title = { Text("Connect Genially Account", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "HighClue connects through official Genially credentials. We never claim your account is connected unless validation succeeds.",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.75f)
                    )
                    Spacer(modifier = Modifier.height(12.dp))
                    OutlinedTextField(
                        value = emailInput,
                        onValueChange = { emailInput = it },
                        label = { Text("Genially Account Email") },
                        placeholder = { Text("creator@school.edu") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    OutlinedTextField(
                        value = tokenInput,
                        onValueChange = { tokenInput = it },
                        label = { Text("Workspace Key / Creator URL") },
                        placeholder = { Text("https://app.genially.com/workspace/...") },
                        singleLine = true,
                        modifier = Modifier.fillMaxWidth()
                    )

                    if (connectError != null) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text(connectError!!, fontSize = 11.sp, color = MaterialTheme.colorScheme.error)
                    }

                    if (connectSuccess) {
                        Spacer(modifier = Modifier.height(6.dp))
                        Text("✓ Verified & connected to Genially Edu Workspace!", fontSize = 11.sp, color = HighClueEmerald)
                    }
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        val res = onConnectGenially(emailInput, tokenInput)
                        if (res.isSuccess) {
                            connectSuccess = true
                            connectError = null
                        } else {
                            connectError = res.exceptionOrNull()?.message ?: "Validation failed."
                        }
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HighClueIndigo)
                ) {
                    Text("Verify & Connect")
                }
            },
            dismissButton = {
                TextButton(onClick = { showGeniallyModal = false }) {
                    Text("Close")
                }
            }
        )
    }

    // Firebase Security Rules Dialog
    if (showRulesModal) {
        AlertDialog(
            onDismissRequest = { showRulesModal = false },
            title = { Text("Firestore Security Rules", fontSize = 15.sp, fontWeight = FontWeight.Bold) },
            text = {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "Production rule specification verifying ownership & minor protection:",
                        fontSize = 12.sp,
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.7f)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        color = MaterialTheme.colorScheme.surfaceVariant,
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(
                            text = FirebaseManager.firestoreRulesDefinition,
                            fontSize = 10.sp,
                            fontFamily = androidx.compose.ui.text.font.FontFamily.Monospace,
                            modifier = Modifier.padding(10.dp)
                        )
                    }
                }
            },
            confirmButton = {
                Button(onClick = { showRulesModal = false }, colors = ButtonDefaults.buttonColors(containerColor = HighClueIndigo)) {
                    Text("Done")
                }
            }
        )
    }

    // Logout Confirmation Dialog
    if (showLogoutConfirm) {
        AlertDialog(
            onDismissRequest = { showLogoutConfirm = false },
            title = { Text("Log Out?", fontSize = 16.sp, fontWeight = FontWeight.Bold) },
            text = {
                Text("Your progress is securely preserved. You can log back in anytime.", fontSize = 13.sp)
            },
            confirmButton = {
                Button(
                    onClick = {
                        showLogoutConfirm = false
                        onLogout()
                    },
                    colors = ButtonDefaults.buttonColors(containerColor = HighClueRose)
                ) {
                    Text("Log Out")
                }
            },
            dismissButton = {
                TextButton(onClick = { showLogoutConfirm = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
