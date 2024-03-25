package com.abler31.treeapp.feature_tree.domain.usecase

import com.abler31.treeapp.feature_tree.domain.model.Node
import com.abler31.treeapp.feature_tree.domain.repository.TreeRepository

class InsertNode(
    private val repository: TreeRepository
) {

    suspend operator fun invoke(node: Node){
        repository.insertNode(node = node)
    }

}