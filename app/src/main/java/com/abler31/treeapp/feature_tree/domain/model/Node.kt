package com.abler31.treeapp.feature_tree.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Node(
    val name: String,
    val parent: Node? = null,
    val children: MutableList<Node> = mutableListOf()
) : Parcelable
