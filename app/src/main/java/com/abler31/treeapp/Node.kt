package com.abler31.treeapp

data class Node(
    val name: String,
    val parent: Node? = null,
    val children: MutableList<Node> = mutableListOf()
)
