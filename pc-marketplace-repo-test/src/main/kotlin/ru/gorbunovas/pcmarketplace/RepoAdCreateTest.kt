package ru.gorbunovas.pcmarketplace

import kotlinx.coroutines.runBlocking
import org.junit.Test
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.repo.DbAdRequest
import ru.gorbunovas.pcmarketplace.common.repo.IAdRepository
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

abstract class  RepoAdCreateTest {
    abstract val repo: IAdRepository

    @Test
    fun createSuccess() {
        val result = runBlocking { repo.createAd(DbAdRequest(createObj)) }
        val expected = createObj.copy(id = result.result?.id ?: PcMkplAdId.NONE)
        assertEquals(true, result.isSuccess)
        assertEquals(expected.title, result.result?.title)
        assertEquals(expected.description, result.result?.description)
        assertEquals(expected.adType, result.result?.adType)
        assertNotEquals(PcMkplAdId.NONE, result.result?.id)
        assertEquals(emptyList(), result.errors)
    }

    companion object: BaseInitAds("search") {

        private val createObj = PcMkplAd(
            title = "create object",
            description = "create object description",
            ownerId = PcMkplUserId("owner-123"),
            visibility = PcMkplVisibility.VISIBLE_TO_GROUP,
            adType = PcMkplDealSide.SUPPLY,
        )
        override val initObjects: List<PcMkplAd> = emptyList()
    }
}
