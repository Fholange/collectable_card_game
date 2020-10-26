package no.kristiania

import no.kristiania.db.CardCopy
import no.kristiania.db.User
import no.kristiania.dto.CardCopyDto
import no.kristiania.dto.UserDto

object DtoConverter {
    fun transform(user : User) : UserDto{
        return UserDto().apply {
            userId = user.userId
            coins = user.coins
            cardPacks = user.cardPacks
            ownedCards = user.ownedCards.map { transform(it) }.toMutableList()
        }
    }

    fun transform(cardCopy: CardCopy) : CardCopyDto{
        return CardCopyDto().apply {
            cardId = cardCopy.cardId
            numberOfCopies = cardCopy.numberOfCopies
        }
    }
}