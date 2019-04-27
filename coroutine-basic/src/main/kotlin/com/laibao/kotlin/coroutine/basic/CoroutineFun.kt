package com.laibao.kotlin.coroutine.basic

import kotlinx.coroutines.*
import java.time.Instant
import java.time.ZoneId
import kotlin.coroutines.CoroutineContext


fun main() {


    fun getTime(): Instant {
        return Instant.now().atZone(ZoneId.systemDefault()).toInstant()
    }

    println("${getTime()}: Start")

    // Start a coroutine
    GlobalScope.launch {
        delay(1000)
        println("${getTime()}: Hello from coroutine")
    }

    runBlocking {
        delay(1500)
        println("${getTime()}: Hello from second coroutine ")
    }
    println("${getTime()}: Stop")

    val deferred = (1..1_000_000).map { n ->
        GlobalScope.async { n }
    }

    runBlocking {
        val sum = deferred.sumBy { it.await() }
        println("Sum: $sum")
    }
}

class SimpleDataSource {

    suspend fun getTasks() = withContext(Dispatchers.Default) {
        delay(2018)
        //Result<Task>(error = NullPointerException())
        //Result<Task>(value = Task(id = 1,title = "wahaha",description = "我们的祖国是花园"))
    }
}


open class BasePresenter : CoroutineScope {

    private lateinit var job: Job

    //lateinit var view: IView

    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main + job

    /*
        fun bindView(view: IView) {
            this.view = view
            job = Job()
        }
    */

    fun unBindView() {
        job.cancel()
    }
}

