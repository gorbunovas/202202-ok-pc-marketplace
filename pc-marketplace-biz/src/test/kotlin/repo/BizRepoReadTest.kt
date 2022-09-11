
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.gorbunovas.pcmarketplace.AdRepoInMemory
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoReadTest {

    private val command = PcMkplCommand.READ
    private val initAd = PcMkplAd(
        id = PcMkplAdId("123"),
        title = "abc",
        description = "abc",
        adType = PcMkplDealSide.DEMAND,
        visibility = PcMkplVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { AdRepoInMemory(initObjects = listOf(initAd)) }
    private val settings by lazy {
        PcMkplSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { PcMkplAdProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadSuccessTest() = runTest {
        val ctx = PcMkplContext(
            command = command,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.TEST,
            adRequest = PcMkplAd(
                id = PcMkplAdId("123"),
            ),
        )
        processor.exec(ctx)
        assertEquals(PcMkplState.FINISHING, ctx.state)
        assertEquals(initAd.id, ctx.adResponse.id)
        assertEquals(initAd.title, ctx.adResponse.title)
        assertEquals(initAd.description, ctx.adResponse.description)
        assertEquals(initAd.adType, ctx.adResponse.adType)
        assertEquals(initAd.visibility, ctx.adResponse.visibility)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoReadNotFoundTest() = repoNotFoundTest(processor, command)
}
