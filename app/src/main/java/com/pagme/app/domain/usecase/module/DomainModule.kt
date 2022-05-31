package com.pagme.app.domain.usecase.module

import com.pagme.app.domain.usecase.card.*
import com.pagme.app.domain.usecase.debt.*
import com.pagme.app.domain.usecase.user.*
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
    fun bindCreateDebtUseCase(useCase: CreateDebtUseCaseImplementation): CreateDebtUseCase

    @Binds
    fun bindGetDebtsUseCase(useCase: GetDebtUseCaseImplementation): GetDebtUseCase

    @Binds
    fun bindLoginUserUseCase(useCase: LoginUseCaseImplementation): LoginUseCase


    @Binds
    fun bindCreateUserUseCase(useCase: CreateUserUseCaseImplementation):CreateUserUseCase

    @Binds
    fun bindGetUserUseCase(useCase: GetUserUseCaseImplementation):GetUserUseCase


}