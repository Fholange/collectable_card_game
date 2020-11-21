package no.kristiania

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import no.kristiania.db.UserService
import no.kristiania.dto.Command
import no.kristiania.dto.PatchResultDto
import no.kristiania.dto.PatchUserDto
import no.kristiania.dto.UserDto
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.lang.IllegalArgumentException

@Api(value = "/api/user-collections", description = "Operations on card collections owned by users")
@RequestMapping(
        path = ["/api/user-collections"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)]
)

@RestController
class RestApi(private val userService: UserService) {

    @ApiOperation("Retrieve card collection information for a specific user")
    @GetMapping(path = ["/{userId}"])
    fun getUserInfo(
            @PathVariable("userId") userId: String
    ): ResponseEntity<UserDto> {
        val user = userService.findByIdEager(userId)
        if (user == null) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.status(200).body(DtoConverter.transform(user))

    }

    @ApiOperation("Create a new user, with the given id")
    @PutMapping(path = ["/{userId}"])
    fun createUser(
            @PathVariable("userId") userId: String
    ): ResponseEntity<Void> {
        val ok = userService.registerNewUser(userId)
        return if (!ok) ResponseEntity.status(400).build()
        else ResponseEntity.status(201).build()
    }


    @ApiOperation("Execute a command on a user's collection, like for example buying/milling cards")
    @PatchMapping(
            path = ["/{userId}"],
            consumes = [(MediaType.APPLICATION_JSON_VALUE)]
    )
    fun patchUser(
            @PathVariable("userId") userId: String,
            @RequestBody dto: PatchUserDto
    ): ResponseEntity<PatchResultDto> {
        if (dto.command == null) {
            return ResponseEntity.status(400).build()
        }
        if (dto.command == Command.OPEN_PACK) {
            val ids = try {
                userService.openPack(userId)
            } catch (e: IllegalArgumentException) {
                return ResponseEntity.status(400).build()
            }

            return ResponseEntity.status(200).body(PatchResultDto().apply { cardIdsInOpenedPack })
        }
        //saving card from patchUserDto if null return status 400
        val cardId = dto.cardId
                ?: return ResponseEntity.status(400).build()

        if (dto.command == Command.BUY_CARD) {

            try {
                userService.buyCard(userId, cardId)
            } catch (e: IllegalArgumentException) {
                return ResponseEntity.status(400).build()
            }
            return ResponseEntity.status(200).body(PatchResultDto())
        }

        if (dto.command == Command.MILL_CARD) {
            try {
                userService.millCard(userId, cardId)
            } catch (e: IllegalArgumentException) {
                return ResponseEntity.status(400).build()
            }
            return ResponseEntity.status(200).body(PatchResultDto())
        }

        return ResponseEntity.status(400).build()
    }


}
