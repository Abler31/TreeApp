package com.abler31.treeapp.feature_tree.data

import com.abler31.treeapp.feature_tree.domain.model.Node
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializable

@Serializable
data class NodeData(
    @SerializedName("name") val name: String,
    @SerializedName("children") val children: List<NodeData>
)

fun Node.toNodeData(): NodeData {
    return NodeData(name, children.map { it.toNodeData() })
}

fun NodeData.toNode(): Node {
    val node = Node(name = name)
    node.children.addAll(children.map { it.toNode() })
    for (child in node.children) {
        child.parent = node
    }
    return node
}
