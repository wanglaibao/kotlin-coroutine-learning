package com.laibao.kotlin.coroutine.reactive.controller

import com.laibao.kotlin.coroutine.reactive.repository.ReactiveAuditRepository
import com.laibao.kotlin.coroutine.reactive.repository.ReactiveMessageRepository
import com.laibao.kotlin.coroutine.reactive.repository.ReactivePeopleRepository
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class ReactiveController {

    lateinit var peopleRepository: ReactivePeopleRepository

    lateinit var messageRepository: ReactiveMessageRepository

    lateinit var auditRepository: ReactiveAuditRepository


    @GetMapping("/reactive/{personId}")
    fun getMessagesFor(@PathVariable personId: String): Mono<String> {
        return peopleRepository.findById(personId)
                .switchIfEmpty(Mono.error(NoSuchElementException()))
                .flatMap { person -> auditRepository.findByEmail(person.email)

                                                    .flatMap { lastLogin -> messageRepository.countByMessageDateGreaterThanAndEmail(lastLogin.eventDate, person.email)
                                                                                            .map { numberOfMessages -> "Hello ${person.name}, you have $numberOfMessages messages since ${lastLogin.eventDate}" }
                                                            }
                }
    }
}