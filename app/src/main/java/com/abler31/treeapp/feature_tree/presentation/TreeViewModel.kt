package com.abler31.treeapp.feature_tree.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abler31.treeapp.feature_tree.domain.model.Node
import com.abler31.treeapp.feature_tree.domain.usecase.TreeUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TreeViewModel @Inject constructor(
    private val treeUseCases: TreeUseCases
) : ViewModel() {

    private val _treeRoot = MutableLiveData<Node?>()
    val treeRoot: LiveData<Node?> = _treeRoot

    init {
        viewModelScope.launch {
            treeUseCases.loadTreeState.invoke().collect { treeRootFromFlow ->
                _treeRoot.value = treeRootFromFlow
            }
        }
    }

    fun saveTreeState(treeRoot: Node) {
        viewModelScope.launch(Dispatchers.IO) {
            treeUseCases.saveTreeState.invoke(treeRoot)
        }
    }

}