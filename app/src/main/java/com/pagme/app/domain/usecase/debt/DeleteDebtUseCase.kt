package com.pagme.app.domain.usecase.debt

import android.net.Uri
import com.pagme.app.domain.model.Debt

interface DeleteDebtUseCase {
    suspend operator fun invoke(idDebt: String):Boolean

}