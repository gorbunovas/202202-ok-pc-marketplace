package validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleCorrect(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
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
    assertEquals("abc", ctx.adValidated.title)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleTrim(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("123"),
            title = " \n\t abc \t\n ",
            description = "abc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(PcMkplState.FAILING, ctx.state)
    assertEquals("abc", ctx.adValidated.title)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleEmpty(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("123"),
            title = "",
            description = "abc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PcMkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationTitleSymbols(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("123"),
            title = "!@#$%^&*(),.{}",
            description = "abc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PcMkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("title", error?.field)
    assertContains(error?.message ?: "", "title")
}
