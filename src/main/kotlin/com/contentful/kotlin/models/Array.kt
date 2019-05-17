package com.contentful.kotlin.models

import com.google.gson.annotations.SerializedName

public class Array<T>(
    public val sys: ArraySystem,
    public val skip: Int,
    public val total: Int,
    public val limit: Int,
    public val items: ArrayList<T>,
    public val includes: ArrayIncludes? = null
)

public data class ArraySystem(
    public val type: String
)

public data class ArrayIncludes(
    @SerializedName("Asset")
    public val assets: List<Asset>,
    @SerializedName("Entry")
    public val entries: List<Entry>
)