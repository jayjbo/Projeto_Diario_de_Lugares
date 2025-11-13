package com.example.myapplication.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "places_table")
data class Place(
    @PrimaryKey val id: String = "",
    val nome: String = "",
    val descricao: String = "",
    val endereco: String = "",
    val dataCriacao: Long = System.currentTimeMillis()
)