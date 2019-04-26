package com.laibao.kotlin.coroutine

import io.reactivex.Single
import kotlinx.coroutines.runBlocking
import java.util.concurrent.CompletableFuture

class Token

class Post

class Item

fun main() {
    val item = Item()

    //传统的同步调用方式
    postItem(item)

    // 采用回调的方式 callback
    postItemAsync(item)

    //Java8 CompletableFuture的方式
    postItemJava8(item)

    //采用Rx反应式的方式
    postItemRx(item)

    //采用kotlin coroutine的方式
    runBlocking {
        postItemKotiln(item)
    }
}

/**
 *  异步编程中最为常见的场景是:在后台线程执行一个复杂任,下一个任务依赖于上一个任务的执行结果,所以必须等待上一个任务执行完成后才能开始执行.
 *
 *  看下面代码中的三个函数,三个函数中的操作都是耗时操作,后两个函数都依赖于前一个函数的执行结果.
 */

fun requestToken(): Token {
    // makes request for a token & waits

    // returns result when received
    return Token()
}

fun createPost(token: Token, item: Item): Post {
    // sends item to the server & waits

    // returns resulting post
    return Post()
}

fun processPost(post: Post) {

    // does some local processing of result
}

fun postItem(item: Item) {
    val token = requestToken()
    val post = createPost(token,item)
    processPost(post)
}

/**
 *  callback: 使用回调,把之后需要执行的任务封装为回调
 */

fun requestTokenAsync(callback: (Token) -> Unit) { }

fun createPostAsync(token: Token, item: Item, callback: (Post) -> Unit) { }

fun processPostAsync(post: Post) {  }

fun postItemAsync(item: Item) {
    requestTokenAsync { token -> createPostAsync(token, item) { post -> processPostAsync(post) } }
}

/**
 * 回调在只有两个任务的场景是非常简单实用的,
 * 很多网络请求框架的onSuccessListener就是使用回调,但是在三个以上任务的场景中就会出现多层回调嵌套的问题,而且不方便处理异常.
 */


/**
 *  Future
 *  FutureTask
 *  CompletableFuture
 *  Java8 引入的CompletableFuture可以将多个任务串联起来,可以避免多层嵌套的问题.
 */

fun requestTokenAsync(): CompletableFuture<Token> {
    return CompletableFuture.supplyAsync { Token() }
}

fun createPostAsync(token: Token, item: Item): CompletableFuture<Post> {
    return CompletableFuture.supplyAsync { Post() }
}

fun processPostJava8(post: Post) { }

fun postItemJava8(item: Item) {
    requestTokenAsync()
            .thenCompose { token -> createPostAsync(token, item) }
            .thenAccept { post -> processPostJava8(post) }
            .exceptionally { e ->
                e.printStackTrace()
                null
            }
}


/**
 * Rx编程
 * CompletableFuture的方式有点类似Rx系列的链式调用
 * RxJava2,Reactor丰富的操作符,简便的线程调度
 */

fun requestTokenRx(): Token { return Token()}

fun createPostRx(token: Token, item: Item): Post { return Post() }

fun processPostRx(post: Post) {  }

fun postItemRx(item: Item) {
    Single.fromCallable { requestTokenRx() }
            .map { token -> createPostRx(token, item) }
            .subscribe(
                    { post -> processPostRx(post) },    // onSuccess
                    { e -> e.printStackTrace() }        // onError
            )
}


/**
 * 基于Kotlin的协程
 */

//挂起函数
suspend fun requestTokenKotlin(): Token { return Token() }

//挂起函数
suspend fun createPostKotlin(token: Token, item: Item): Post {return Post()
}

//挂起函数
suspend fun processPostKotlin(post: Post) { }

//挂起函数
suspend fun postItemKotiln(item: Item) {
        val token = requestToken()
        val post = createPost(token, item)
        processPost(post)
        // 需要异常处理，直接加上 try/catch 语句即可
}