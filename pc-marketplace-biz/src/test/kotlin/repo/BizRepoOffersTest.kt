import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.gorbunovas.pcmarketplace.AdRepoInMemory
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

class BizRepoOffersTest {
    private val command = PcMkplCommand.OFFERS
    private val initAd = PcMkplAd(
        id = PcMkplAdId("123"),
        title = "abc",
        description = "abc",
        adType = PcMkplDealSide.DEMAND,
        visibility = PcMkplVisibility.VISIBLE_PUBLIC,
    )
    private val noneTypeAd = PcMkplAd(
        id = PcMkplAdId("213"),
        title = "abc",
        description = "abc",
        adType = PcMkplDealSide.NONE,
        visibility = PcMkplVisibility.VISIBLE_PUBLIC,
    )
    private val offerAd = PcMkplAd(
        id = PcMkplAdId("321"),
        title = "abcd",
        description = "xyz",
        adType = PcMkplDealSide.SUPPLY,
        visibility = PcMkplVisibility.VISIBLE_PUBLIC,
    )
    private val repo by lazy { AdRepoInMemory(initObjects = listOf(initAd, offerAd, noneTypeAd)) }
    private val settings by lazy {
        PcMkplSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { PcMkplAdProcessor(settings) }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoOffersSuccessTest() = runTest {
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
        assertEquals(1, ctx.adsResponse.size)
        assertEquals(PcMkplDealSide.SUPPLY, ctx.adsResponse.first().adType)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoOffersIllegalTypeTest() = runTest {
        val ctx = PcMkplContext(
            command = command,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.TEST,
            adRequest = PcMkplAd(
                id = PcMkplAdId("213"),
            ),
        )
        processor.exec(ctx)
        assertEquals(PcMkplState.FAILING, ctx.state)
        assertEquals(1, ctx.errors.size)
        assertEquals("adType", ctx.errors.first().field)
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun repoOffersNotFoundTest() = repoNotFoundTest(processor, command)
}
