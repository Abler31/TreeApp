package com.abler31.treeapp.feature_tree.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.security.MessageDigest

@Parcelize
data class Node(
    @SerializedName("name") var name: String = "",
    @SerializedName("parent") var parent: Node? = null,
    @SerializedName("children") var children: MutableList<Node> = mutableListOf(),
    private var idCounter: Int = 0 // статический idCounter
) : Parcelable {

    companion object {
        private var staticIdCounter: Int = 0
        // Node hash name generation
        fun generateHash(input: String): String {
            val md = MessageDigest.getInstance("SHA-256")
            val hashBytes = md.digest(input.toByteArray())
            return hashBytes.joinToString("") { "%02x".format(it) }.substring(0, 20)
        }
    }

    init {
        ++staticIdCounter // Увеличиваем статический счетчик только при создании нового экземпляра Node
        if (name.isEmpty()) {
            this.name = generateHash("Node $staticIdCounter")
        } else {
            val originalName = name
            this.name = originalName
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other !is Node) return false

        // Сравниваем только свойства name и parent, не включая children
        return name == other.name && parent == other.parent
    }

    override fun hashCode(): Int {
        var result = name.hashCode()
        result = 31 * result + (parent?.hashCode() ?: 0)
        return result
    }
}

