package com.laibao.kotlin.coroutine.reactive.controller

import com.laibao.kotlin.coroutine.reactive.repository.ReactiveAuditRepository
import com.laibao.kotlin.coroutine.reactive.repository.ReactiveMessageRepository
import com.laibao.kotlin.coroutine.reactive.repository.ReactivePeopleRepository
import kotlinx.coroutines.Dispatchers.Unconfined
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.reactive.awaitSingle
import kotlinx.coroutines.reactor.mono
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class CoroutineReactiveController {

    lateinit var peopleRepository: ReactivePeopleRepository

    lateinit var messageRepository: ReactiveMessageRepository

    lateinit var auditRepository: ReactiveAuditRepository

    @GetMapping("/coroutine/{personId}")
    fun getMessages(@PathVariable personId: String): Mono<String> = GlobalScope.mono(Unconfined){
        val person = peopleRepository.findById(personId).awaitSingle()
        val lastLogin = auditRepository.findByEmail(person.email).awaitSingle().eventDate
        val numberOfMessages = messageRepository.countByMessageDateGreaterThanAndEmail(lastLogin, person.email).awaitSingle()

        val message:String? = "Hello ${person.name}, you have $numberOfMessages messages since $lastLogin"

        message
    }
       /*
        mono(Unconfined) {
        val person = peopleRepository.findById(personId).awaitSingle()
        val lastLogin = auditRepository.findByEmail(person.email).awaitSingle().eventDate
        val numberOfMessages = messageRepository.countByMessageDateGreaterThanAndEmail(lastLogin, person.email).awaitSingle()

        val message:String? = "Hello ${person.name}, you have $numberOfMessages messages since $lastLogin"

        message
    }*/
}