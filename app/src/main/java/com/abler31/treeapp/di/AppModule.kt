package com.abler31.treeapp.di

import android.content.Context
import com.abler31.treeapp.feature_tree.data.data_source.DataStore
import com.abler31.treeapp.feature_tree.data.data_source.TreeStateDataStore
import com.abler31.treeapp.feature_tree.data.repository.TreeRepositoryImpl
import com.abler31.treeapp.feature_tree.domain.repository.TreeRepository
import com.abler31.treeapp.feature_tree.domain.usecase.LoadTreeState
import com.abler31.treeapp.feature_tree.domain.usecase.SaveTreeState
import com.abler31.treeapp.feature_tree.domain.usecase.TreeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideDataStore(@ApplicationContext context: Context): DataStore{
        return TreeStateDataStore(context)
    }
    @Provides
    @Singleton
    fun provideRepository(dataStore: DataStore): TreeRepository{
        return TreeRepositoryImpl(dataStore)
    }
    @Provides
    @Singleton
    fun provideTreeUseCases(repository: TreeRepository): TreeUseCases{
        return TreeUseCases(
            loadTreeState = LoadTreeState(repository),
            saveTreeState = SaveTreeState(repository)
        )
    }
}