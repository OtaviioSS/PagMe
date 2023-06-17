package com.pagme.app.di.module

import androidx.lifecycle.ViewModelProvider
import com.pagme.app.data.datasource.CardDataSource
import com.pagme.app.data.datasource.ContactDataSource
import com.pagme.app.data.datasource.DebtDataSource
import com.pagme.app.data.datasource.UserDataSource
import com.pagme.app.data.repository.CardRepositoryImplementation
import com.pagme.app.data.repository.CardRepositoryInterface
import com.pagme.app.data.repository.ContactRepositoryImplementation
import com.pagme.app.data.repository.ContactRepositoryInterface
import com.pagme.app.data.repository.DebtRepositoryImplementation
import com.pagme.app.data.repository.DebtRepositoryInterface
import com.pagme.app.data.repository.UserRepositoryImplementation
import com.pagme.app.data.repository.UserRepositoryInterface
import com.pagme.app.domain.usecase.CardUseCase
import com.pagme.app.domain.usecase.ContactUseCase
import com.pagme.app.domain.usecase.DebtUseCase
import com.pagme.app.domain.usecase.UserUseCase
import com.pagme.app.presentation.viewmodel.CardViewModelFactory
import com.pagme.app.presentation.viewmodel.ContactViewModelFactory
import com.pagme.app.presentation.viewmodel.DebtViewModelFactory
import com.pagme.app.presentation.viewmodel.UserViewModelFactory
import dagger.Module
import dagger.Provides


@Module
class AppModule {

    @Provides
    fun provideCardDataSource(): CardDataSource {
        return CardDataSource()
    }


    @Provides
    fun provideCardRepository(dataSource: CardDataSource): CardRepositoryInterface {
        return CardRepositoryImplementation(dataSource)
    }

    @Provides
    fun provideCardUseCase(repository: CardRepositoryInterface): CardUseCase {
        return CardUseCase(repository)
    }



    @Provides
    fun provideDebtDataSource(): DebtDataSource {
        return DebtDataSource()
    }


    @Provides
    fun provideDebtRepository(dataSource: DebtDataSource): DebtRepositoryInterface {
        return DebtRepositoryImplementation(dataSource)
    }

    @Provides
    fun provideDebtUseCase(repository: DebtRepositoryInterface): DebtUseCase {
        return DebtUseCase(repository)
    }

    @Provides
    fun provideDebtViewModelFactory(useCase: DebtUseCase): ViewModelProvider.Factory {
        return DebtViewModelFactory(useCase)
    }
    @Provides
    fun provideCardViewModelFactory(useCase: CardUseCase): ViewModelProvider.Factory {
        return CardViewModelFactory(useCase)
    }




    @Provides
    fun provideContactDataSource(): ContactDataSource {
        return ContactDataSource()
    }


    @Provides
    fun provideContactRepository(dataSource: ContactDataSource): ContactRepositoryInterface {
        return ContactRepositoryImplementation(dataSource)
    }

    @Provides
    fun provideContactUseCase(repository: ContactRepositoryInterface): ContactUseCase {
        return ContactUseCase(repository)
    }

    @Provides
    fun provideContactViewModelFactory(useCase: ContactUseCase): ViewModelProvider.Factory {
        return ContactViewModelFactory(useCase)
    }

    @Provides
    fun provideUserDataSource(): UserDataSource {
        return UserDataSource()
    }


    @Provides
    fun provideUserRepository(dataSource: UserDataSource): UserRepositoryInterface {
        return UserRepositoryImplementation(dataSource)
    }

    @Provides
    fun provideUserUseCase(repository: UserRepositoryInterface): UserUseCase {
        return UserUseCase(repository)
    }
    @Provides
    fun provideUserViewModelFactory(useCase: UserUseCase): ViewModelProvider.Factory {
        return UserViewModelFactory(useCase)
    }

}