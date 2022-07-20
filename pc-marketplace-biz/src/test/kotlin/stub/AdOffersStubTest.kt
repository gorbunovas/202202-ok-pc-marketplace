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
class AdOffersStubTest {

    private val processor = PcMkplAdProcessor()
    val id = PcMkplAdId("777")

    @Test
    fun offers() = runTest {

        val ctx = PcMkplContext(
            command = PcMkplCommand.OFFERS,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.SUCCESS,
            adRequest = PcMkplAd(
                id = id,
            ),
        )
        processor.exec(ctx)

        assertEquals(id, ctx.adResponse.id)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.title, ctx.adResponse.title)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.description, ctx.adResponse.description)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.adType, ctx.adResponse.adType)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.visibility, ctx.adResponse.visibility)

        assertTrue(ctx.adsResponse.size > 1)
        val first = ctx.adsResponse.firstOrNull() ?: fail("Empty response list")
        assertTrue(first.title.contains(ctx.adResponse.title))
        assertTrue(first.description.contains(ctx.adResponse.title))
        assertEquals(PcMkplAdStubsPc.AD_SUPPLY_PC1.adType, first.adType)
        assertEquals(PcMkplAdStubsPc.AD_SUPPLY_PC1.visibility, first.visibility)
    }
    
}
