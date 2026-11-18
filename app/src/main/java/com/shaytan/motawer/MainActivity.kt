package com.shaytan.motawer

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.common.MediaItem
import androidx.media3.ui.PlayerView

private val Background = Color(0xFF080808)
private val CardColor = Color(0xFF171717)
private val Accent = Color(0xFFFF2525)
private val White = Color.White
private val Gray = Color(0xFFAAAAAA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = Accent,
                    background = Background,
                    surface = CardColor
                )
            ) {
                EditorHome()
            }
        }
    }
}

@Composable
fun EditorHome() {
    val context = LocalContext.current

    var selectedVideo by remember { mutableStateOf<Uri?>(null) }
    var message by remember { mutableStateOf("مرحباً بك في مطور شيطان") }

    val videoPicker = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            selectedVideo = uri
            message = "تم اختيار الفيديو بنجاح"
        }
    }

    val player = remember(selectedVideo) {
        selectedVideo?.let { uri ->
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(uri))
                prepare()
                playWhenReady = false
            }
        }
    }

    DisposableEffect(player) {
        onDispose {
            player?.release()
        }
    }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = Background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(18.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        "MOTAWER",
                        color = White,
                        fontSize = 27.sp,
                        fontWeight = FontWeight.Black
                    )
                    Text(
                        "SHAYTAN EDITOR",
                        color = Accent,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Bold
                    )
                }

                Box(
                    modifier = Modifier
                        .size(48.dp)
                        .border(1.dp, Accent, RoundedCornerShape(15.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Text("♛", color = Accent, fontSize = 29.sp)
                }
            }

            Spacer(Modifier.height(28.dp))

            if (player != null) {
                Column {
                    Text(
                        "معاينة الفيديو",
                        color = White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold
                    )

                    Spacer(Modifier.height(10.dp))

                    AndroidView(
                        factory = { viewContext ->
                            PlayerView(viewContext).apply {
                                this.player = player
                                useController = true
                            }
                        },
                        update = { it.player = player },
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(230.dp)
                            .background(Color.Black, RoundedCornerShape(18.dp))
                    )
                }
            } else {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(190.dp)
                        .background(
                            Brush.verticalGradient(
                                listOf(Color(0xFF252525), Color(0xFF101010))
                            ),
                            RoundedCornerShape(22.dp)
                        )
                        .border(
                            1.dp,
                            Color(0xFF393939),
                            RoundedCornerShape(22.dp)
                        ),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎬", fontSize = 52.sp)
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "استوديو تحرير الفيديو",
                            color = White,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold
                        )
                        Text(
                            "اختر فيديو لبدء العمل عليه",
                            color = Gray,
                            fontSize = 13.sp
                        )
                    }
                }
            }

            Spacer(Modifier.height(24.dp))

            Text(
                "أدوات التحرير",
                color = White,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold
            )

            Spacer(Modifier.height(14.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ToolCard("🎬", "الفيديو", "اختيار فيديو") {
                    videoPicker.launch("video/*")
                }

                ToolCard("✨", "التأثيرات", "الفلاتر والحركات") {
                    message = "قسم التأثيرات قيد التطوير"
                }
            }

            Spacer(Modifier.height(12.dp))

            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                ToolCard("🎨", "الألوان", "تعديل الألوان") {
                    message = "قسم الألوان قيد التطوير"
                }

                ToolCard("🎵", "الصوت", "الموسيقى") {
                    message = "قسم الصوت قيد التطوير"
                }
            }

            Spacer(Modifier.height(22.dp))

            Button(
                onClick = { videoPicker.launch("video/*") },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Accent
                ),
                shape = RoundedCornerShape(15.dp)
            ) {
                Text(
                    "＋  ابدأ مشروعاً جديداً",
                    color = White,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Spacer(Modifier.height(18.dp))

            Text(
                message,
                color = Gray,
                fontSize = 13.sp
            )

            Spacer(Modifier.height(30.dp))
        }
    }
}

@Composable
fun RowScope.ToolCard(
    icon: String,
    title: String,
    subtitle: String,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .weight(1f)
            .height(135.dp)
            .background(CardColor, RoundedCornerShape(18.dp))
            .border(1.dp, Color(0xFF333333), RoundedCornerShape(18.dp))
            .clickable { onClick() }
            .padding(14.dp),
        verticalArrangement = Arrangement.Center
    ) {
        Text(icon, fontSize = 29.sp)
        Spacer(Modifier.height(9.dp))
        Text(
            title,
            color = White,
            fontSize = 16.sp,
            fontWeight = FontWeight.Bold
        )
        Spacer(Modifier.height(4.dp))
        Text(
            subtitle,
            color = Gray,
            fontSize = 11.sp
        )
    }
}
