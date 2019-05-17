package com.contentful.kotlin.models

public class Locale(
    public val sys: System? = null,
    public val code: String,
    public val name: String,
    public val default: Boolean,
    public val fallbackCode: String
) : Resource()
