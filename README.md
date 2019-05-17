<p align="center">
  <img src=".assets/feature_graphic.png" alt="Kontentful: Contentful  Kotlin SDK"><br/>
  <img src="https://circleci.com/gh/mariobodemann/kontentful.svg?style=svg">
</p>


🚧 Kontentful - Contentful Kotlin Delivery SDK Beta 🚧
=============================================================

> Kotlin SDK for [Content Delivery API](https://www.contentful.com/developers/docs/references/content-delivery-api/) and [Content Preview API](https://www.contentful.com/developers/docs/references/content-preview-api/). It helps in easily accessing the content stored in Contentful using Kotlin applications.


> 🚧 This SDK is in Beta version and not officially supported by Contentful. 🚧


Usage
-----

```kotlin
data class Cat(val name: String, val bestFriend: Cat? = null) : Resource()

val contentful = Contentful( spaceId = SPACE_ID, token = DELIVERY_TOKEN )

val cats = contentful.fetchOne<Cat>("happycat").getOrThrow().items
```

For more usage information, please take a look at the [end to end tests](src/test/kotlin/com/contentful/kotlin).


> !THERE IS NO WARRANTY! USE SOFTWARE AT YOUR OWN RISK!
