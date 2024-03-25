package com.abler31.treeapp.feature_tree.domain.repository

import com.abler31.treeapp.feature_tree.domain.model.Node

interface TreeRepository {
    suspend fun insertNode(node: Node)

    suspend fun deleteNode(node: Node)
}