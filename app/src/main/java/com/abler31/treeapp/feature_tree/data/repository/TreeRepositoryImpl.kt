package com.abler31.treeapp.feature_tree.data.repository

import com.abler31.treeapp.feature_tree.data.data_source.DataStore
import com.abler31.treeapp.feature_tree.domain.model.Node
import com.abler31.treeapp.feature_tree.domain.repository.TreeRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class TreeRepositoryImpl @Inject constructor (private val dataStore: DataStore): TreeRepository {
    override suspend fun saveTreeState(node: Node) {
        dataStore.saveTreeState(node)
    }

    override fun loadTreeState(): Flow<Node?> {
        return dataStore.loadTreeState()
    }
}