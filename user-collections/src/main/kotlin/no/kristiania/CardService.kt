package no.kristiania

import no.kristiania.model.Card
import no.kristiania.model.Collection
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class CardService {
    companion object{
        private val log = LoggerFactory.getLogger(CardService::class.java)
    }

    protected var collection: Collection? = null

    val cardCollection : List<Card>
        get() = collection?.cards ?: listOf()

    fun isInitialized() = false
}