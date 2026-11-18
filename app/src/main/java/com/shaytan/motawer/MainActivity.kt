
package com.shaytan.motawer

import android.net.Uri
import android.os.Bundle
import android.widget.MediaController
import android.widget.VideoView
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView

private val BG = Color(0xFF090910)
private val PURPLE = Color(0xFF963CFF)
private val WHITE = Color.White
private val CARD = Color(0xFF191923)

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
    var selectedTab by remember { mutableIntStateOf(0) }
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var message by remember { mutableStateOf("") }

    val videoPicker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenDocument()
    ) { uri ->
        if (uri != null) {
            videoUri = uri
            message = ""
        }
    }

    if (videoUri != null) {
        EditorScreen(
            videoUri = videoUri!!,
            onBack = { videoUri = null },
            onChooseAnother = {
                videoPicker.launch(arrayOf("video/*"))
            }
        )
        return
    }

    Scaffold(
        containerColor = BG,
        bottomBar = {
            NavigationBar(containerColor = Color(0xFF151520)) {
                val tabs = listOf("الرئيسية", "مشاريعي", "حسابي")
                val icons = listOf("⌂", "▣", "●")

                tabs.forEachIndexed { index, title ->
                    NavigationBarItem(
                        selected = selectedTab == index,
                        onClick = { selectedTab = index },
                        icon = {
                            Text(icons[index], fontSize = 23.sp)
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
                .verticalScroll(rememberScrollState())
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
                    .height(200.dp)
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
                        "ابدأ مشروعك الإبداعي الآن",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )

                    Spacer(Modifier.height(20.dp))

                    Button(
                        onClick = {
                            videoPicker.launch(arrayOf("video/*"))
                        },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = WHITE,
                            contentColor = Color(0xFF54228C)
                        ),
                        shape = RoundedCornerShape(14.dp)
                    ) {
                        Text(
                            "＋ مشروع جديد",
                            fontWeight = FontWeight.Bold
                        )
                    }
                }
            }

            if (message.isNotEmpty()) {
                Spacer(Modifier.height(12.dp))
                Text(message, color = PURPLE, fontSize = 13.sp)
            }

            Spacer(Modifier.height(28.dp))

            Text(
                "اكتشف أدوات الإبداع",
                color = WHITE,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(15.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ToolCard("🎬", "الفيديو", "تحرير الفيديو") {
                    videoPicker.launch(arrayOf("video/*"))
                }
                ToolCard("✨", "التأثيرات", "فلاتر وحركات") {
                    message = "قسم التأثيرات قيد التطوير"
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                ToolCard("🎨", "الألوان", "تعديل الألوان") {
                    message = "قسم الألوان قيد التطوير"
                }
                ToolCard("🎵", "الصوت", "الموسيقى") {
                    message = "قسم الصوت قيد التطوير"
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
                    .background(CARD, RoundedCornerShape(18.dp)),
                contentAlignment = Alignment.Center
            ) {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("▣", color = PURPLE, fontSize = 30.sp)
                    Text(
                        "لا توجد مشاريع محفوظة بعد",
                        color = Color.LightGray,
                        fontSize = 14.sp
                    )
                }
            }

            Spacer(Modifier.height(25.dp))
        }
    }
}

@Composable
fun ToolCard(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .height(125.dp)
            .background(CARD, RoundedCornerShape(18.dp))
            .clickable { onClick() }
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

@Composable
fun EditorScreen(
    videoUri: Uri,
    onBack: () -> Unit,
    onChooseAnother: () -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
            .verticalScroll(rememberScrollState())
            .padding(16.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "‹ رجوع",
                color = PURPLE,
                fontSize = 18.sp,
                modifier = Modifier.clickable { onBack() }
            )

            Spacer(Modifier.weight(1f))

            Text(
                "محرر الفيديو",
                color = WHITE,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
        }

        Spacer(Modifier.height(20.dp))

        Text(
            "معاينة الفيديو",
            color = WHITE,
            fontSize = 18.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(12.dp))

        key(videoUri) {
            AndroidView(
                factory = { context ->
                    VideoView(context).apply {
                        setVideoURI(videoUri)

                        val controller = MediaController(context)
                        controller.setAnchorView(this)
                        setMediaController(controller)

                        setOnPreparedListener { player ->
                            player.isLooping = true
                            start()
                        }
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(310.dp)
                    .background(Color.Black, RoundedCornerShape(18.dp))
            )
        }

        Spacer(Modifier.height(24.dp))

        Text(
            "أدوات التحرير",
            color = WHITE,
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(Modifier.height(15.dp))

        val tools = listOf(
            "✂️" to "قص الفيديو",
            "🎵" to "إضافة صوت",
            "✨" to "التأثيرات",
            "🎨" to "الألوان",
            "📝" to "النصوص",
            "⚡" to "السرعة"
        )

        tools.chunked(2).forEach { pair ->
            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                pair.forEach { tool ->
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .background(CARD, RoundedCornerShape(16.dp))
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(tool.first, fontSize = 27.sp)
                        Spacer(Modifier.height(8.dp))
                        Text(
                            tool.second,
                            color = WHITE,
                            fontSize = 13.sp
                        )
                    }
                }
                if (pair.size == 1) {
                    Spacer(Modifier.weight(1f))
                }
            }
            Spacer(Modifier.height(12.dp))
        }

        OutlinedButton(
            onClick = onChooseAnother,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("اختيار فيديو آخر", color = PURPLE)
        }

        Spacer(Modifier.height(20.dp))
    }
}

