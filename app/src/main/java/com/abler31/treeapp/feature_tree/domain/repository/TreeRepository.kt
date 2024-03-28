package com.abler31.treeapp.feature_tree.domain.repository

import com.abler31.treeapp.feature_tree.domain.model.Node
import kotlinx.coroutines.flow.Flow

interface TreeRepository {
    suspend fun saveTreeState(node: Node)

    fun loadTreeState(): Flow<Node?>
}