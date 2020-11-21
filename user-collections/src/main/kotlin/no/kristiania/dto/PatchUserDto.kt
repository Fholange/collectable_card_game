package no.kristiania.dto

import io.swagger.annotations.ApiModelProperty

enum class Command{
    OPEN_PACK,
    MILL_CARD,
    BUY_CARD
}

data class PatchUserDto (
    @get:ApiModelProperty("Command to execute on a user's collectiuon")
    var command: Command? = null,

    @get:ApiModelProperty("Comma")
    var cardId: String? = null
    )