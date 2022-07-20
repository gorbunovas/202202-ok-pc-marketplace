package validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionCorrect(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("123"),
            title = "abc",
            description = "abc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(PcMkplState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionTrim(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("123"),
            title = "abc",
            description = " \n\tabc \n\t",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(PcMkplState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.description)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionEmpty(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("123"),
            title = "abc",
            description = "",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PcMkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationDescriptionSymbols(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("123"),
            title = "abc",
            description = "!@#$%^&*(),.{}",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PcMkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("description", error?.field)
    assertContains(error?.message ?: "", "description")
}
