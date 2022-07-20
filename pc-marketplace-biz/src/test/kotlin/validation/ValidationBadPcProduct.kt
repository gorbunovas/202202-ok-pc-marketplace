package validation

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
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
fun validationProductCorrect(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("ddddd"),
            title = "title",
            description = "desc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
            product = PcMkplAdProductPc(
                price = 10000,
                hdd = "ssd 1Tb",
                motherboard = "s360",
                formFactor = PcMkplAdProductPcFormfactor.FULL_TOWER,
                type = "server",
                cpu = PcMkplAdProductPcCpu(model = "i10", core = 8, clock = 10),
                ram = PcMkplAdProductPcRam(PcMkplAdProductPcRam.TypeRam.DDR6, 1333, "kingstom3000")
            ),
            comments = mutableListOf(PcMkplComment(PcMkplAdCommentId("11111111111"), "Вопрос о продукте", "Пропатчен под freeBSD?"))
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(PcMkplState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationProductRamEmpty(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("dsdsd"),
            title = "title",
            description = "desc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
            product = PcMkplAdProductPc(
                price = 10000,
                hdd = "ssd 1Tb",
                motherboard = "s360",
                formFactor = PcMkplAdProductPcFormfactor.FULL_TOWER,
                type = "server",
                cpu = PcMkplAdProductPcCpu(model = "i10", core = 8, clock = 10),
                //ram = PcMkplAdProductPcRam(PcMkplAdProductPcRam.TypeRam.DDR6, 1333, "kingstom3000")
            ),
            comments = mutableListOf(PcMkplComment(PcMkplAdCommentId("11111111111"), "Вопрос о продукте", "Пропатчен под freeBSD?"))
        )
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PcMkplState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationProductMotherboardEmpty(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("dsdsd"),
            title = "title",
            description = "desc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
            product = PcMkplAdProductPc(
                price = 10000,
                hdd = "ssd 1Tb",
                //motherboard = "s360",
                formFactor = PcMkplAdProductPcFormfactor.FULL_TOWER,
                type = "server",
                cpu = PcMkplAdProductPcCpu(model = "i10", core = 8, clock = 10),
                ram = PcMkplAdProductPcRam(PcMkplAdProductPcRam.TypeRam.DDR6, 1333, "kingstom3000")
            ),
            comments = mutableListOf(PcMkplComment(PcMkplAdCommentId("11111111111"), "Вопрос о продукте", "Пропатчен под freeBSD?"))
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PcMkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("motherboard", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationProductCpuEmpty(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("fff"),
            title = "abc",
            description = "abc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
            product = PcMkplAdProductPc(
                price = 10000,
                hdd = "ssd 1Tb",
                motherboard = "s360",
                formFactor = PcMkplAdProductPcFormfactor.FULL_TOWER,
                type = "server",
                //cpu = PcMkplAdProductPcCpu(model = "i10", core = 8, clock = 10),
                ram = PcMkplAdProductPcRam(PcMkplAdProductPcRam.TypeRam.DDR6, 1333, "kingstom3000")
            ),
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PcMkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("CPU", error?.field)
    assertContains(error?.message ?: "", "id")
}
