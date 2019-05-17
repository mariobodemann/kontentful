package com.contentful.kotlin

import com.contentful.kotlin.models.*
import org.junit.Test
import kotlin.test.*

internal const val DELIVERY_TOKEN = "b4c0n73n7fu1"
internal const val SPACE_ID = "cfexampleapi"
internal const val MASTER_ENVIRONMENT_ID = "master"

internal const val BLOG_SPACE_ID = "w7sdyslol3fu"
internal const val BLOG_DELIVERY_TOKEN = "baa905fc9cbfab17b1bc0b556a7e17a3e783a2068c9fd6ccf74ba09331357182"

internal const val TEA_SPACE_ID = "qz0n5cdakyl9"
internal const val TEA_DELIVERY_TOKEN = "580d5944194846b690dd89b630a1cb98a0eef6a19b860ef71efc37ee8076ddb8"
internal const val TEA_PREVIEW_TOKEN = "e8fc39d9661c7468d0285a7ff949f7a23539dd2e686fcb7bd84dc01b392d698b"

internal const val ENVIRONMENT_SPACE_ID = "pzlh94jb0ghw"
internal const val ENVIRONMENT_DELIVERY_TOKEN = "85877952640eea1f95fd1d212983041db640108d3c57fd92b6dcb66ce05659dc"
internal const val ENVIRONMENT_ID = "human-readable"

class BasicFunctionalityTests {
    @Test
    fun canFetchRawEntries() {
        val contentful = Contentful(
            spaceId = SPACE_ID,
            token = DELIVERY_TOKEN,
            environmentId = MASTER_ENVIRONMENT_ID
        )

        val entries = contentful.fetchAll<Entry>().getOrThrow().items

        assertEquals(10, entries.size)
        assertEquals("Garfield", entries.first().fields["name"])
    }

    @Test
    fun canFetchWithFilters() {
        val contentful = Contentful(SPACE_ID, DELIVERY_TOKEN)

        val cats = contentful.fetchAll<Entry> {
            contentType("cat")
            id("garfield")
            fieldEquals("name", "Garfield")
            systemFieldEquals("id", "garfield")
            skip(0)
            orderBy("sys.id")
            limit(9)
            select("fields.name")
            custom("sys.id[in]", "garfield")
        }.getOrThrow().items

        assertEquals(1, cats.size)
        assertEquals("Garfield", cats.first().fields["name"])
    }

    @Test
    fun canSynchronize() {
        val contentful = Contentful(spaceId = SPACE_ID, token = DELIVERY_TOKEN)

        val firstSync = contentful.initialSync().getOrThrow()

        assertEquals(14, firstSync.items.size)

        assertTrue(firstSync.nextSyncUrl.contains("sync_token="))
        assertTrue(firstSync.nextSyncToken().length > 10)

        val diffOnlySync = contentful.sync(firstSync).getOrThrow()
        assertEquals(0, diffOnlySync.items.size)

        val finalSync = contentful.sync(diffOnlySync.nextSyncToken()).getOrThrow()
        assertEquals(0, finalSync.items.size)
    }

    @Test
    fun canFetchRawAssets() {
        val contentful = Contentful(spaceId = SPACE_ID, token = DELIVERY_TOKEN)

        val assets = contentful.fetchAll<Asset>().getOrThrow().items

        assertEquals(4, assets.size)
        assertEquals("Happy Cat", assets.first().fields.title)
    }

    @Test
    fun canFetchRawContentTypes() {
        val contentful = Contentful(spaceId = SPACE_ID, token = DELIVERY_TOKEN)

        val types = contentful.fetchAll<ContentType>().getOrThrow().items

        assertEquals(4, types.size)
        assertEquals(ContentTypeFieldType.Text, types.first().fields.first().type)
    }

    @Test
    fun canFetchRawLocales() {
        val contentful = Contentful(
            spaceId = SPACE_ID,
            token = DELIVERY_TOKEN,
            environmentId = MASTER_ENVIRONMENT_ID
        )

        val locales = contentful.fetchAll<Locale>().getOrThrow().items

        assertEquals(2, locales.size)
        assertEquals("en-US", locales.first().code)
    }

    @Test
    fun canFetchLinkedRawEntries() {
        val contentful = Contentful(
            spaceId = SPACE_ID,
            token = DELIVERY_TOKEN,
            environmentId = MASTER_ENVIRONMENT_ID
        )

        val catWithFriend = contentful.fetchAll<Entry> {
            id("happycat")
            include(2)
        }.getOrThrow().items

        assertEquals("Happy Cat", catWithFriend.first().fields["name"])

        val friend = catWithFriend.first().fields["bestFriend"]
        assertTrue(friend is Entry)
        assertEquals("Nyan Cat", friend.fields["name"])
    }

    @Test
    fun canFetchRawAssetWithRawEntry() {
        val contentful = Contentful(
            spaceId = SPACE_ID,
            token = DELIVERY_TOKEN,
            environmentId = MASTER_ENVIRONMENT_ID
        )

        val entries = contentful.fetchOne<Entry>("6KntaYXaHSyIw8M6eo26OK").getOrThrow().items

        assertEquals(1, entries.size)
        assertEquals("Doge", entries.first().fields["name"])

        assertTrue(entries.first().fields["image"] is Asset)
        val asset = entries.first().fields["image"] as Asset
        assertNotNull(asset)
        assertEquals(
            "//images.ctfassets.net/cfexampleapi/1x0xpXu4pSGS4OukSyWGUK/cc1239c6385428ef26f4180190532818/doge.jpg",
            asset.fields.file?.url
        )
    }

    @Test
    fun canSearch() {
        val contentful = Contentful(
            spaceId = SPACE_ID,
            token = DELIVERY_TOKEN,
            environmentId = MASTER_ENVIRONMENT_ID
        )

        data class Cat(val name: String) : Resource()

        val cats = contentful.fetchAll<Cat> {
            fieldEquals("name", "Garfield")
        }.getOrThrow().items

        assertEquals(1, cats.size)
        assertEquals("Garfield", cats.first().name)
    }

    @Test
    fun canAccessPreview() {
        val contentful = Contentful(
            spaceId = TEA_SPACE_ID,
            token = TEA_PREVIEW_TOKEN,
            environmentId = MASTER_ENVIRONMENT_ID,
            preview = true
        )

        data class Course(val title: String) : Resource()

        val courses = contentful.fetchAll<Course>().getOrThrow().items

        assertEquals(2, courses.size)
        assertEquals("Hello SDKs", courses.first().title)
        assertEquals("Hello Contentful", courses.last().title)
    }

    @Test
    fun canAccessEnvironment() {
        val contentful = Contentful(
            spaceId = ENVIRONMENT_SPACE_ID,
            token = ENVIRONMENT_DELIVERY_TOKEN,
            environmentId = ENVIRONMENT_ID
        )

        data class Rich(val name: String, val rich: Any?) : Resource()

        val richTextDocuments = contentful.fetchAll<Rich>().getOrThrow().items

        assertEquals(26, richTextDocuments.size)
        assertEquals("simple_horizontal_rule", richTextDocuments.first().name)
    }

    @Test
    fun canReturnFailure() {
        val contentful = Contentful(
            spaceId = SPACE_ID,
            token = DELIVERY_TOKEN
        )

        data class NoCat(val name: String) : Resource()

        val result = contentful.fetchOne<NoCat>("NOT_HERE_MY_FRIEND")

        assertTrue(result.failure)
        assertFalse(result.success)
        assertNull(result.getOrNull())
    }
}