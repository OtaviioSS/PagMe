package com.pagme.app.di.component

import com.pagme.app.di.module.AppModule
import com.pagme.app.presentation.viewmodel.CardViewModelFactory
import com.pagme.app.presentation.viewmodel.ContactViewModelFactory
import com.pagme.app.presentation.viewmodel.DebtViewModelFactory
import com.pagme.app.presentation.viewmodel.UserViewModelFactory
import com.pagme.app.ui.DetailDebtActivity
import com.pagme.app.ui.FormCardActivity
import com.pagme.app.ui.FormDebtActivity
import com.pagme.app.ui.FormUserActivity
import com.pagme.app.ui.ListDebtActivity
import com.pagme.app.ui.ProfileUserActivity
import dagger.Component


@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(listDebtActivity: ListDebtActivity)
    fun inject(formCardActivity: FormCardActivity)
    fun inject(formDebtActivity: FormDebtActivity)
    fun inject(detailDebtActivity: DetailDebtActivity)
    fun inject(formUserActivity: FormUserActivity)
    fun inject(profileUserActivity: ProfileUserActivity)

    fun provideCardViewModelFactory(): CardViewModelFactory
    fun provideDebtViewModelFactory(): DebtViewModelFactory
    fun provideContactViewModelFactory(): ContactViewModelFactory
    fun provideUserViewModelFactory(): UserViewModelFactory

}

