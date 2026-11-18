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
import androidx.compose.foundation.lazy.itemsIndexed
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

private val BG = Color(0xFF09090D)
private val PANEL = Color(0xFF191923)
private val RED = Color(0xFFFF315B)
private val PURPLE = Color(0xFF8B5CF6)
private val WHITE = Color.White
private val GRAY = Color(0xFF9999A8)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme(
                colorScheme = darkColorScheme(
                    primary = RED,
                    secondary = PURPLE,
                    background = BG,
                    surface = PANEL
                )
            ) {
                MotawerApp()
            }
        }
    }
}

@Composable
fun MotawerApp() {
    var page by remember { mutableStateOf("home") }
    var selectedTab by remember { mutableStateOf("الرئيسية") }
    val clips = remember { mutableStateListOf<Uri>() }
    val projects = remember { mutableStateListOf<Uri>() }

    val picker = rememberLauncherForActivityResult(
        ActivityResultContracts.OpenMultipleDocuments()
    ) { uris ->
        if (uris.isNotEmpty()) {
            uris.forEach { uri ->
                if (!clips.contains(uri)) clips.add(uri)
                if (!projects.contains(uri)) projects.add(0, uri)
            }
            page = "editor"
        }
    }

    if (page == "editor") {
        EditorScreen(
            clips = clips.toList(),
            onAddClips = { picker.launch(arrayOf("video/*")) },
            onBack = { page = "home" }
        )
    } else {
        HomeScreen(
            selectedTab = selectedTab,
            projects = projects,
            onTab = { selectedTab = it },
            onCreate = {
                clips.clear()
                picker.launch(arrayOf("video/*"))
            },
            onOpenProject = {
                clips.clear()
                clips.add(it)
                page = "editor"
            }
        )
    }
}

@Composable
fun HomeScreen(
    selectedTab: String,
    projects: List<Uri>,
    onTab: (String) -> Unit,
    onCreate: () -> Unit,
    onOpenProject: (Uri) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 18.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    "MOTAWER",
                    color = WHITE,
                    fontSize = 27.sp,
                    fontWeight = FontWeight.Black
                )
                Text(
                    "SHAYTAN STUDIO",
                    color = RED,
                    fontSize = 11.sp,
                    fontWeight = FontWeight.Bold,
                    letterSpacing = 2.sp
                )
            }
            Box(
                modifier = Modifier
                    .size(46.dp)
                    .background(PANEL, RoundedCornerShape(15.dp))
                    .border(1.dp, PURPLE, RoundedCornerShape(15.dp)),
                contentAlignment = Alignment.Center
            ) {
                Text("♛", color = RED, fontSize = 29.sp)
            }
        }

        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = 18.dp)
        ) {
            Text(
                "اصنع فيديوهاتك بطريقة مختلفة",
                color = WHITE,
                fontSize = 21.sp,
                fontWeight = FontWeight.Bold
            )
            Text(
                "حرّر، أضف المؤثرات واصنع إبداعك",
                color = GRAY,
                fontSize = 13.sp,
                modifier = Modifier.padding(top = 6.dp)
            )

            Spacer(Modifier.height(24.dp))

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(190.dp)
                    .background(
                        Color(0xFF24132F),
                        RoundedCornerShape(22.dp)
                    )
                    .border(
                        1.dp,
                        PURPLE.copy(alpha = 0.7f),
                        RoundedCornerShape(22.dp)
                    )
                    .clickable { onCreate() }
                    .padding(20.dp)
            ) {
                Column(
                    modifier = Modifier.align(Alignment.CenterStart)
                ) {
                    Text("✨", fontSize = 35.sp)
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "مشروع جديد",
                        color = WHITE,
                        fontSize = 23.sp,
                        fontWeight = FontWeight.ExtraBold
                    )
                    Text(
                        "ابدأ تحرير فيديو جديد الآن",
                        color = Color(0xFFD5C7E8),
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Row(
                        modifier = Modifier
                            .background(RED, RoundedCornerShape(10.dp))
                            .padding(horizontal = 17.dp, vertical = 9.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("＋  إنشاء فيديو", color = WHITE, fontWeight = FontWeight.Bold)
                    }
                }
                Text(
                    "▶",
                    modifier = Modifier
                        .align(Alignment.CenterEnd)
                        .padding(end = 8.dp),
                    color = Color.White.copy(alpha = 0.15f),
                    fontSize = 76.sp
                )
            }

            Spacer(Modifier.height(27.dp))

            Text(
                "أدوات الإبداع",
                color = WHITE,
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
            Spacer(Modifier.height(13.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {
                HomeTool("✨", "المؤثرات", Modifier.weight(1f)) { onCreate() }
                HomeTool("🔀", "الانتقالات", Modifier.weight(1f)) { onCreate() }
                HomeTool("🎨", "الألوان", Modifier.weight(1f)) { onCreate() }
            }

            Spacer(Modifier.height(27.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    "مشاريعك الأخيرة",
                    color = WHITE,
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${projects.size} مشروع",
                    color = GRAY,
                    fontSize = 11.sp
                )
            }

            Spacer(Modifier.height(12.dp))

            if (projects.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(125.dp)
                        .background(PANEL, RoundedCornerShape(16.dp)),
                    contentAlignment = Alignment.Center
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text("🎬", fontSize = 30.sp)
                        Text(
                            "لا توجد مشاريع بعد",
                            color = WHITE,
                            fontSize = 13.sp
                        )
                        Text(
                            "أنشئ مشروعك الأول وابدأ الإبداع",
                            color = GRAY,
                            fontSize = 11.sp
                        )
                    }
                }
            } else {
                projects.forEachIndexed { index, uri ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 9.dp)
                            .background(PANEL, RoundedCornerShape(14.dp))
                            .clickable { onOpenProject(uri) }
                            .padding(15.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text("🎞", fontSize = 27.sp)
                        Spacer(Modifier.width(13.dp))
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                "مشروع فيديو ${index + 1}",
                                color = WHITE,
                                fontWeight = FontWeight.Bold
                            )
                            Text("اضغط للمتابعة", color = GRAY, fontSize = 11.sp)
                        }
                        Text("›", color = RED, fontSize = 28.sp)
                    }
                }
            }

            Spacer(Modifier.height(20.dp))
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF111118))
                .padding(vertical = 11.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            HomeNav("⌂", "الرئيسية", selectedTab, onTab)
            HomeNav("🎬", "مشاريعي", selectedTab, onTab)
            HomeNav("✨", "اكتشف", selectedTab, onTab)
            HomeNav("👤", "حسابي", selectedTab, onTab)
        }
    }
}

