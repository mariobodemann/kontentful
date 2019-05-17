package com.contentful.kotlin.models

public class Filter {
    private var parameter: MutableMap<String, Any> = mutableMapOf()

    public fun contentType(value: String) {
        parameter["content_type"] = value
    }

    public fun id(value: String) {
        parameter["sys.id"] = value
    }

    public fun include(value: Long) {
        parameter["include"] = value
    }

    public fun locale(value: String) {
        parameter["locale"] = value
    }

    public fun limit(value: Int) {
        parameter["limit"] = value.toString()
    }

    public fun skip(value: Int) {
        parameter["skip"] = value.toString()
    }

    public fun fieldEquals(field: String, value: Any) {
        parameter["fields.$field"] = value.toString()
    }

    public fun systemFieldEquals(field: String, value: Any) {
        parameter["sys.$field"] = value.toString()
    }

    public fun orderBy(value: String, inverted: Boolean = false) {
        parameter["order"] = "${if (inverted) "-" else ""}$value"
    }

    public fun select(value: String) {
        parameter["select"] = value
    }

    public fun custom(key: String, value: String) {
        parameter[key] = value
    }

    public fun build(): MutableMap<String, Any> = parameter
}
