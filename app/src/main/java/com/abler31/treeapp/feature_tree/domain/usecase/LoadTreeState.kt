package com.abler31.treeapp.feature_tree.domain.usecase

import com.abler31.treeapp.feature_tree.domain.model.Node
import com.abler31.treeapp.feature_tree.domain.repository.TreeRepository
import kotlinx.coroutines.flow.Flow

class LoadTreeState(
    private val repository: TreeRepository
) {

    operator fun invoke(): Flow<Node?> {
        return repository.loadTreeState()
    }

}