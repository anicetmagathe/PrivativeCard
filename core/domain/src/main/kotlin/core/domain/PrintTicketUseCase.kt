package core.domain

import core.async.Dispatcher
import core.async.InDispatchers
import core.common.format
import core.model.entity.Categorie
import core.model.entity.Match
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import mg.anet.dll.device.printer.Newline
import mg.anet.dll.device.printer.PrintResult
import mg.anet.dll.device.printer.Printer
import mg.anet.dll.device.printer.Style
import mg.anet.dll.device.printer.Text
import javax.inject.Inject

class PrintTicketUseCase @Inject constructor(
    private val printer: Printer,
    @Dispatcher(InDispatchers.IO)
    private val ioDispatcher: CoroutineDispatcher
) {
    suspend operator fun invoke(match: Match, categorie: Categorie, count: Long): Result<Unit> =
        withContext(ioDispatcher) {
            val printerData = buildList {
                addAll(
                    listOf(
                        Text("Match: ${match.club1.name} vs ${match.club2.name}"),
                        Text("Stadium: ${match.stadium.name}"),
                        Text("Date: ${match.date.format("dd.MM.yyyy HH:mm")}"),
                        Text("Count: $count"),
                        Text("Price Unit: ${categorie.price.format()} €"),
                        Newline(2),
                        Text(
                            "Total: ${(categorie.price * count).format()} €",
                            style = Style(size = Style.Size.Big)
                        ),
                        Newline(5)
                    )
                )
            }.asIterable().toList()



            printer.print(printerData).mapCatching {
                if (it == PrintResult.Success) {
                    Unit
                } else {
                    throw Exception("Failed to print ticket")
                }
            }
        }
}