package com.pagme.app.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.UUID


@Entity
data class User(

    @PrimaryKey
    var userId: String,
    var email: String,
    var userName: String? = null,
    var userPassword: String


)