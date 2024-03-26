package com.abler31.treeapp.feature_tree.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Button
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.modifier.ModifierLocalReadScope
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abler31.treeapp.feature_tree.domain.model.Node
import com.abler31.treeapp.ui.theme.TreeAppTheme
import java.security.MessageDigest

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val rootNode = Node("Root")
        val childNode1 = Node("Child 1", rootNode)
        /*val newNode = createNode(rootNode)
        Log.d("test", newNode.name)*/
        val childNode2 = Node("Child 2", rootNode)

        rootNode.children.add(childNode1)
        rootNode.children.add(childNode2)
        for (i in 3..7) {
            childNode1.children.add(Node("Child $i", childNode1))
        }
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

/*private fun generateNodeName(node: Node): String {
    val hashString = nodeHash(node)
    // last 20 symbols
    return hashString.substring(hashString.length - 20)
}

// Node hash function
private fun nodeHash(node: Node): String {
    val md = MessageDigest.getInstance("SHA-256")
    val hashBytes = md.digest(node.toString().toByteArray())
    return hashBytes.joinToString("") { "%02x".format(it) }
}*/

/*private fun createNode(parent: Node?): Node{
    val node = Node(
        parent = parent
    )
    val name = generateNodeName(node)
    node.name = name
    return node
}*/

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
    val rootNode = Node("Root")
    val childNode1 = Node("Child 1", rootNode)
    val childNode2 = Node("Child 2", rootNode)

    for (i in 1..5) {
        childNode1.children.add(Node("Child $i", rootNode))
    }

    rootNode.children.add(childNode1)
    rootNode.children.add(childNode2)
    TreeAppTheme {
        TreeScreen(rootNode = childNode1)
    }
}

@Composable
fun TreeContent(node: Node) {
    var currentNode by rememberSaveable {
        mutableStateOf(node)
    }

    var childrenList = remember {
        currentNode.children.toMutableStateList()
    }

    var nodeHasParent by remember {
        mutableStateOf(currentNode.parent != null)
    }

    fun updateUi(newNode: Node) {
        currentNode = newNode
        childrenList.clear()
        newNode.children.forEach {
            childrenList.add(it)
        }
        nodeHasParent = (currentNode.parent != null)
    }

    fun addNode() {
        val newNode = Node(
            name = "Node ${Node.id++}",
            parent = currentNode
        )
        //Log.d("test", name)
        //newNode.name = name
        currentNode.children.add(newNode)
        childrenList.add(newNode)
    }

    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (nodeHasParent) {
            Text(text = "Parent")
            NodeItem(
                node = currentNode.parent!!,
                onItemClick = { updateUi(currentNode.parent!!) },
                onDeleteClick = {
                    currentNode.parent = null
                    nodeHasParent = false
                }
            )
        }
        Text(text = "Children")
        Button(onClick = {
            addNode()
        }) {
            Text("Add node")
        }
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()

        ) {
            items(childrenList) { childNode ->
                NodeItem(
                    node = childNode,
                    onItemClick = {
                        updateUi(it)
                    },
                    onDeleteClick = {
                        childrenList.remove(it)
                        currentNode.children.remove(it)
                        updateUi(currentNode)
                    })
            }
        }
    }
}

@Composable
fun NodeItem(node: Node, onItemClick: (Node) -> Unit, onDeleteClick: (Node) -> Unit) {
    ElevatedCard(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .fillMaxWidth()
            .clickable { onItemClick.invoke(node) },
        elevation = CardDefaults.cardElevation(
            defaultElevation = 8.dp
        )
    ) {
        val annotatedText = buildAnnotatedString {
            withStyle(
                style = SpanStyle(
                    color = Color.Blue,
                    fontWeight = FontWeight.Bold
                )
            ) {
                append("Delete")
            }
        }
        Row(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = node.name,
                style = MaterialTheme.typography.bodySmall,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(0.85f)
            )
            ClickableText(text = annotatedText, onClick = {
                onDeleteClick.invoke(node)
            })
        }
    }
}