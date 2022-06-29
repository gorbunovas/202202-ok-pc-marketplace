package exceptions

import ru.gorbunovas.pcmarketplace.common.models.PcMkplCommand
import ru.gorbunovas.pcmarketplace.common.models.PcMkplComment

class UnknownMkplCommand(command: PcMkplCommand) : Throwable("Wrong command $command at mapping toTransport stage")
