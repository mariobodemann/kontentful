package com.contentful.kotlin.models

public class Sync(
    public val items: List<Map<Any, Any>>,
    public val nextSyncUrl: String
) {
    public fun nextSyncToken() = nextSyncUrl.split("?").last().split("=").last()
}