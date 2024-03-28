package com.abler31.treeapp.feature_tree.presentation

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.abler31.treeapp.feature_tree.domain.model.Node
import com.abler31.treeapp.ui.theme.TreeAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    val viewModel by viewModels<TreeViewModel>()
    var treeRoot by mutableStateOf<Node?>(null)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            LaunchedEffect(Unit) {
                viewModel.treeRoot.observe(this@MainActivity) { treeRootFromLiveData ->
                    // Update node
                    treeRoot = treeRootFromLiveData
                }
            }
            TreeAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    treeRoot?.children?.firstOrNull()?.let { Log.d("test", it.name) }
                    treeRoot?.let {
                        TreeScreen(
                            rootNode = it,
                            onSaveTreeState = viewModel::saveTreeState
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun TreeScreen(rootNode: Node, onSaveTreeState: (Node) -> Unit) {
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        TreeContent(rootNode, onSaveTreeState)
    }
}

@Preview
@Composable
fun TreeScreenPreview() {
    val rootNode = Node("cbsg", null)
    val childNode1 = Node("dgdgg", rootNode)
    val childNode2 = Node("gjfgdgfd", rootNode)

    for (i in 1..5) {
        val node = Node("", rootNode)
        childNode1.children.add(node)
    }

    rootNode.children.add(childNode1)
    rootNode.children.add(childNode2)
    TreeAppTheme {
        TreeScreen(rootNode = childNode1) {}
    }
}

@Composable
fun TreeContent(node: Node, onSaveTreeState: (Node) -> Unit) {
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
            parent = currentNode
        )
        currentNode.children.add(newNode)
        childrenList.add(newNode)
        onSaveTreeState(currentNode)
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
                    onSaveTreeState(currentNode)
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
                        onSaveTreeState(currentNode)
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