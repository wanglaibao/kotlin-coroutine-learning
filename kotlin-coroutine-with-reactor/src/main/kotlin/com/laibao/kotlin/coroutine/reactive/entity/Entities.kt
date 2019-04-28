package com.laibao.kotlin.coroutine.reactive.entity

import org.springframework.data.annotation.Id
import java.io.Serializable
import java.time.LocalDateTime

data class Person(@Id val id: String, val name: String, val email: String):Serializable

data class Audit(val eventDate: LocalDateTime, val email: String):Serializable

data class Message(val text: String, val messageDate: LocalDateTime, val email: String):Serializable