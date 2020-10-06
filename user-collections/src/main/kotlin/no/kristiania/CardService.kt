package no.kristiania

import no.kristiania.model.Card
import no.kristiania.model.Collection
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class CardService {
    companion object{
        private val log = LoggerFactory.getLogger(CardService::class.java)
    }

    protected var collection: Collection? = null

    val cardCollection : List<Card>
        get() = collection?.cards ?: listOf()
    private val lock = Any()

    @PostConstruct
    fun init(){
        synchronized(lock){
            if(cardCollection.isNotEmpty()){
                return
            }
            fetchData()
        }
    }

    fun isInitialized() = cardCollection.isNotEmpty()
    protected fun fetchData(){
        //todo
    }

    private fun verifyCollection(){

    }




}