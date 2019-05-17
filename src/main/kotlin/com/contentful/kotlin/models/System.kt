package com.contentful.kotlin.models

public data class System(
    public val space: Link,
    public val contentType: Link?,
    public val environment: Link?,
    public val type: String,
    public val id: String,
    public val revision: Long?,
    public val createdAt: String?,
    public val updatedAt: String?
)