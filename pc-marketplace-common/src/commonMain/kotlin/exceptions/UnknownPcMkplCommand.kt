package ru.gorbunovas.pcmarketplace.common.exceptions

import ru.gorbunovas.pcmarketplace.common.models.PcMkplCommand

class UnknownPcMkplCommand(command: PcMkplCommand) : Throwable("Wrong command $command at mapping toTransport stage")
