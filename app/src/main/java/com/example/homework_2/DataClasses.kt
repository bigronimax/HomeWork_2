package com.example.homework_2

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class DataResult(
    @SerializedName("data") val res: List<DataObject>
)

data class DataObject(
    @SerializedName("images") val images: DataImage
)

data class DataImage(
    @SerializedName("original") val ogImage: ogImage
)

data class ogImage(
    val url: String
)