package com.abler31.treeapp.feature_tree.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.security.MessageDigest

@Serializable
@Parcelize
data class Node(
    @SerializedName("name") var name: String = "",
    @SerializedName("parent") var parent: Node? = null,
    @SerializedName("children") var children: MutableList<Node> = mutableListOf(),
) : Parcelable {
    companion object {
        private var idCounter: Int = 0

        // Node hash name generation
        private fun generateHash(input: String): String {
            val md = MessageDigest.getInstance("SHA-256")
            val hashBytes = md.digest(input.toByteArray())
            return hashBytes.joinToString("") { "%02x".format(it) }.substring(0, 20)
        }
    }

    init {
        if (name.isEmpty()) {
            ++idCounter
            this.name = generateHash("Node $idCounter")
        } else {
            val originalName = name
            this.name = generateHash(originalName)
        }
    }
}

