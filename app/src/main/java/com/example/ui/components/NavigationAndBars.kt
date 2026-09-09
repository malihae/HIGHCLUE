package com.example.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AutoAwesome
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Explore
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.MenuBook
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Psychology
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.filled.Timeline
import androidx.compose.material.icons.outlined.AutoAwesome
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.MenuBook
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Psychology
import androidx.compose.material.icons.outlined.Timeline
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.ui.theme.ArialBlackFontFamily
import com.example.ui.theme.HighClueAmber
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
import com.example.ui.theme.HighClueTealLight
import com.example.ui.theme.HighClueWhite
import com.example.ui.theme.HighClueWhiteMuted
import com.example.ui.theme.HighClueWhiteOff

sealed class Screen(val route: String, val title: String) {
    object Splash : Screen("splash", "Welcome")
    object Auth : Screen("auth", "Sign In")
    object Home : Screen("home", "Home")
    object Dashboard : Screen("dashboard", "Dashboard")
    object Explore : Screen("explore", "Explore")
    object CourseDetail : Screen("course_detail", "Course")
    object ModuleViewer : Screen("module_viewer", "Module")
    object InterestLeveler : Screen("interest_leveler", "Leveler")
    object Progress : Screen("progress", "Progress")
    object Notes : Screen("notes", "Notes")
    object AiGuidance : Screen("ai_guidance", "AI Guide")
    object Profile : Screen("profile", "Profile")
    object Completion : Screen("completion", "Course Completion")
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HighClueTopBar(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit,
    onSearchClick: () -> Unit = {}
) {
    TopAppBar(
        title = {
            Row(verticalAlignment = Alignment.CenterVertically) {
                // HighClue Logo with Grey Background, White Icon & Arial Black Typography
                Box(
                    modifier = Modifier
                        .size(38.dp)
                        .clip(RoundedCornerShape(10.dp))
                        .background(
                            Brush.verticalGradient(
                                listOf(HighClueLogoGrey, HighClueLogoGreyDark)
                            )
                        )
                        .border(1.dp, HighClueLogoGreyBorder, RoundedCornerShape(10.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Explore,
                        contentDescription = "HighClue Icon",
                        tint = HighClueWhite,
                        modifier = Modifier.size(22.dp)
                    )
                }
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = "HighClue",
                    fontFamily = ArialBlackFontFamily,
                    fontWeight = FontWeight.Black,
                    fontSize = 20.sp,
                    color = HighClueWhite
                )
                Spacer(modifier = Modifier.width(6.dp))
                Surface(
                    shape = RoundedCornerShape(4.dp),
                    color = HighClueGreySurfaceVariant,
                    border = androidx.compose.foundation.BorderStroke(0.5.dp, HighClueGreyBorder),
                    modifier = Modifier.padding(bottom = 2.dp)
                ) {
                    Text(
                        text = "11–17",
                        fontFamily = ArialBlackFontFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = HighClueWhite,
                        modifier = Modifier.padding(horizontal = 5.dp, vertical = 2.dp)
                    )
                }
            }
        },
        actions = {
            IconButton(onClick = onSearchClick) {
                Icon(
                    imageVector = Icons.Default.Search,
                    contentDescription = "Search",
                    tint = HighClueWhite
                )
            }
            IconButton(onClick = { onNavigate(Screen.Notes) }) {
                Icon(
                    imageVector = Icons.Default.MenuBook,
                    contentDescription = "Student Notes",
                    tint = if (currentScreen == Screen.Notes) HighClueTealLight else HighClueWhite
                )
            }
            IconButton(onClick = { onNavigate(Screen.Profile) }) {
                Box(
                    modifier = Modifier
                        .size(34.dp)
                        .clip(CircleShape)
                        .background(HighClueGreySurfaceVariant)
                        .border(1.dp, HighClueGreyBorder, CircleShape),
                    contentAlignment = Alignment.Center
                ) {
                    Icon(
                        imageVector = Icons.Default.Person,
                        contentDescription = "Profile",
                        tint = HighClueWhite,
                        modifier = Modifier.size(18.dp)
                    )
                }
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = HighClueGreyNavbar,
            titleContentColor = HighClueWhite,
            actionIconContentColor = HighClueWhite
        )
    )
}

@Composable
fun HighClueBottomNav(
    currentScreen: Screen,
    onNavigate: (Screen) -> Unit
) {
    NavigationBar(
        containerColor = HighClueGreyNavbar,
        tonalElevation = 6.dp
    ) {
        val items = listOf(
            Triple(Screen.Home, "Home", Icons.Default.Home to Icons.Outlined.Home),
            Triple(Screen.Explore, "Explore", Icons.Default.Explore to Icons.Outlined.Explore),
            Triple(Screen.InterestLeveler, "Leveler", Icons.Default.Psychology to Icons.Outlined.Psychology),
            Triple(Screen.Progress, "Progress", Icons.Default.Timeline to Icons.Outlined.Timeline),
            Triple(Screen.AiGuidance, "AI Guide", Icons.Default.AutoAwesome to Icons.Outlined.AutoAwesome)
        )

        items.forEach { (screen, label, icons) ->
            val isSelected = currentScreen == screen
            NavigationBarItem(
                selected = isSelected,
                onClick = { onNavigate(screen) },
                icon = {
                    Icon(
                        imageVector = if (isSelected) icons.first else icons.second,
                        contentDescription = label,
                        tint = if (isSelected) HighClueWhite else HighClueWhiteMuted
                    )
                },
                label = {
                    Text(
                        text = label,
                        fontFamily = ArialBlackFontFamily,
                        fontSize = 10.sp,
                        fontWeight = FontWeight.Black,
                        color = if (isSelected) HighClueWhite else HighClueWhiteMuted
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = HighClueWhite,
                    selectedTextColor = HighClueWhite,
                    indicatorColor = HighClueGreySurfaceVariant,
                    unselectedIconColor = HighClueWhiteMuted,
                    unselectedTextColor = HighClueWhiteMuted
                )
            )
        }
    }
}
