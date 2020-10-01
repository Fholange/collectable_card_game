package no.kristiania.model

import no.kristiania.dto.CardsDto
import no.kristiania.dto.Rarity


data class Card(
        val cardId : String?,
        val rarity: Rarity

) {
    constructor(dto: CardsDto): this(
            dto.cardId ?: throw IllegalArgumentException("Null cardId"),
             dto.rarity ?: throw IllegalArgumentException("Null rarity")
    )
}