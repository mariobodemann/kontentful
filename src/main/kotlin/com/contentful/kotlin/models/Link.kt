package com.contentful.kotlin.models

public data class Link(
    public val sys: LinkSystem
)

public data class LinkSystem(
    public val type: String,
    public val linkType: String,
    public val id: String
)
