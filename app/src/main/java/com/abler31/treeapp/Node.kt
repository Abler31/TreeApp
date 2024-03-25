package com.abler31.treeapp

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Node(
    val name: String,
    val parent: Node? = null,
    val children: MutableList<Node> = mutableListOf()
) : Parcelable
