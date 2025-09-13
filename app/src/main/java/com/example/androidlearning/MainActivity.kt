package com.example.androidlearning

import android.content.ContentValues.TAG
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import com.example.androidlearning.ui.theme.AndroidLearningTheme
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.concurrent.thread
import kotlin.math.log


class MainActivity : ComponentActivity() {
    val list = listOf(1, 2, 3, 4).asFlow()
    val TAG = "MainActivity"

    suspend fun consumee() {
        list.collect {
            Log.d(TAG, "consume: $it")
            delay(100)
        }
    }


    suspend fun callSito(): Int {
        repeat(500) { i ->
            Log.d(TAG, "callSito: $i")
            delay(10)
        }
        return 4
    }

    suspend fun callMito(): Int {
        repeat(500) { m ->
            Log.d(TAG, "callMito: $m")
            delay(10)
        }
        return 5
    }

    @OptIn(DelicateCoroutinesApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        val channel = Channel<Int>()
        GlobalScope.launch {
            repeat(50) { i ->
                delay(100)
                channel.send(i)
//                Log.d(TAG, "send: $i")
            }
        }

        GlobalScope.launch {
            channel.receiveAsFlow().collect { c->
                Log.d(TAG, "receive 1: $c ")

            }
//            for (c in channel) {
//                Log.d(TAG, "receive 1: $c ")
//            }
        }

        GlobalScope.launch {
            channel.receiveAsFlow().collect { c->
                Log.d(TAG, "receive 2: $c ")

            }
//            for (c in channel) {
//            }
        }

        GlobalScope.launch {
            channel.receiveAsFlow().collect { c->
                Log.d(TAG, "receive 3: $c ")

            }
//            for (c in channel) {
//                Log.d(TAG, "receive 3: $c ")
//            }
        }





//        val xd = MutableStateFlow<Int>(0)
//
//        GlobalScope.launch {
//            repeat(5) { i->
//                delay(100)
//                xd.emit(i)
//            }
//        }
//
//
//
//        GlobalScope.launch {
//            xd.collect {
//                Log.d(TAG, "collect 1: $it")
//            }
//        }
//
//        GlobalScope.launch {
//            delay(300)
//            xd.collect {
//                Log.d(TAG, "collect 2: $it")
//            }
//        }
////
//        // UI Event Channel - for user clicks, navigation, etc.
//        val uiEventChannel = Channel<String>()
//
//        // Progress Channel - for showing loading states
//        val progressChannel = Channel<Int>()
//
//        // Error Channel - conflated to show only latest error
//        val errorChannel = Channel<String>(Channel.CONFLATED)
//
//        // Unlimited buffer channel - for high-frequency events
//        val logChannel = Channel<String>(Channel.UNLIMITED)
//
//        // Producer: Simulates background work with progress updates
//        GlobalScope.launch(Dispatchers.IO) {
//            repeat(10) { progress ->
//                delay(500)
//                progressChannel.send(progress * 10) // Send progress 0%, 10%, 20%...
//                logChannel.send("Background task progress: ${progress * 10}%")
//
//                if (progress == 5) {
//                    errorChannel.send("Warning: Halfway done!")
//                }
//            }
//
//            progressChannel.send(100) // Complete
//            logChannel.send("Background task completed!")
//            uiEventChannel.send("TASK_COMPLETE")
//        }
//
//        // Consumer: UI thread listening to channels
//        GlobalScope.launch(Dispatchers.Main) {
//            // Listen to progress updates
//            launch {
//                for (progress in progressChannel) {
//                    Log.d(TAG, "UI Progress: $progress%")
//                }
//            }
//
//            // Listen to UI events
//            launch {
//                for (event in uiEventChannel) {
//                    Log.d(TAG, "UI Event: $event")
//                    when (event) {
//                        "TASK_COMPLETE" -> Log.d(TAG, "Show success dialog")
//                        "USER_CLICKED" -> Log.d(TAG, "Handle user click")
//                    }
//                }
//            }
//
//            // Listen to errors (conflated - only latest)
//            launch {
//                for (error in errorChannel) {
//                    Log.d(TAG, "UI Error: $error")
//                }
//            }
//
//            // Listen to logs (unlimited buffer)
//            launch {
//                for (logMsg in logChannel) {
//                    Log.d(TAG, "Log: $logMsg")
//                }
//            }
//        }
//
//        // Simulate user interaction
//        GlobalScope.launch {
//            delay(3000)
//            uiEventChannel.send("USER_CLICKED")
//
//            delay(2000)
//            errorChannel.send("Network error!")
//            errorChannel.send("Database error!") // This replaces previous error (CONFLATED)
//        }

//        scope.launch { produce() }
//        scope.launch { consume() }

//        val scope = CoroutineScope(Dispatchers.IO + CoroutineExceptionHandler {x,y -> {
//
//        }} + CoroutineName("MyCustomScope"))
//
//        scope.launch {
//
//        }
//        runBlocking {  }
//        val handler = CoroutineExceptionHandler {exception, throwable ->
//            Log.d(TAG, "exception handled")
//        }
//
//        GlobalScope.launch(handler) {
//            throw Exception("")
//        }


//        GlobalScope.launch(Dispatchers.Main) {
//            Log.d(TAG, "${Thread.currentThread().name}")
//            val x = launch {
//                Log.d(TAG, "${Thread.currentThread().name}")
//            }
////                callSito()}
////            val y = async { callMito()}
//        }


//        GlobalScope.launch {
//            val result : String? = withTimeoutOrNull(2000) {
//                repeat(500) { i->
//                    Log.d(TAG, "$i")
//                    delay(1)
//                }
//                "Completed"
//            }
//            delay(3000)
//            Log.d(TAG, result.toString())
//        }

//        val job = GlobalScope.launch {
//            repeat(1000) { i->
//                try {
//                    Log.d(TAG, "$i run")
//                    delay(10)
//                } catch (ex : CancellationException) {
//                    Log.d(TAG, "cancelled $ex")
//                }
//            }
//        }
//
//        Thread.sleep(200)
//        job.cancel()
//        CoroutineScope(Dispatchers.IO).launch {
//
//        }
//        runBlocking {
//            // Launch 1000 coroutines
//            repeat(1000) { i ->
//                launch {
//                    delay(100)
//                    Log.d(TAG, "$i completed")
//                }
//            }
//
//            delay(200)
//            Log.d(TAG, "process finish")
//        }
//
////        GlobalScope.launch {
////            Log.d(TAG, "work started 2 ${Thread.currentThread().name}}")
////            Log.d(TAG, "work finished 2${Thread.currentThread().name}")
////        }
//        Log.d(TAG, "finish ${Thread.currentThread().name}")

        setContent {
            AndroidLearningTheme {
                Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->
                    Greeting(
                        name = "Android",
                        modifier = Modifier.padding(innerPadding)
                    )
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    AndroidLearningTheme {
        Greeting("Android")
    }
}