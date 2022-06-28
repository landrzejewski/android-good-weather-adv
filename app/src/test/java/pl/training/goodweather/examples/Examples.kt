package pl.training.goodweather.examples

import kotlinx.coroutines.*
import org.junit.Test
import java.lang.Exception
import kotlin.RuntimeException
import kotlin.concurrent.thread

class Examples {

    @Test
    fun routines() {
        println("Main starts")
        routine(1, 500)
        routine(2, 200)
        println("Main has finished")
    }

    fun routine(number: Int, delay: Long) {
        println("Routine $number starts")
        Thread.sleep(delay)
        println("Routine $number has finished")
    }


    @Test
    fun threadedRoutines() {
        println("Main starts (${threadName()})")
        threadedRoutine(1, 500)
        threadedRoutine(2, 200)
        Thread.sleep(1_000)
        println("Main has finished (${threadName()})")
    }

    fun threadedRoutine(number: Int, delay: Long) {
        thread {
            println("Routine $number starts (${threadName()})")
            Thread.sleep(delay)
            println("Routine $number has finished (${threadName()})")
        }
    }

    @Test
    fun lotsOfThreadsProblem() {
        repeat(1_000_000) {
            thread {
                Thread.sleep(10_000)
                println("Done")
            }
        }
    }

    @Test
    fun coroutines() = runBlocking {
        println("Main starts (${threadName()})")
        launch { coroutine(1, 500) }
        launch { coroutine(2, 200) }
        println("Main has finished (${threadName()})")
    }

    suspend fun coroutine(number: Int, milliseconds: Long) {
        println("Routine $number starts (${threadName()})")
        delay(milliseconds)
        println("Routine $number has finished (${threadName()})")
    }

    @Test
    fun joinCoroutines() {
        println("Main starts (${threadName()})")
        val job =
            GlobalScope.launch(start = CoroutineStart.LAZY/*start = CoroutineStart.DEFAULT*/) {
                println("First job starts (${threadName()})")
                delay(200)
                println("First job has finished (${threadName()})")
            }
        // job.start()
        val secondJob = GlobalScope.launch {
            println("Second job starts (${threadName()})")
            job.join()
            println("Second job has finished (${threadName()})")
        }
        Thread.sleep(10_000)
        println("Main has finished (${threadName()})")
    }

    @Test
    fun hierarchicalJobs() {
        val parentJob = GlobalScope.launch {
            delay(200)
            println("Parent job has finished (${threadName()})")
        }
        GlobalScope.launch(parentJob) {
            delay(1_000)
            println("Child job has finished (${threadName()})")
        }
        if (parentJob.children.iterator().hasNext()) {
            println("Has children ${parentJob.children.first()}")
        }
        Thread.sleep(500)
        println("Is parent job finished: ${parentJob.isCompleted}")
        Thread.sleep(10_000)
        println("Main has finished (${threadName()})")
    }

    @Test
    fun sharedState() {
        var isDataLoaded = false
        GlobalScope.launch {
            delay(1_000)
            isDataLoaded = true
            println("Data loading has finished (${threadName()})")
        }
        GlobalScope.launch {
            repeat(3) {
                println("Data loading status (${threadName()}): $isDataLoaded")
                delay(500)
            }
        }
        Thread.sleep(10_000)
        println("Main has finished (${threadName()})")
    }

    @Test
    fun coroutinesWithResults() = runBlocking {
        val deferred = async { getData(1) }
        val secondDeferred = async { getData(2) }
        val thirdDeferred = getDataDeferred(3)
        println("Finished: ${deferred.await()} ${secondDeferred.await()} ${thirdDeferred.await()} (${threadName()})")
    }

    private suspend fun getData(number: Int): String {
        println("Job $number started (${threadName()})")
        delay(10_000)
        return "Response $number"
    }

    private fun getDataDeferred(number: Int): Deferred<String> = GlobalScope.async {
        println("Job $number started (${threadName()})")
        delay(10_000)
        "Response $number"
    }

    @Test
    fun cancelingCoroutines() = runBlocking {
        println("Main starts (${threadName()})")
        val job = launch {
            if (isActive) {
                println("First job starts (${threadName()})")
                try {
                    delay(10_000)
                } catch (exception: CancellationException) {
                    println("Cancellation exception")
                    throw exception
                }
                println("First job has finished (${threadName()})")
            }
        }
        delay(1_000)
        job.cancel()
        println("Main has finished (${threadName()})")
    }

