package com.max.foodies.data.repositories

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface RepositoriesModule {

    @Binds
    fun bindCartRepository(repositoryImpl: CartRepositoryImpl): CartRepository

    @Binds
    fun bindCategoryRepository(repositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    fun bindProductsRepository(repositoryImpl: ProductsRepositoryImpl): ProductsRepository
}