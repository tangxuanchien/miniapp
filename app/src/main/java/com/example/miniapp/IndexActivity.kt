package com.example.todolist

import android.content.Context
import android.os.Bundle
import android.widget.Space
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Divider
import androidx.compose.material3.Label
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class IndexActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val username = intent.getStringExtra("username") ?: ""
        setContent {
            Index(username)
        }
    }
}

@Composable
fun Index(
    username: String
) {
    var name by remember {
        mutableStateOf("")
    }
    val names = remember {
        mutableStateListOf<String>()
    }
    var context = LocalContext.current
    Column(
        Modifier
            .fillMaxSize()
            .padding(12.dp)
    ) {
        Text(
            "Hi $username, To do list",
            fontWeight = FontWeight.Bold,
            fontSize = 24.sp,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Spacer(Modifier.padding(10.dp))
        Row {
            OutlinedTextField(
                value = name,
                onValueChange = { name = it }
            )
            Spacer(modifier = Modifier.width(16.dp))
            Button(
                onClick = {
                    if (name.isNotBlank()) {
                        names += name
                        name = ""
                    }
                }
            ) {
                Text("Add")
            }
        }
        NameList(names, context)
    }

}

@Composable
fun NameList(
    names:  SnapshotStateList<String>,
    context: Context,
    modifier: Modifier = Modifier
) {
    LazyColumn {
        items(names) { currentName ->
            Row {
                Text(
                    text = currentName,
                    Modifier.padding(10.dp)
                )
                Spacer(modifier = Modifier.weight(1f))
                Button(
                    onClick = {
                        Toast.makeText(context, "Remove $currentName", Toast.LENGTH_LONG).show()
                        names.remove(currentName)
                    }
                ) {
                    Text("Remove")
                }
            }
            Divider()
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewIndex() {
    Index(username = "Chien")
}