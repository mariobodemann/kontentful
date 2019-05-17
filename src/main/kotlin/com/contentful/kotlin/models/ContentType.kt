package com.contentful.kotlin.models

public class ContentType(
    public val sys: System? = null,
    public val displayField: String,
    public val name: String,
    public val description: String?,
    public val fields: List<ContentTypeField>
) : Resource()

public data class ContentTypeField(
    public val id: String,
    public val name: String,
    public val type: ContentTypeFieldType,
    public val localized: Boolean,
    public val required: Boolean,
    public val disabled: Boolean,
    public val omitted: Boolean
)

public enum class ContentTypeFieldType {
    Symbol,
    Text,
    Integer,
    Number,
    Date,
    Location,
    Boolean,
    Link,
    Array,
    Object,
    RichText
}