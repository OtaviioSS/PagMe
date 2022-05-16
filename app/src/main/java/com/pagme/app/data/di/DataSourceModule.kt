package com.pagme.app.data.di

import com.pagme.app.data.card.CardDataSource
import com.pagme.app.data.card.FirebaseCardDataSource
import com.pagme.app.data.debt.DebtDataSource
import com.pagme.app.data.debt.FirebaseDebtDataSource
import com.pagme.app.data.user.FirebaseUserDataSource
import com.pagme.app.data.user.UserDataSource
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
interface DataSourceModule {
    @Singleton
    @Binds
    fun bindCardDataSource(dataSource: FirebaseCardDataSource): CardDataSource

    @Singleton
    @Binds
    fun bindDebtDataSource(dataSource: FirebaseDebtDataSource): DebtDataSource

    @Singleton
    @Binds
    fun bindUserDataSource(dataSource: FirebaseUserDataSource): UserDataSource
}