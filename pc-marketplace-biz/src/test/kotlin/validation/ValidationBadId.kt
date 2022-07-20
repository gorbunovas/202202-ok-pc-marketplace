package validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdCorrect(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("123-234-abc-ABC"),
            title = "abc",
            description = "abc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(PcMkplState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdTrim(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId(" \n\t 123-234-abc-ABC \n\t "),
            title = "abc",
            description = "abc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(0, ctx.errors.size)
    assertNotEquals(PcMkplState.FAILING, ctx.state)
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdEmpty(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId(""),
            title = "abc",
            description = "abc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PcMkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}

@OptIn(ExperimentalCoroutinesApi::class)
fun validationIdFormat(command: PcMkplCommand, processor: PcMkplAdProcessor) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRequest = PcMkplAd(
            id = PcMkplAdId("!@#\$%^&*(),.{}"),
            title = "abc",
            description = "abc",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        ),
    )
    processor.exec(ctx)
    assertEquals(1, ctx.errors.size)
    assertEquals(PcMkplState.FAILING, ctx.state)
    val error = ctx.errors.firstOrNull()
    assertEquals("id", error?.field)
    assertContains(error?.message ?: "", "id")
}
