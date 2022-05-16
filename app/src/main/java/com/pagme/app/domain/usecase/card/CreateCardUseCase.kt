package com.pagme.app.domain.usecase.card

import android.net.Uri
import com.pagme.app.domain.model.Card

interface CreateCardUseCase {
    suspend operator fun invoke(cardName: String,closingDate: String,dueDate: String):Card

}