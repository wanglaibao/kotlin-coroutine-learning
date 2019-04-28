package com.laibao.kotlin.coroutine.reactive.configuration

import com.laibao.kotlin.coroutine.reactive.entity.Audit
import com.laibao.kotlin.coroutine.reactive.entity.Message
import com.laibao.kotlin.coroutine.reactive.entity.Person
import com.laibao.kotlin.coroutine.reactive.repository.AuditRepository
import com.laibao.kotlin.coroutine.reactive.repository.MessageRepository
import com.laibao.kotlin.coroutine.reactive.repository.PeopleRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.context.annotation.Configuration
import java.time.Clock
import java.time.LocalDateTime

@Configuration
open class BootStrapCommandLineRunner (val peopleRepository: PeopleRepository,
                                       val auditRepository: AuditRepository,
                                       val messageRepository: MessageRepository,
                                       val clock: Clock): CommandLineRunner {

    override fun run(vararg args: String?) {
        peopleRepository.save(Person("P1", "Alex", "alex@example.com"))

        auditRepository.save(Audit(LocalDateTime.now(clock).minusDays(2), "alex@example.com"))

        messageRepository.save(Message("Hello", LocalDateTime.now(clock).minusDays(10), "alex@example.com"))

        messageRepository.save(Message("How are you", LocalDateTime.now(clock), "alex@example.com"))
    }
}