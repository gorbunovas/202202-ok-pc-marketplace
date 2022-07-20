package validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Test
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.PcMkplContext
import ru.gorbunovas.pcmarketplace.common.models.*
import kotlin.test.*

@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationSearchTest {

    private val processor = PcMkplAdProcessor()
    private val command = PcMkplCommand.SEARCH

    @Test
    fun correctEmpty() = runTest {
        val ctx = PcMkplContext(
            command = command,
            state = PcMkplState.NONE,
            workMode = PcMkplWorkMode.TEST,
            adFilterRequest = PcMkplAdFilter()
        )
        processor.exec(ctx)
        assertEquals(0, ctx.errors.size)
        assertNotEquals(PcMkplState.FAILING, ctx.state)
    }
}

