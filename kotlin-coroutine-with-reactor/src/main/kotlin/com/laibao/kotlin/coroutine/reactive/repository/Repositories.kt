package com.laibao.kotlin.coroutine.reactive.repository

import com.laibao.kotlin.coroutine.reactive.entity.Audit
import com.laibao.kotlin.coroutine.reactive.entity.Message
import com.laibao.kotlin.coroutine.reactive.entity.Person
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface PeopleRepository: CrudRepository<Person, String>


@Repository
interface AuditRepository: CrudRepository<Audit, String> {
    fun findByEmail(email: String): Audit
}

@Repository
interface MessageRepository: CrudRepository<Message, String> {
    fun countByMessageDateGreaterThanAndEmail(messageDate: LocalDateTime, email: String): Long
}