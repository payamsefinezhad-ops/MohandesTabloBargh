package ir.example.mohandestablo

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

private val Bg = Color(0xFF101713)
private val Panel = Color(0xFF1B2921)
private val Green = Color(0xFF65D99A)

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent { MaterialTheme { AppHome() } }
    }
}

@Composable
fun AppHome() {
    var query by remember { mutableStateOf("") }
    var selected by remember { mutableStateOf("همه") }
    val categories = listOf("همه", "حفاظتی", "فرمان", "اتوماسیون", "اتصال", "اندازه‌گیری")
    val samples = listOf(
        "کلید مینیاتوری (MCB)" to "حفاظتی",
        "کلید اتوماتیک (MCCB)" to "حفاظتی",
        "کنتاکتور" to "فرمان",
        "رله حرارتی" to "حفاظتی",
        "تایمر" to "فرمان",
        "PLC" to "اتوماسیون",
        "ترمینال" to "اتصال",
        "ولت‌متر" to "اندازه‌گیری"
    )
    val shown = samples.filter { (name, category) ->
        (selected == "همه" || selected == category) && name.contains(query, ignoreCase = true)
    }

    Surface(modifier = Modifier.fillMaxSize(), color = Bg) {
        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(14.dp)
        ) {
            item {
                Text("مهندس تابلو برق", color = Green, fontSize = 27.sp, fontWeight = FontWeight.Bold)
                Text("سامانه طراحی و مدیریت تابلوهای صنعتی", color = Color.LightGray, fontSize = 13.sp)
            }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Panel), shape = RoundedCornerShape(18.dp)) {
                    Column(Modifier.padding(16.dp), verticalArrangement = Arrangement.spacedBy(10.dp)) {
                        Text("پروژه جدید", color = Green, fontWeight = FontWeight.Bold)
                        Text("نوع تابلو یا درخواست خود را وارد کنید", color = Color.White)
                        OutlinedTextField(
                            value = query,
                            onValueChange = { query = it },
                            modifier = Modifier.fillMaxWidth(),
                            placeholder = { Text("مثلاً کنتاکتور یا PLC") },
                            singleLine = true
                        )
                        Text(
                            "نسخه اولیه: فعلاً جست‌وجوی نمونه قطعات فعال است؛ تولید نقشه اجرایی هنوز پیاده‌سازی نشده است.",
                            color = Color(0xFFFFD180),
                            fontSize = 12.sp
                        )
                    }
                }
            }
            item {
                Text("کتابخانه تجهیزات", color = Green, fontSize = 20.sp, fontWeight = FontWeight.Bold)
            }
            item {
                Column(verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    categories.chunked(3).forEach { row ->
                        Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                            row.forEach { category ->
                                FilterChip(
                                    selected = selected == category,
                                    onClick = { selected = category },
                                    label = { Text(category) }
                                )
                            }
                        }
                    }
                }
            }
            items(shown) { (name, category) ->
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    colors = CardDefaults.cardColors(containerColor = Panel)
                ) {
                    Row(
                        Modifier.padding(14.dp).fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(Modifier.weight(1f)) {
                            Text(name, color = Color.White, fontWeight = FontWeight.SemiBold)
                            Text("دسته: $category", color = Color.LightGray, fontSize = 12.sp)
                            Text("شماره فنی و دیتاشیت: هنوز ثبت نشده", color = Color(0xFFFFD180), fontSize = 11.sp)
                        }
                        Text("جزئیات", color = Green, fontSize = 13.sp)
                    }
                }
            }
            item {
                Card(colors = CardDefaults.cardColors(containerColor = Panel)) {
                    Column(Modifier.padding(14.dp)) {
                        Text("وضعیت توسعه", color = Green, fontWeight = FontWeight.Bold)
                        Text("• رابط فارسی و تم تیره: آماده در نمونه اولیه", color = Color.White)
                        Text("• کتابخانه نمونه: آماده", color = Color.White)
                        Text("• پایگاه داده Room و همگام‌سازی: مرحله بعد", color = Color.White)
                        Text("• موتور نقشه‌کشی و اعتبارسنجی: هنوز ساخته نشده", color = Color.White)
                    }
                }
            }
        }
    }
}
