import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.gorbunovas.pcmarketplace.AdRepoInMemory
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

class BizRepoCreateTest {

    private val command = PcMkplCommand.CREATE
    private val uuid = "10000000-0000-0000-0000-000000000001"
    private val repo = AdRepoInMemory(
        randomUuid = { uuid }
    )
    private val settings = PcMkplSettings(
        repoTest = repo
    )
    private val processor = PcMkplAdProcessor(settings)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoCreateSuccessTest() = runTest {
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
        assertEquals(PcMkplState.FINISHING, ctx.state)
        assertNotEquals(PcMkplAdId.NONE, ctx.adResponse.id)
        assertEquals("abc", ctx.adResponse.title)
        assertEquals("abc", ctx.adResponse.description)
        assertEquals(PcMkplDealSide.DEMAND, ctx.adResponse.adType)
        assertEquals(PcMkplVisibility.VISIBLE_PUBLIC, ctx.adResponse.visibility)
        assertEquals(uuid, ctx.adResponse.lock.asString())
    }
}
