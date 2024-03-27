package com.abler31.treeapp.feature_tree.domain.usecase

import com.abler31.treeapp.feature_tree.domain.model.Node
import com.abler31.treeapp.feature_tree.domain.repository.TreeRepository

class SaveTreeState(
    private val repository: TreeRepository
) {

    suspend operator fun invoke(node: Node){
        repository.saveTreeState(node = node)
    }

}