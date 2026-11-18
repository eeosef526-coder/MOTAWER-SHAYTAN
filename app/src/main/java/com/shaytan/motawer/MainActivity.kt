
package com.shaytan.motawer

import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.*
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.viewinterop.AndroidView
import androidx.media3.common.MediaItem
import androidx.media3.exoplayer.ExoPlayer
import androidx.media3.ui.PlayerView

private val BG = Color(0xFF080808)
private val PANEL = Color(0xFF191919)
private val RED = Color(0xFFFF2525)
private val WHITE = Color.White
private val GRAY = Color(0xFFAAAAAA)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(colorScheme = darkColorScheme(primary = RED)) {
                MotawerEditor()
            }
        }
    }
}

@Composable
fun MotawerEditor() {
    val context = LocalContext.current
    var videoUri by remember { mutableStateOf<Uri?>(null) }
    var tab by remember { mutableStateOf("التأثيرات") }
    var effect by remember { mutableStateOf("بدون") }
    var transition by remember { mutableStateOf("بدون") }
    var intensity by remember { mutableFloatStateOf(0.5f) }
    var playing by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("اختر فيديو للبدء") }

    val picker = rememberLauncherForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri ->
        if (uri != null) {
            videoUri = uri
            status = "تم تحميل الفيديو"
        }
    }

    val player = remember(videoUri) {
        videoUri?.let {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(it))
                prepare()
            }
        }
    }

    DisposableEffect(player) {
        onDispose { player?.release() }
    }

    val effects = listOf(
        "بدون", "Glitch", "RGB", "Neon", "Shake",
        "Blur", "Flash", "Glow", "VHS", "Pixel",
        "Mirror", "Invert", "Particles", "Zoom",
        "Distortion", "Color Shift", "Light Leak",
        "Vintage", "Chromatic", "Strobe"
    )

    val transitions = listOf(
        "بدون", "Zoom In", "Zoom Out", "Spin",
        "Flash", "Glitch", "Swipe", "Shake",
        "Blur", "Fade", "Dissolve", "RGB",
        "Cube", "Mirror", "Whip", "Slide",
        "Stretch", "Light", "Warp", "Rotate"
    )

    val tools = listOf(
        "🎬" to "الفيديو",
        "✨" to "التأثيرات",
        "🔀" to "الانتقالات",
        "🎨" to "الألوان",
        "🔤" to "النص",
        "🎵" to "الصوت",
        "⚡" to "السرعة",
        "🧩" to "الطبقات"
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "MOTAWER",
                    color = WHITE,
                    fontSize = 25.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    "SHAYTAN EDITOR",
                    color = RED,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Text("♛", color = RED, fontSize = 34.sp)
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 12.dp)
                .background(Color.Black, RoundedCornerShape(18.dp)),
            contentAlignment = Alignment.Center
        ) {
            if (player != null) {
                AndroidView(
                    factory = {
                        PlayerView(it).apply {
                            this.player = player
                            useController = false
                        }
                    },
                    update = { it.player = player },
                    modifier = Modifier.fillMaxSize()
                )
            } else {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Text("🎬", fontSize = 60.sp)
                    Text(
                        "MOTAWER STUDIO",
                        color = WHITE,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "اختر فيديو لبدء التحرير",
                        color = GRAY,
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.height(15.dp))
                    Button(
                        onClick = { picker.launch("video/*") },
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RED
                        )
                    ) {
                        Text("＋ إضافة فيديو")
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text("معاينة الفيديو", color = GRAY, fontSize = 11.sp)
            Button(
                onClick = {
                    player?.let {
                        if (it.isPlaying) {
                            it.pause()
                            playing = false
                        } else {
                            it.play()
                            playing = true
                        }
                    }
                },
                colors = ButtonDefaults.buttonColors(containerColor = RED)
            ) {
                Text(if (playing) "إيقاف" else "تشغيل")
            }
            Text("00:00", color = GRAY, fontSize = 11.sp)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF111111))
                .padding(12.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("TIMELINE", color = RED, fontSize = 12.sp)
                Text("المسار الرئيسي", color = GRAY, fontSize = 11.sp)
            }
            Spacer(Modifier.height(10.dp))
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(55.dp)
                    .background(PANEL, RoundedCornerShape(10.dp))
                    .border(1.dp, Color.DarkGray, RoundedCornerShape(10.dp))
                    .clickable { picker.launch("video/*") },
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    if (videoUri == null) "＋ إضافة مقطع فيديو"
                    else "🎞 المقطع الرئيسي",
                    color = WHITE,
                    fontSize = 13.sp
                )
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF101010))
                .padding(vertical = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(tools) { tool ->
                Column(
                    modifier = Modifier
                        .width(70.dp)
                        .clickable {
                            tab = tool.second
                            status = "القسم: ${tool.second}"
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(47.dp)
                            .background(PANEL, RoundedCornerShape(12.dp))
                            .border(
                                1.dp,
                                if (tab == tool.second) RED else Color.DarkGray,
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(tool.first, fontSize = 23.sp)
                    }
                    Spacer(Modifier.height(5.dp))
                    Text(
                        tool.second,
                        color = if (tab == tool.second) RED else WHITE,
                        fontSize = 10.sp
                    )
                }
            }
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(max = 230.dp)
                .background(Color(0xFF151515))
                .verticalScroll(rememberScrollState())
                .padding(12.dp)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    tab,
                    color = WHITE,
                    fontSize = 17.sp,
                    fontWeight = FontWeight.Bold
                )
                Text("SHAYTAN FX", color = RED, fontSize = 10.sp)
            }

            Spacer(Modifier.height(12.dp))

            when (tab) {
                "التأثيرات" -> {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(effects) { item ->
                            SelectCard(item, effect == item) {
                                effect = item
                                status = "اختير التأثير: $item"
                            }
                        }
                    }
                    Spacer(Modifier.height(12.dp))
                    Text("شدة التأثير", color = WHITE, fontSize = 12.sp)
                    Slider(
                        value = intensity,
                        onValueChange = { intensity = it },
                        colors = SliderDefaults.colors(
                            thumbColor = RED,
                            activeTrackColor = RED
                        )
                    )
                    Text(
                        "${(intensity * 100).toInt()}%",
                        color = RED,
                        fontSize = 11.sp
                    )
                }

                "الانتقالات" -> {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        items(transitions) { item ->
                            SelectCard(item, transition == item) {
                                transition = item
                                status = "اختير الانتقال: $item"
                            }
                        }
                    }
                }

                else -> {
                    Text(
                        "أدوات $tab ستضاف في مراحل التطوير التالية",
                        color = GRAY,
                        fontSize = 12.sp
                    )
                }
            }
        }

        Text(
            status,
            color = GRAY,
            fontSize = 10.sp,
            modifier = Modifier.padding(10.dp)
        )
    }
}

@Composable
fun SelectCard(
    name: String,
    selected: Boolean,
    onClick: () -> Unit
) {
    Column(
        modifier = Modifier
            .width(85.dp)
            .height(75.dp)
            .background(
                if (selected) Color(0xFF451414) else PANEL,
                RoundedCornerShape(12.dp)
            )
            .border(
                1.dp,
                if (selected) RED else Color.DarkGray,
                RoundedCornerShape(12.dp)
            )
            .clickable { onClick() },
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text("✨", fontSize = 23.sp)
        Spacer(Modifier.height(5.dp))
        Text(
            name,
            color = WHITE,
            fontSize = 11.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

