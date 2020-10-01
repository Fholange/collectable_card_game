package no.kristiania.db

import no.kristiania.CardService
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import javax.persistence.LockModeType

interface UserRepository : CrudRepository<User, String>{
@Lock(LockModeType.PESSIMISTIC_WRITE)
@Query("select u from User u where u.userId = :id")
fun lockedFind(@Param("id") userId: String) :User?
}

class UserService(
    private val userRepository: UserRepository,
    private val cardService: CardService
) {
companion object{

    const val CARDS_PER_DECK = 5
}

fun findUserByIdEeager(userId: String) : User?{
    val user = userRepository.findById(userId).orElse(null)
    if(user != null){
        user.ownedCards.size
    }
    return user
}

    fun registerNewUser(userId: String) : Boolean {
        if(userRepository.existsById(userId)){
            return false
        }

        val user = User()
        user.userId = userId
        user.cardPacks = 3
        user.coins = 100
        userRepository.save(user)
        return true
    }

    private fun validateCard(){
    if(cardService.isInitialized()){

    }
    }
}