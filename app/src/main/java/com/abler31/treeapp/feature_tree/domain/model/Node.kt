package com.abler31.treeapp.feature_tree.domain.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize
import java.security.MessageDigest

@Parcelize
data class Node(
    @SerializedName("name") var name: String = "",
    @SerializedName("parent") var parent: Node? = null,
    @SerializedName("children") var children: MutableList<Node> = mutableListOf(),
) : Parcelable {
    companion object {
        var id: Int = 0

        // Node hash name generation
        private fun generateHash(input: String): String {
            val md = MessageDigest.getInstance("SHA-256")
            val hashBytes = md.digest(input.toByteArray())
            return hashBytes.joinToString("") { "%02x".format(it) }.substring(0, 20)
        }
    }

    init {
        this.name = generateHash(name)
    }
}

