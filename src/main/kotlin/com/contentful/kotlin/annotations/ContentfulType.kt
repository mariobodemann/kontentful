package com.contentful.kotlin.annotations

import kotlin.reflect.KClass


@Retention(AnnotationRetention.RUNTIME)
annotation class ContentfulType(
    val contentTypeId: String = "",
    val connectedModelsClasses: Array<KClass<*>> = []
)