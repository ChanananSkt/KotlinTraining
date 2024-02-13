package com.codemobiles.android.cmauthenmvvm.model

data class ChartLineResult (
    val data: List<Datum>,
    val type: Double,
    val title: String
)

data class Datum (
    val date: String,
    val prices: Long
)
