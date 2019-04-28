package com.laibao.kotlin.coroutine.reactive.controller

import com.laibao.kotlin.coroutine.reactive.repository.AuditRepository
import com.laibao.kotlin.coroutine.reactive.repository.MessageRepository
import com.laibao.kotlin.coroutine.reactive.repository.PeopleRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class BlockingController {

    lateinit var peopleRepository: PeopleRepository

    lateinit var auditRepository: AuditRepository

    lateinit var messageRepository: MessageRepository

    @GetMapping("/blocking/{personId}")
    fun getMessagesFor(@PathVariable personId: String): String {
        val person = peopleRepository.findById(personId)
                                    .orElseThrow { NoSuchElementException("Not found") }

        val lastLogin = auditRepository.findByEmail(person.email).eventDate

        val numberOfMessages = messageRepository.countByMessageDateGreaterThanAndEmail(lastLogin, person.email)

        return "Hello ${person.name}, you have $numberOfMessages messages since $lastLogin"
    }
}