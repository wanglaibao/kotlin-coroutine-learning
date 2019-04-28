package com.laibao.kotlin.coroutine.reactive.repository

import com.laibao.kotlin.coroutine.reactive.entity.Audit
import com.laibao.kotlin.coroutine.reactive.entity.Message
import com.laibao.kotlin.coroutine.reactive.entity.Person
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Mono
import java.time.LocalDateTime

@Repository
interface ReactivePeopleRepository: ReactiveCrudRepository<Person, String>

@Repository
interface ReactiveAuditRepository: ReactiveCrudRepository<Audit, String> {
    fun findByEmail(email: String): Mono<Audit>
}

@Repository
interface ReactiveMessageRepository: ReactiveCrudRepository<Message, String> {
    fun countByMessageDateGreaterThanAndEmail(messageDate: LocalDateTime, email: String): Mono<Long>
}