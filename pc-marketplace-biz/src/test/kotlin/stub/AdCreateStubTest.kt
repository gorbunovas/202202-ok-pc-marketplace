package stub

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPc
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPcCpu
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPcFormfactor
import ru.gorbunovas.pcmarketplace.common.models.product.PcMkplAdProductPcRam
import ru.gorbunovas.pcmarketplace.common.stubs.PcMkplStubs
import ru.gorbunovas.pcmarketplace.stubs.*
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class AdCreateStubTest {

    private val processor = PcMkplAdProcessor()
    val id = PcMkplAdId("id8888")
    val title = "title 8888"
    val description = "666"
    val dealSide = PcMkplDealSide.DEMAND
    val visibility = PcMkplVisibility.VISIBLE_PUBLIC
    val product = PcMkplAdProductPc(
        price = 10000.0,
        hdd = "ssd 1Tb",
        motherboard = "s360",
        formFactor = PcMkplAdProductPcFormfactor.FULL_TOWER,
        type = "server",
        cpu = PcMkplAdProductPcCpu(model = "i10", core = 8, clock = 10),
        ram = PcMkplAdProductPcRam(PcMkplAdProductPcRam.TypeRam.DDR6, 1333, "kingstom3000")
    )

    @Test
    fun create() = runTest {

        val ctx = PcMkplContext(
            command = PcMkplCommand.CREATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.SUCCESS,
            adRequest = PcMkplAdStubsPc.AD_DEMAND_PC1,
        )
        processor.exec(ctx)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.id, ctx.adResponse.id)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.title, ctx.adResponse.title)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.description, ctx.adResponse.description)
        assertEquals(dealSide, ctx.adResponse.adType)
        assertEquals(PcMkplAdStubsPc.AD_DEMAND_PC1.visibility, ctx.adResponse.visibility)
    }

    @Test
    fun badTitle() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.CREATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.BAD_TITLE,
            adRequest = PcMkplAd(
                id = id,
                title = "",
                description = description,
                adType = dealSide,
                visibility = visibility,
                product = product
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
            command = PcMkplCommand.CREATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.BAD_DESCRIPTION,
            adRequest = PcMkplAd(
                id = id,
                title = title,
                description = "",
                adType = dealSide,
                visibility = visibility,
                product = product
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
            command = PcMkplCommand.CREATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.DB_ERROR,
            adRequest = PcMkplAd(
                id = id,
                product = product
            ),
        )
        processor.exec(ctx)
        assertEquals(PcMkplAd(), ctx.adResponse)
        assertEquals("internal", ctx.errors.firstOrNull()?.group)
    }

    @Test
    fun badNoCase() = runTest {
        val ctx = PcMkplContext(
            command = PcMkplCommand.CREATE,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.STUB,
            stubCase = PcMkplStubs.BAD_ID,
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
