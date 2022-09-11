import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import ru.gorbunovas.pcmarketplace.AdRepoInMemory
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import ru.gorbunovas.pcmarketplace.common.repo.IAdRepository
import kotlin.test.assertEquals

private val initAd = PcMkplAd(
    id = PcMkplAdId("123"),
    title = "abc",
    description = "abc",
    adType = PcMkplDealSide.DEMAND,
    visibility = PcMkplVisibility.VISIBLE_PUBLIC,
)
private val uuid = "10000000-0000-0000-0000-000000000001"
private val repo: IAdRepository
    get() = AdRepoInMemory(initObjects = listOf(initAd), randomUuid = { uuid })


@OptIn(ExperimentalCoroutinesApi::class)
fun repoNotFoundTest(processor: PcMkplAdProcessor, command: PcMkplCommand) = runTest {
    val ctx = PcMkplContext(
        command = command,
        state = PcMkplState.NONE,
        workMode = PcMkplWorkMode.TEST,
        adRepo = repo,
        adRequest = PcMkplAd(
            id = PcMkplAdId("12345"),
            title = "xyz",
            description = "xyz",
            adType = PcMkplDealSide.DEMAND,
            visibility = PcMkplVisibility.VISIBLE_TO_GROUP,
            lock = PcMkplAdLock(uuid),
        ),
    )
    processor.exec(ctx)
    assertEquals(PcMkplState.FAILING, ctx.state)
    assertEquals(PcMkplAd(), ctx.adResponse)
    assertEquals(1, ctx.errors.size)
    assertEquals("id", ctx.errors.first().field)
}
