package com.pagme.app.di.component

import com.pagme.app.presentation.activities.VerifiedActivity
import com.pagme.app.di.module.AppModule
import com.pagme.app.presentation.viewmodel.CardViewModelFactory
import com.pagme.app.presentation.viewmodel.ContactViewModelFactory
import com.pagme.app.presentation.viewmodel.DebtViewModelFactory
import com.pagme.app.presentation.viewmodel.UserViewModelFactory
import com.pagme.app.presentation.activities.DetailDebtActivity
import com.pagme.app.presentation.activities.EditFormDebtActivity
import com.pagme.app.presentation.activities.FormCardActivity
import com.pagme.app.presentation.activities.FormDebtActivity
import com.pagme.app.presentation.activities.FormUserActivity
import com.pagme.app.presentation.activities.ListCardActivity
import com.pagme.app.presentation.activities.ListDebtActivity
import com.pagme.app.presentation.activities.ProfileUserActivity
import com.pagme.app.presentation.viewmodel.PaymentViewModelFactory
import dagger.Component


@Component(modules = [AppModule::class])
interface AppComponent {
    fun inject(listDebtActivity: ListDebtActivity)
    fun inject(formCardActivity: FormCardActivity)
    fun inject(formDebtActivity: FormDebtActivity)
    fun inject(detailDebtActivity: DetailDebtActivity)
    fun inject(formUserActivity: FormUserActivity)
    fun inject(profileUserActivity: ProfileUserActivity)
    fun inject(verifiedActivity: VerifiedActivity)
    fun inject(editFormDebtActivity: EditFormDebtActivity)
    fun inject(listCardActivity: ListCardActivity)


    fun provideCardViewModelFactory(): CardViewModelFactory
    fun provideDebtViewModelFactory(): DebtViewModelFactory
    fun provideContactViewModelFactory(): ContactViewModelFactory
    fun provideUserViewModelFactory(): UserViewModelFactory
    fun providePaymentViewModelFactory(): PaymentViewModelFactory

}

