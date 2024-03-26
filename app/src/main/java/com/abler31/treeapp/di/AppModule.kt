package com.abler31.treeapp.di

import android.app.Application
import androidx.room.Room
import com.abler31.treeapp.feature_tree.data.data_source.TreeDatabase
import com.abler31.treeapp.feature_tree.data.repository.TreeRepositoryImpl
import com.abler31.treeapp.feature_tree.domain.repository.TreeRepository
import com.abler31.treeapp.feature_tree.domain.usecase.GetNode
import com.abler31.treeapp.feature_tree.domain.usecase.InsertNode
import com.abler31.treeapp.feature_tree.domain.usecase.TreeUseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideTreeDatabase(app: Application): TreeDatabase{
        return Room.databaseBuilder(
            app,
            TreeDatabase::class.java,
            TreeDatabase.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideTreeRepository(db: TreeDatabase): TreeRepository{
        return TreeRepositoryImpl(db.treeDao)
    }

    @Provides
    @Singleton
    fun provideTreeUseCases(repository: TreeRepository): TreeUseCases{
        return TreeUseCases(
            getNode = GetNode(repository),
            insertNode = InsertNode(repository)
        )
    }
}