package com.pagme.app.domain.usecase.debt

import com.pagme.app.domain.model.Debt

interface GetDebtUseCase {
    suspend operator fun invoke():List<Debt>

}