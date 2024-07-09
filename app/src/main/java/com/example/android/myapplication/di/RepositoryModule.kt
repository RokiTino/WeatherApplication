package com.example.android.myapplication.di

import com.example.android.myapplication.domain.Repository
import com.example.android.myapplication.domain.RepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent


@InstallIn(ViewModelComponent::class)
@Module
interface RepositoryModule {
    @Binds
    fun bindRepository(repositoryImpl: RepositoryImpl): Repository
}