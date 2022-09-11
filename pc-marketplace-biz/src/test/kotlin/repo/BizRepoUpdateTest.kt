
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.gorbunovas.pcmarketplace.AdRepoInMemory
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import kotlin.test.Test
import kotlin.test.assertEquals

@OptIn(ExperimentalCoroutinesApi::class)
class BizRepoUpdateTest {

    private val command = PcMkplCommand.UPDATE
    private val uuidOld = "10000000-0000-0000-0000-000000000001"
    private val uuidNew = "10000000-0000-0000-0000-000000000002"
    private val uuidBad = "10000000-0000-0000-0000-000000000003"
    private val initAd = PcMkplAd(
        id = PcMkplAdId("123"),
        title = "abc",
        description = "abc",
        adType = PcMkplDealSide.DEMAND,
        visibility = PcMkplVisibility.VISIBLE_PUBLIC,
        lock = PcMkplAdLock(uuidOld),
    )
    private val repo by lazy { AdRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuidNew }) }
    private val settings by lazy {
        PcMkplSettings(
            repoTest = repo
        )
    }
    private val processor by lazy { PcMkplAdProcessor(settings) }

    @Test
    fun repoUpdateSuccessTest() = runTest {
        val adToUpdate = PcMkplAd(
            id = PcMkplAdId("123"),
            title = "xyz",
            description = "xyz",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_TO_GROUP,
            lock = PcMkplAdLock(uuidOld),
        )
        val ctx = PcMkplContext(
            command = command,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.TEST,
            adRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(PcMkplState.FINISHING, ctx.state)
        assertEquals(adToUpdate.id, ctx.adResponse.id)
        assertEquals(adToUpdate.title, ctx.adResponse.title)
        assertEquals(adToUpdate.description, ctx.adResponse.description)
        assertEquals(adToUpdate.adType, ctx.adResponse.adType)
        assertEquals(adToUpdate.visibility, ctx.adResponse.visibility)
        assertEquals(uuidNew, ctx.adResponse.lock.asString())
    }

    @Test
    fun repoUpdateConcurrentTest() = runTest {
        val adToUpdate = PcMkplAd(
            id = PcMkplAdId("123"),
            title = "xyz",
            description = "xyz",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_TO_GROUP,
            lock = PcMkplAdLock(uuidBad)
        )
        val ctx = PcMkplContext(
            command = command,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.TEST,
            adRequest = adToUpdate,
        )
        processor.exec(ctx)
        assertEquals(PcMkplState.FAILING, ctx.state)
        assertEquals(initAd.id, ctx.adResponse.id)
        assertEquals(initAd.title, ctx.adResponse.title)
        assertEquals(initAd.description, ctx.adResponse.description)
        assertEquals(initAd.adType, ctx.adResponse.adType)
        assertEquals(initAd.visibility, ctx.adResponse.visibility)
        assertEquals(uuidOld, ctx.adResponse.lock.asString())
    }

    @Test
    fun repoUpdateNotFoundTest() = repoNotFoundTest(processor, command)
}
