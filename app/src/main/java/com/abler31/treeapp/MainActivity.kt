package com.abler31.treeapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abler31.treeapp.ui.theme.TreeAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootNode = Node("Root")

        val childNode1 = Node("Child 1", rootNode)
        val childNode2 = Node("Child 2", rootNode)

        for (i in 1..15) {
            childNode1.children.add(Node("Child $i", rootNode))
        }

        rootNode.children.add(childNode1)
        rootNode.children.add(childNode2)
        setContent {
            TreeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TreeScreen(childNode1)
                }
            }
        }
    }
}

@Composable
fun TreeScreen(rootNode: Node) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        TreeContent(rootNode)
    }
}

@Preview
@Composable
fun TreeScreenPreview() {
    TreeAppTheme {
        TreeScreen(rootNode = Node("name1"))
    }
}

@Composable
fun TreeContent(node: Node) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (node.parent != null) {
            Text(text = "Parent")
            NodeItem(node = node.parent)
        }
        Text(text = "Childs")
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()

        ) {
            items(node.children) { childNode ->
                NodeItem(node = childNode)
            }
        }
    }
}

@Composable
fun NodeItem(node: Node) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth(),
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Text(
            text = node.name,
            style = MaterialTheme.typography.bodySmall,
            textAlign = TextAlign.Center,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )
    }
}

