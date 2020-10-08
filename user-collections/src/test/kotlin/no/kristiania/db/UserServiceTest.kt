package no.kristiania.db

import no.kristiania.CardService
import no.kristiania.FakeData
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Service

@Profile("UserServiceTest")
@Primary
@Service
class FakeCardService : CardService(){

    override fun fetchData() {
        val dto = FakeData.getCollectionDto()
        super.collection = Collection(dto)
    }
}


internal class UserServiceTest {
}