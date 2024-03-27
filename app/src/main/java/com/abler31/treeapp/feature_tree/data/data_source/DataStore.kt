package com.abler31.treeapp.feature_tree.data.data_source

import androidx.datastore.preferences.core.edit
import com.abler31.treeapp.feature_tree.domain.model.Node
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface DataStore {
    suspend fun saveTreeState(treeRoot: Node)

    fun loadTreeState(): Flow<Node?>
}