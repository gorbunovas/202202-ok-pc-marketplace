package stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs
import ru.gorbunovas.pcmarketplace.stubs.PcMkplAdStubsPc
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import kotlin.test.fail

@OptIn(ExperimentalCoroutinesApi::class)
class AdSearchStubTest {

    private val processor = PcMkplAdProcessor()
    val filter = PcMkplAdFilter(searchString = "bolt")

    @Test
    fun read() = runTest {

        val ctx = PcMkplContext(
            command = PcMkplCommand.SEARCH,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.SUCCESS,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertTrue(ctx.adsResponse.size > 1)
        val first = ctx.adsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(filter.searchString))
        assertTrue(first.description.contains(filter.searchString))
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.adType, first.adType)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.visibility, first.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.SEARCH,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.BAD_ID,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(PcMkplAd(), ctx.adResponse)
        assertEquals("id", ctx.errors.firstOrNull()?.field)
        assertEquals("validation", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun databaseError() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.SEARCH,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.DB_ERROR,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(PcMkplAd(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.SEARCH,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.BAD_TITLE,
            adFilterRequest = filter,
        )
        processor.exec(ctx)
        assertEquals(PcMkplAd(), ctx.adResponse)
        assertEquals("stub", ctx.errors.firstOrNull()?.field)
    }
}
