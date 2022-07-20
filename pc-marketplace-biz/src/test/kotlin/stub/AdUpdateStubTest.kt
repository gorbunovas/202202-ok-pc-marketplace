package stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class AdUpdateStubTest {

    private val processor = PcMkplAdProcessor()
    val id = PcMkplAdId("777")
    val title = "title 666"
    val description = "desc 666"
    val dealSide = PcMkplDealSide.DEMAND
    val visibility = PcMkplVisibility.VISIBLE_PUBLIC

    @Test
    fun create() = runTest {

        val ctx = PcMkplContext(
            command = PcMkplCommand.UPDATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.SUCCESS,
            adRequest = PcMkplAd(
                id = id,
                title = title,
                description = description,
                adType = dealSide,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(id, ctx.adResponse.id)
        assertEquals(title, ctx.adResponse.title)
        assertEquals(description, ctx.adResponse.description)
        assertEquals(dealSide, ctx.adResponse.adType)
        assertEquals(visibility, ctx.adResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.UPDATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.BAD_ID,
            adRequest = PcMkplAd(),
        )
        processor.exec(ctx)
        assertEquals(PcMkplAd(), ctx.adResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.UPDATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.BAD_TITLE,
            adRequest = PcMkplAd(
                id = id,
                title = "",
                description = description,
                adType = dealSide,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(PcMkplAd(), ctx.adResponse)
        assertEquals("title", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
    @Test
    fun badDescription() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.UPDATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.BAD_DESCRIPTION,
            adRequest = PcMkplAd(
                id = id,
                title = title,
                description = "",
                adType = dealSide,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(PcMkplAd(), ctx.adResponse)
        assertEquals("description", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.UPDATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.DB_ERROR,
            adRequest = PcMkplAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(PcMkplAd(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.UPDATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.BAD_SEARCH_STRING,
            adRequest = PcMkplAd(
                id = id,
                title = title,
                description = description,
                adType = dealSide,
                visibility = visibility,
            ),
        )
        processor.exec(ctx)
        assertEquals(PcMkplAd(), ctx.adResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }
}