    @Test
    fun cancelingHierarchicalJobs() = runBlocking {
        val parentJob = launch {
            delay(10_000)
            println("Parent job has finished (${threadName()})")
        }
        parentJob.invokeOnCompletion {
            if (it is CancellationException) {
                println("Parent wad cancelled")
            }
        }
        val childJob = launch(parentJob) {
            delay(1_000)
            println("Child job has finished (${threadName()})")
        }
        childJob.invokeOnCompletion {
            if (it is CancellationException) {
                println("Child wad cancelled")
            }
        }
        delay(500)
        // childJob.cancel()
        parentJob.cancel()
        delay(10_000)
        println("Main has finished (${threadName()})")
    }

    @Test
    fun exceptionPropagationInHierarchicalJobs() = runBlocking {
       // supervisorScope {
            val parentJob = launch {
                delay(10_000)
                println("Parent job has finished (${threadName()})")
            }
            parentJob.invokeOnCompletion {
                println("Parent isActive: $isActive ($it)")
            }
            val childJob = launch(parentJob) {
                delay(1_000)
                throw RuntimeException()
            }
            childJob.invokeOnCompletion {
                println("Child isActive: $isActive ($it)")
            }
            val secondChildJob = launch(parentJob) {
                delay(1_000)
            }
            secondChildJob.invokeOnCompletion {
                println("Second child isActive: $isActive ($it)")
            }
            delay(500)
            println("Main has finished (${threadName()})")
      //  }
    }

    val usersApi = createMockApi(UsersApi::class.java,
        MockResponse("https://localhost/users", listOf(1, 2)),
        MockResponse("https://localhost/users/1", UserDto(1, "Jan Kowalski", "jan.kowalski@training.pl")),
        MockResponse("https://localhost/users/2", UserDto(2, "Marek Nowak", "marek.nowak@training.pl")),
        MockResponse("https://localhost/users/3", UserDto(2, "Marek Nowak", "marek.nowak@training.pl"))
    )

    val brokenUsersApi = createMockApi(UsersApi::class.java,
        MockResponse("https://localhost/users", listOf(1, 2), delayOnMilliseconds = 5_000)
    )

    @Test
    fun singleRequest() {
        GlobalScope.launch {
            println("Result ${usersApi.getUsersIds()}")
        }
        Thread.sleep(1_000)
    }

    @Test
    fun manySequentialRequests() {
        GlobalScope.launch {
            val ids = usersApi.getUsersIds()
            println("Ids: $ids")
            ids.forEach {
                println(usersApi.getUser(it))
            }
        }
        Thread.sleep(1_000)
    }

    @Test
    fun manyConcurrentRequests() {
        GlobalScope.launch {
            val ids = usersApi.getUsersIds()
            println("Ids: $ids")
            val tasks = ids.map { async { usersApi.getUser(it) } }.toTypedArray()
            awaitAll(*tasks).forEach { println(it) }
        }
        Thread.sleep(1_000)
    }

    @Test
    fun usingTimeout() {
        GlobalScope.launch {
            try {
                val ids = withTimeout(500) {
                    brokenUsersApi.getUsersIds()
                }
                println("Ids: $ids")
            } catch (exception: TimeoutCancellationException) {
                println("Timeout")
            }
        }
        Thread.sleep(1_000)
    }

    @Test
    fun retryTest() = runBlocking {
        try {
            retry(3) {
                throw RuntimeException()
            }
        } catch (exception: Exception) {
            println("Failed")
        }
    }

    private suspend fun <T> retryWithTimeout(times: Int, maxDelayInMilliseconds: Long = 1_000, factor: Double = 2.0, task: suspend () -> T, timeoutInMilliseconds: Long = 1_000)
     = retry(times, maxDelayInMilliseconds, factor) {
         withTimeout(timeoutInMilliseconds) {
             task()
         }
    }

    private suspend fun <T> retry(times: Int, maxDelayInMilliseconds: Long = 1_000, factor: Double = 2.0, task: suspend () -> T): T {
        var delayValue = 0L
        repeat(times) {
            try {
                return task()
            } catch (exception: Exception) {
                println("Retry due to exception ${exception}")
            }
            delay(delayValue)
            delayValue = (delayValue * factor).toLong().coerceAtMost(maxDelayInMilliseconds)
        }
        return task()
    }

    private fun threadName() = Thread.currentThread().name

}