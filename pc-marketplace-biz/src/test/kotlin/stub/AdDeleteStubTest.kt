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


@OptIn(ExperimentalCoroutinesApi::class)
class AdDeleteStubTest {

    private val processor = PcMkplAdProcessor()
    val id = PcMkplAdId("666")

    @Test
    fun delete() = runTest {

        val ctx = PcMkplContext(
            command = PcMkplCommand.DELETE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.SUCCESS,
            adRequest = PcMkplAd(
                id = id,
            ),
        )
        processor.exec(ctx)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.id, ctx.adResponse.id)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.title, ctx.adResponse.title)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.description, ctx.adResponse.description)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.adType, ctx.adResponse.adType)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.visibility, ctx.adResponse.visibility)
    }

    @Test
    fun badId() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.DELETE,
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
    fun databaseError() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.DELETE,
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

}
