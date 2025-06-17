package com.example.miniapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.todolist.Index

class CalculatorActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            CalculatorScreen()
        }
    }
}

@Composable
fun CalculatorScreen() {
    val buttonModifier = Modifier
        .padding(4.dp)

    val screen = remember {
        mutableStateOf("")
    }

    val math1 = remember {
        mutableIntStateOf(0)
    }

    val math2 = remember {
        mutableIntStateOf(0)
    }

    val result = remember {
        mutableIntStateOf(0)
    }

    val operator = remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF202020))
            .padding(16.dp)
    ) {
        Text(
            text = screen.value,
            color = Color.White,
            fontSize = 48.sp,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            textAlign = TextAlign.Start
        )

        Spacer(modifier = Modifier.height(8.dp))

        val buttons = listOf(
            listOf("7", "8", "9", "×"),
            listOf("4", "5", "6", "-"),
            listOf("1", "2", "3", "+"),
            listOf("0", ".", "=", "÷")
        )

        for (row in buttons) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (btn in row) {
                    val isZero = btn == "0" && row.size == 3
                    Button(
                        onClick = {
                            when (btn) {
                                "+" -> operator.value = "+"
                                "-" -> operator.value = "-"
                                "×" -> operator.value = "*"
                                "÷" -> operator.value = "/"
                                "=" ->
                                {
                                    when (operator.value) {
                                        "+" -> result.intValue = math1.intValue + math2.intValue
                                        "-" -> result.intValue = math1.intValue - math2.intValue
                                        "*"-> result.intValue = math1.intValue * math2.intValue
                                        "/" -> result.intValue = math1.intValue / math2.intValue
                                    }
                                    screen.value = ""
                                    operator.value = ""
                                    math1.intValue = 0
                                    math2.intValue = 0
                                }
                                else -> {
                                    if (math1.intValue == 0) {
                                        math1.intValue += btn.toInt()
                                    } else if (math2.intValue == 0) {
                                        math2.intValue += btn.toInt()
                                    }
                                }
                            }
                            if(btn != "=") {
                                screen.value += " $btn"
                            }
                        },
                        colors = ButtonDefaults.buttonColors(containerColor = getButtonColor(btn)),
                        shape = CircleShape,
                        modifier = if (isZero)
                            Modifier
                                .weight(2f)
                                .height(64.dp)
                                .padding(4.dp)
                        else buttonModifier
                    ) {
                        Text(
                            text = btn,
                            fontSize = 24.sp,
                            color = Color.White
                        )
                    }
                }
            }
        }
//        if (result.intValue != 0) {
            Text(
                "Result: ${result.intValue}",
                color = Color.White,
                fontSize = 30.sp,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(10.dp)
            )
//        }
        Button(
            onClick = {}
        ) {
            Text("Back")
        }
    }
}

fun getButtonColor(text: String): Color {
    return when (text) {
        "Back" -> Color.Gray
        "÷", "×", "-", "+" -> Color(0xFFFF9800)
        else -> Color(0xFF424242)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewCalculator() {
    CalculatorScreen()
}