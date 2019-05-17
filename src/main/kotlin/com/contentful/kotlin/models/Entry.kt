package com.contentful.kotlin.models

public class Entry(
    public val sys: System? = null,
    public val fields: MutableMap<String, Any?>
) : Resource()