package com.laibao.kotlin.coroutine

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.*
import java.lang.Runnable
import kotlin.concurrent.thread
import kotlin.coroutines.CoroutineContext

/**
 * [揭开Kotlin协程的神秘面纱] (https://www.jdon.com/49698)
 */


val singleThreadDispatcher = newSingleThreadContext("singleThreadDispatcher")



fun <T> singleThreadAsync(block: () -> T): Job = runBlocking {
    launch(singleThreadDispatcher) { block.invoke() }
}


fun <T> myCoroutineDispatcherThread(block: () -> T): Job = runBlocking {
    launch(MyCoroutineDispatcher) { block.invoke() }
}


fun main() {

    runBlocking {
        val job = launch { myHeavyFunction() }
    }

    singleThreadAsync { myHeavyFunction() }


    myCoroutineDispatcherThread{
        myHeavyFunction()
    }

    runBlocking {
        launch(MyCoroutineDispatcher) {
            println("Thread Running ${Thread.currentThread().name}")
            println("Thread Running ${Thread.currentThread().name}")
            println("Thread Running ${Thread.currentThread().name}")
            println("Thread Running ${Thread.currentThread().name}")
            println("Thread Running ${Thread.currentThread().name}")
        }
    }

}


/*
这个是老版本的 launch函数 现在已经彻底修改掉了

DefaultDispatcher ----> 这个已经被 EmptyCoroutineContext 取代掉了

老版本中的下面的这个属性声明已经被新的 标记为 @JvmStatic 的新的属性取代了
public actual val DefaultDispatcher: CoroutineDispatcher = CommonPool

@JvmStatic
public actual val Default: CoroutineDispatcher = createDefaultDispatcher()

internal actual fun createDefaultDispatcher(): CoroutineDispatcher = if (useCoroutinesScheduler) DefaultScheduler else CommonPool


新的和老的默认最终都指向了CommonPool,都是ForkJoinPool.commonPool,也就是说都是ForkJoinPool【线程池】

这个线程池类根据当前CPU处理器总数创建一个带有Executors的CoroutineContext.

launch是一种协程构建器,将协程的创建委托给协程调度器CoroutineDispatcher来指定对应的线程池,调度器实际上负责在单独的线程中运行代码

public fun launch(
                    context:CoroutineContext =DefaultDispatcher,
                    start:CoroutineStart CoroutineStart.DEFAULT,
                    block:suspend CoroutineScope.()->Unit
            ):Job
*/


private fun myHeavyFunction() {
    println("Thread Running ${Thread.currentThread().name}")
}


/**
 * 让我们看看我们可以通过Dispatchers做更多事情
 *
 * 在这里我们创建了三个不同的调度器并重载了dispatch方法,
 *
 * 我们在每个dispatch方法中以不同的方式执行Runnable块,也就是一个简单的线程执行逻辑，
 *
 * 这三个调度器分别通过自定义调度器实现的,基于RxJava响应式框架实现,以及基于Android主线程使用Handler处理器来实现的
 */
object MyCoroutineDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        thread {
            block.run()
        }
    }
}


object RxDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        Observable.fromCallable { block.run() }
                .subscribeOn(Schedulers.io())
                .subscribe {}

    }
}

/*
object UIDispatcher : CoroutineDispatcher() {
    override fun dispatch(context: CoroutineContext, block: Runnable) {
        Handler(Looper.getMainLooper()).post {
            block.run()
        }
    }
}
*/


/**
 * 这真的显示了Kotlin Coroutine【协程】的强大功能,
 * 因为Coroutines只是语言语法,它们与运行它们的平台无关.
 * 不同线程的职责分配只需开发人员使用一组函数就能实现，
 * 开发人员可以在Rx线程或主线程上执行他喜欢的协同程序。
 *
 *
 * 协同程序就像空的冰淇淋甜筒，你可以选择你想要冰淇淋的填入
 */


/**
 *                              实战协程
 *
 *  在大多数情况下，我们需要来自一个异步执行的回调，这样我们就可以通过回调函数来更新UI等,这里就可以使用Deferred<T>：
 *
 *  Deferred<T>扩展了Job,并且可以在函数完成执行后返回结果。
 *
 *  让我们看看我们在这里做了什么:
 */

fun <T> asyncExecutor(block: () -> T, response: (T) -> Unit): Job = runBlocking{

    launch(Dispatchers.Unconfined) {
        val deferred = async(MyCoroutineDispatcher) { block.invoke() }
        response.invoke(deferred.await())
    }
}


/**
 * 现在我们在哪里可以使用他们呢? 数据库查询
 *
 *       Insert into DB without callback
 *       singleThreadAsync { movieDataBase.movieDao().insert(movieObject) }
 *
 *
 *
 *       Get List of movies from DB and filter it
 *       asyncExecutor({ movieDataBase.movieDao().getAll() }, { movieList ->
 *                                movieList.filter { it.isFavorite }
 *                                         .map { it.originalLanguage = "English" }
 *
 *                   //Dispatch to UI
 *       })
 */

