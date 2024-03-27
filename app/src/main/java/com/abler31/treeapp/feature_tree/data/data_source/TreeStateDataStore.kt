package com.abler31.treeapp.feature_tree.data.data_source

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.abler31.treeapp.feature_tree.data.utils.NodeData
import com.abler31.treeapp.feature_tree.data.utils.toNode
import com.abler31.treeapp.feature_tree.data.utils.toNodeData
import com.abler31.treeapp.feature_tree.domain.model.Node
import com.google.gson.Gson
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class TreeStateDataStore(private val context: Context) :
    com.abler31.treeapp.feature_tree.data.data_source.DataStore {
    companion object {
        private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "tree_state")
        val TREE_ROOT_KEY = stringPreferencesKey("tree_root")
        val DEFAULT_TREE_ROOT = Node("Root", null)
    }

    override suspend fun saveTreeState(treeRoot: Node) {
        Log.d("test", "до edit")
        context.dataStore.edit { preferences ->
            Log.d("test", "после edit")
            val nodeData = treeRoot.toNodeData()
            preferences[TREE_ROOT_KEY] = Gson().toJson(nodeData)
        }
    }

    override fun loadTreeState(): Flow<Node?> {
        return context.dataStore.data.map { preferences ->
            val defaultNode = DEFAULT_TREE_ROOT.toNodeData()
            val jsonString = preferences[TREE_ROOT_KEY] ?: Gson().toJson(defaultNode)
            val nodeData = Gson().fromJson(jsonString, NodeData::class.java)
            nodeData.toNode()
        }
    }
}