@Composable
fun HomeTool(
    emoji: String,
    name: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Column(
        modifier = modifier
            .background(PANEL, RoundedCornerShape(15.dp))
            .border(1.dp, Color(0xFF292938), RoundedCornerShape(15.dp))
            .clickable { onClick() }
            .padding(vertical = 17.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(emoji, fontSize = 27.sp)
        Spacer(Modifier.height(7.dp))
        Text(name, color = WHITE, fontSize = 11.sp, fontWeight = FontWeight.Bold)
    }
}

@Composable
fun HomeNav(
    emoji: String,
    name: String,
    selected: String,
    onClick: (String) -> Unit
) {
    Column(
        modifier = Modifier
            .clickable { onClick(name) }
            .padding(horizontal = 8.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            emoji,
            fontSize = 22.sp,
            color = if (selected == name) RED else GRAY
        )
        Text(
            name,
            fontSize = 10.sp,
            color = if (selected == name) RED else GRAY
        )
    }
}

@Composable
fun EditorScreen(
    clips: List<Uri>,
    onAddClips: () -> Unit,
    onBack: () -> Unit
) {
    val context = LocalContext.current
    var tab by remember { mutableStateOf("الفيديو") }
    var selectedIndex by remember { mutableIntStateOf(0) }
    var selectedBoundaryIndex by remember { mutableIntStateOf(0) }
    var effect by remember { mutableStateOf("بدون") }
    var intensity by remember { mutableFloatStateOf(0.5f) }
    var transitionDuration by remember { mutableFloatStateOf(0.5f) }
    var playing by remember { mutableStateOf(false) }
    var status by remember { mutableStateOf("أضف مقاطع إلى الخط الزمني") }

    val transitions = listOf(
        "بدون", "Zoom In", "Zoom Out", "Spin",
        "Flash", "Glitch", "Swipe", "Shake",
        "Blur", "Fade", "Dissolve", "RGB",
        "Cube", "Mirror", "Whip", "Slide",
        "Stretch", "Light", "Warp", "Rotate"
    )

    val effects = listOf(
        "بدون", "Glitch", "RGB", "Neon", "Shake",
        "Blur", "Flash", "Glow", "VHS", "Pixel",
        "Mirror", "Invert", "Particles", "Zoom",
        "Distortion", "Color Shift", "Light Leak",
        "Vintage", "Chromatic", "Strobe"
    )

    val tools = listOf(
        "🎬" to "الفيديو",
        "🔀" to "الانتقالات",
        "✨" to "التأثيرات",
        "🎨" to "الألوان",
        "🔤" to "النص",
        "🎵" to "الصوت",
        "⚡" to "السرعة",
        "🧩" to "الطبقات"
    )

    val transitionsBetween = remember {
        mutableStateListOf<String>()
    }

    LaunchedEffect(clips.size) {
        while (transitionsBetween.size < (clips.size - 1).coerceAtLeast(0)) {
            transitionsBetween.add("بدون")
        }
        while (transitionsBetween.size > (clips.size - 1).coerceAtLeast(0)) {
            transitionsBetween.removeAt(transitionsBetween.lastIndex)
        }
        selectedIndex = selectedIndex.coerceIn(
            0, (clips.size - 1).coerceAtLeast(0)
        )
        selectedBoundaryIndex = selectedBoundaryIndex.coerceIn(
            0, (clips.size - 2).coerceAtLeast(0)
        )
    }

    val selectedUri = clips.getOrNull(selectedIndex)

    val player = remember(selectedUri) {
        selectedUri?.let {
            ExoPlayer.Builder(context).build().apply {
                setMediaItem(MediaItem.fromUri(it))
                prepare()
            }
        }
    }

    DisposableEffect(player) {
        onDispose {
            player?.release()
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(BG)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 12.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "‹ رجوع",
                color = RED,
                fontSize = 16.sp,
                modifier = Modifier.clickable { onBack() }
            )
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    "MOTAWER",
                    color = WHITE,
                    fontSize = 19.sp,
                    fontWeight = FontWeight.Black
                )
                Text("VIDEO EDITOR", color = RED, fontSize = 9.sp)
            }
            Text(
                "تصدير",
                color = WHITE,
                fontSize = 13.sp,
                modifier = Modifier.clickable {
                    status = "التصدير سيُفعّل بعد تجهيز معالجة المشروع"
                }
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
                .padding(horizontal = 12.dp)
                .background(Color.Black, RoundedCornerShape(16.dp)),
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
                    Text("🎬", fontSize = 55.sp)
                    Text(
                        "MOTAWER STUDIO",
                        color = WHITE,
                        fontSize = 19.sp,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        "أضف مقاطع الفيديو لبدء التحرير",
                        color = GRAY,
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.height(12.dp))
                    Button(
                        onClick = onAddClips,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RED
                        )
                    ) {
                        Text("＋ إضافة فيديوهات")
                    }
                }
            }
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 14.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                "المقطع ${if (clips.isEmpty()) 0 else selectedIndex + 1} / ${clips.size}",
                color = GRAY,
                fontSize = 11.sp
            )
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
                enabled = player != null,
                colors = ButtonDefaults.buttonColors(
                    containerColor = RED
                )
            ) {
                Text(if (playing) "إيقاف" else "▶ تشغيل")
            }
            Text("معاينة", color = GRAY, fontSize = 11.sp)
        }

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF111118))
                .padding(vertical = 10.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 12.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    "TIMELINE",
                    color = RED,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Bold
                )
                Text(
                    "${clips.size} مقاطع",
                    color = GRAY,
                    fontSize = 11.sp
                )
            }

            Spacer(Modifier.height(10.dp))

            LazyRow(
                contentPadding = PaddingValues(horizontal = 12.dp),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                itemsIndexed(clips) { index, clip ->
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Column(
                            modifier = Modifier
                                .width(105.dp)
                                .height(66.dp)
                                .background(
                                    if (selectedIndex == index)
                                        Color(0xFF452033) else PANEL,
                                    RoundedCornerShape(9.dp)
                                )
                                .border(
                                    1.dp,
                                    if (selectedIndex == index) RED
                                    else Color.DarkGray,
                                    RoundedCornerShape(9.dp)
                                )
                                .clickable {
                                    selectedIndex = index
                                    tab = "الفيديو"
                                    status = "تم تحديد المقطع ${index + 1}"
                                }
                                .padding(7.dp),
                            verticalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text("🎞", fontSize = 19.sp)
                            Text(
                                "مقطع ${index + 1}",
                                color = WHITE,
                                fontSize = 11.sp,
                                fontWeight = FontWeight.Bold
                            )
                        }

                        if (index < clips.lastIndex) {
                            Column(
                                modifier = Modifier
                                    .width(66.dp)
                                    .clickable {
                                        selectedBoundaryIndex = index
                                        tab = "الانتقالات"
                                        status = "انتقال بين المقطع ${index + 1} و${index + 2}"
                                    },
                                horizontalAlignment = Alignment.CenterHorizontally
                            ) {
                                Box(
                                    modifier = Modifier
                                        .size(38.dp)
                                        .background(
                                            Color(0xFF34234A),
                                            RoundedCornerShape(10.dp)
                                        )
                                        .border(
                                            1.dp,
                                            PURPLE,
                                            RoundedCornerShape(10.dp)
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text("🔀", fontSize = 18.sp)
                                }
                                Text(
                                    transitionsBetween.getOrNull(index) ?: "بدون",
                                    color = WHITE,
                                    fontSize = 9.sp,
                                    maxLines = 1
                                )
                            }
                        }
                    }
                }

                item {
                    Column(
                        modifier = Modifier
                            .width(76.dp)
                            .height(66.dp)
                            .background(PANEL, RoundedCornerShape(9.dp))
                            .border(
                                1.dp,
                                Color.DarkGray,
                                RoundedCornerShape(9.dp)
                            )
                            .clickable { onAddClips() },
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text("＋", color = RED, fontSize = 25.sp)
                        Text("إضافة", color = WHITE, fontSize = 10.sp)
                    }
                }
            }
        }

        LazyRow(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color(0xFF101018))
                .padding(vertical = 10.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            contentPadding = PaddingValues(horizontal = 10.dp)
        ) {
            items(tools) { tool ->
                Column(
                    modifier = Modifier
                        .width(68.dp)
                        .clickable {
                            tab = tool.second
                            status = "القسم: ${tool.second}"
                        },
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Box(
                        modifier = Modifier
                            .size(45.dp)
                            .background(PANEL, RoundedCornerShape(12.dp))
                            .border(
                                1.dp,
                                if (tab == tool.second) RED else Color.DarkGray,
                                RoundedCornerShape(12.dp)
                            ),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(tool.first, fontSize = 22.sp)
                    }
                    Spacer(Modifier.height(4.dp))
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
                .heightIn(max = 205.dp)
                .background(Color(0xFF15151D))
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
                Text("SHAYTAN STUDIO", color = RED, fontSize = 10.sp)
            }

            Spacer(Modifier.height(12.dp))

            when (tab) {
                "الفيديو" -> {
                    Text(
                        "اختر مقطعاً من الخط الزمني أو أضف مقاطع جديدة",
                        color = GRAY,
                        fontSize = 12.sp
                    )
                    Spacer(Modifier.height(8.dp))
                    Button(
                        onClick = onAddClips,
                        colors = ButtonDefaults.buttonColors(
                            containerColor = RED
                        )
                    ) {
                        Text("＋ إضافة مقاطع")
                    }
                }

                "الانتقالات" -> {
                    if (clips.size < 2) {
                        Text(
                            "أضف مقطعين على الأقل لاختيار انتقال بينهما",
                            color = GRAY,
                            fontSize = 12.sp
                        )
                    } else {
                        Text(
                            "الانتقال بين المقطع ${selectedBoundaryIndex + 1} و${selectedBoundaryIndex + 2}",
                            color = GRAY,
                            fontSize = 11.sp
                        )
                        Spacer(Modifier.height(8.dp))
                        LazyRow(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            items(transitions) { item ->
                                SelectCard(
                                    item,
                                    transitionsBetween.getOrNull(selectedBoundaryIndex) == item
                                ) {
                                    if (selectedBoundaryIndex in transitionsBetween.indices) {
                                        transitionsBetween[selectedBoundaryIndex] = item
                                        status = "تم اختيار $item للانتقال"
                                    }
                                }
                            }
                        }
                        Spacer(Modifier.height(10.dp))
                        Text(
                            "مدة الانتقال: ${(transitionDuration * 1000).toInt()} ms",
                            color = WHITE,
                            fontSize = 12.sp
                        )
                        Slider(
                            value = transitionDuration,
                            onValueChange = { transitionDuration = it },
                            valueRange = 0.1f..2f,
                            colors = SliderDefaults.colors(
                                thumbColor = RED,
                                activeTrackColor = RED
                            )
                        )
                    }
                }

                "التأثيرات" -> {
                    LazyRow(
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(effects) { item ->
                            SelectCard(item, effect == item) {
                                effect = item
                                status = "تم اختيار التأثير: $item"
                            }
                        }
                    }
                    Spacer(Modifier.height(8.dp))
                    Text(
                        "شدة التأثير: ${(intensity * 100).toInt()}%",
                        color = WHITE,
                        fontSize = 12.sp
                    )
                    Slider(
                        value = intensity,
                        onValueChange = { intensity = it },
                        colors = SliderDefaults.colors(
                            thumbColor = RED,
                            activeTrackColor = RED
                        )
                    )
                }

                else -> {
                    Text(
                        "قسم $tab: سنضيف أدواته ووظائفه في مراحل التطوير التالية",
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
            modifier = Modifier.padding(8.dp)
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
                if (selected) Color(0xFF451424) else PANEL,
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
