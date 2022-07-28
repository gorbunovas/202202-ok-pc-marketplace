package validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.models.PcMkplCommand


@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationOffersTest {

    private val processor = PcMkplAdProcessor()
    private val command = PcMkplCommand.OFFERS

    @Test fun correctId() = validationIdCorrect(command, processor)
    @Test fun trimId() = validationIdTrim(command, processor)
    @Test fun emptyId() = validationIdEmpty(command, processor)
    @Test fun badFormatId() = validationIdFormat(command, processor)

}

