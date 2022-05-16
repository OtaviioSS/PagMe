package com.pagme.app.domain.usecase.di

import com.pagme.app.domain.usecase.card.*
import com.pagme.app.domain.usecase.debt.*
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@Module
@InstallIn(ViewModelComponent::class)
interface DomainModule {
    @Binds
    fun bindCreateCardUseCase(useCase: CreateCardUseCaseImplementation): CreateCardUseCase

    @Binds
    fun bindGetCardsUseCase(useCase: GetCardsUseCaseImplementation): GetCardsUseCase

    @Binds
    fun bindUpdateCardUseCase(useCase: UpdateCardUseCaseImplementation): UpdateCardUseCase

}