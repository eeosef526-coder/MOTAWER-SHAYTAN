package com.shaytan.motawer

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val BG = Color(0xFF090910)
private val PURPLE = Color(0xFF963CFF)
private val WHITE = Color.White

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen() {
    var selectedTab by remember { mutableStateOf(0) }
    var message by remember { mutableStateOf("") }

    Scaffold(
        containerColor = BG,
        snackbarHost = {
            SnackbarHost(remember { SnackbarHostState() })
        },
        bottomBar = {
            NavigationBar(
                containerColor = Color(0xFF151520)
            ) {
                val tabs = listOf("الرئيسية", "مشاريعي", "حسابي")
                val icons = listOf("⌂", "▣", "●")

                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Text(
                                icons[index],
                                fontSize = 23.sp
                            )
                        },
                        label = {
                            Text(title, fontSize = 11.sp)
                        },
                        colors = NavigationBarItemDefaults.colors(
                            selectedIconColor = PURPLE,
                            selectedTextColor = PURPLE,
                            indicatorColor = Color(0xFF302044),
                            unselectedIconColor = Color.Gray,
                            unselectedTextColor = Color.Gray
                        )
                    )
                }
            }
        }
    ) { padding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .background(BG)
                .padding(horizontal = 18.dp)
        ) {
            Spacer(Modifier.height(22.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "مـطـوّر شـيـطـآن",
                        color = WHITE,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        "VIDEO CREATOR",
                        color = PURPLE,
                        fontSize = 11.sp,
                        letterSpacing = 3.sp
                    )
                }

                Spacer(Modifier.weight(1f))

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .background(
                            Brush.linearGradient(
                                listOf(PURPLE, Color(0xFF38205B))
                            ),
                            CircleShape
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Text("✦", color = WHITE, fontSize = 28.sp)
                }
            }

            Spacer(Modifier.height(28.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(
                        Brush.linearGradient(
                            listOf(
                                Color(0xFF6325A5),
                                Color(0xFF241438),
                                Color(0xFF151520)
                            )
                        ),
                        RoundedCornerShape(25.dp)
                    )
                    .padding(22.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text(
                        "اصنع فيديوهاتك",
                        color = WHITE,
                        fontSize = 25.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(8.dp))

                    Text(
                        "أطلق إبداعك بلا حدود",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            message = "سيتم تجهيز استيراد الفيديو في الخطوة القادمة"
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WHITE,
                            contentColor = Color(0xFF54228C)
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            "＋  مشروع جديد",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            Spacer(Modifier.height(28.dp))

            Text(
                "اكتشف أدوات الإبداع",
                color = WHITE,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(15.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                item {
                    ToolCard("🎬", "الفيديو", "تحرير الفيديو")
                }
                item {
                    ToolCard("✨", "التأثيرات", "فلاتر وحركات")
                }
                item {
                    ToolCard("🎨", "الألوان", "تعديل الألوان")
                }
                item {
                    ToolCard("🎵", "الصوت", "الموسيقى")
                }
            }

            Spacer(Modifier.height(28.dp))

            Text(
                "مشاريعك الأخيرة",
                color = WHITE,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(14.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(115.dp)
                    .background(
                        Color(0xFF171722),
                        RoundedCornerShape(18.dp)
                    ),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("▣", color = PURPLE, fontSize = 30.sp)
                    Text(
                        "لا توجد مشاريع بعد",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
            }
        }
    }
}

@Composable
fun ToolCard(
    icon: String,
    title: String,
    subtitle: String
) {
    Column(
        modifier = Modifier
            .width(145.dp)
            .height(125.dp)
            .background(
                Color(0xFF191923),
                RoundedCornerShape(18.dp)
            )
            .clickable { }
            .padding(14.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(icon, fontSize = 29.sp)
        Spacer(Modifier.height(8.dp))
        Text(
            title,
            color = WHITE,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp
        )
        Text(
            subtitle,
            color = Color.Gray,
            fontSize = 11.sp
        )
    }
}
