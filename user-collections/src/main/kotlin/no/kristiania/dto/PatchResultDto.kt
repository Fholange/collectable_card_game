package no.kristiania.dto

import io.swagger.annotations.ApiModelProperty

data class PatchResultDto (
        @get:ApiModelProperty("If a card pack was opened, specify which cards were in it")
        var cardIdsInOpenedPack: MutableList<String> = mutableListOf()

)