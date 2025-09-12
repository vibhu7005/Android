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
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withTimeoutOrNull
import kotlin.concurrent.thread
import kotlin.math.log


class MainActivity : ComponentActivity() {
    val list = listOf(1,2,3,4).asFlow()
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

        //cold flows
        val x = flow {
            for (i in 1..10) {
                emit(i)
                Log.d(TAG, "emiited $i")
                delay(5000)
            }
        }

        val scope = CoroutineScope(Dispatchers.IO)
        scope.launch {
            x.collect {
                Log.d(TAG, "collected $it")
                delay(4000)
            }
        }

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