package com.contentful.kotlin.models

public class Asset(
    public val sys: System? = null,
    public val fields: AssetFields
) : Resource()

public data class AssetFields(
    public val title: String?,
    public val description: String?,
    public val file: AssetFileField?,
    public val fileName: String?,
    public val contentType: String?
)

public data class AssetFileField(
    public val url: String?,
    public val details: AssetFileDetails
)

public data class AssetFileDetails(
    public val size: Long,
    public val image: AssetImageDetail?
)

public data class AssetImageDetail(
    public val width: Int?,
    public val height: Int?
)