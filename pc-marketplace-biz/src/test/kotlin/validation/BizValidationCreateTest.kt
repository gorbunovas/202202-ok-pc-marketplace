package validation

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Test
import ru.gorbunovas.pcmarketplace.PcMkplAdProcessor
import ru.gorbunovas.pcmarketplace.common.models.PcMkplCommand



@OptIn(ExperimentalCoroutinesApi::class)
class BizValidationCreateTest {

    private val processor = PcMkplAdProcessor()
    private val command = PcMkplCommand.CREATE

    @Test
    fun correctTitle() = validationTitleCorrect(command, processor)
    @Test fun trimTitle() = validationTitleTrim(command, processor)
    @Test fun emptyTitle() = validationTitleEmpty(command, processor)
    @Test fun badSymbolsTitle() = validationTitleSymbols(command, processor)

    @Test fun correctDescription() = validationDescriptionCorrect(command, processor)
    @Test fun trimDescription() = validationDescriptionTrim(command, processor)
    @Test fun emptyDescription() = validationDescriptionEmpty(command, processor)
    @Test fun badSymbolsDescription() = validationDescriptionSymbols(command, processor)

    @Test fun correctPruduct() = validationProductCorrect (command, processor)
    @Test fun badPruductCPU() = validationProductCpuEmpty(command, processor)
    @Test fun badPruductRAM() = validationProductRamEmpty(command, processor)
    @Test fun badPruductMotherboard() = validationProductMotherboardEmpty(command, processor)

}

