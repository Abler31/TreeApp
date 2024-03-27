package com.abler31.treeapp.feature_tree.data

import androidx.room.TypeConverter
import com.abler31.treeapp.feature_tree.domain.model.Node
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    val gson = Gson()

    @TypeConverter
    fun nodeToString(node: Node): String {
        return gson.toJson(node)
    }

    @TypeConverter
    fun stringToNode(nodeString: String): Node {
        val objectType = object : TypeToken<Node>() {}.type
        return gson.fromJson(nodeString, objectType)
    }

    @TypeConverter
    fun nodesToString(nodes: MutableList<Node>): String {
        return gson.toJson(nodes)
    }

    @TypeConverter
    fun stringToNodes(nodesString: String): MutableList<Node> {
        val objectType = object : TypeToken<Node>() {}.type
        return gson.fromJson(nodesString, objectType)
    }
}