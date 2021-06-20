//package name
package com.example.ktserve

//Libraries needed for running Server
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.gson.gson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.server.netty.Netty

//Foreground necessities
import android.app.*
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import io.ktor.client.*
import io.ktor.server.engine.*
import kotlin.concurrent.thread

//ktor client end to call/request URL
import io.ktor.client.request.*
import io.ktor.client.response.*
import io.ktor.http.*
import kotlinx.coroutines.runBlocking

class MyService : Service() {
    private val CHANNEL_ID = "ForegroundService Kotlin"

    companion object {
        fun startService(context: Context, message: String) {
            val startIntent = Intent(context, MyService::class.java)
            startIntent.putExtra("inputExtra", message)
            ContextCompat.startForegroundService(context, startIntent)
        }

        fun stopService(context: Context, portz: Int) {
            val stopIntent = Intent(context, MyService::class.java)

            //Coroutine sa client.request/client.get are suspended function types and they either need to be in other suspended functions / coroutine
            runBlocking {
                val client = HttpClient()
                //ktor client get request method, this api routine helps shutdown ktor netty server
                val response: HttpResponse = client.request("http://localhost:$portz/ktor/application/shutdown") {
                    method = HttpMethod.Get
                }}
            context.stopService(stopIntent)
        }
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //do heavy work on a background thread
        val input = intent?.getStringExtra("inputExtra")
        createNotificationChannel()
        val notificationIntent = Intent(this, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            this,
            0, notificationIntent, 0
        )

        //Foreground notification bar settings
        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setContentTitle("Foreground Service Kotlin Example")
            .setContentText(input)
            .setContentIntent(pendingIntent)
            .setOngoing(true)
            .build()
        startForeground(1, notification)

        //to prevent application not responding error, we need to push application from UI thread to worker thread
        thread(start = true, isDaemon = true) {
            //port is 8124
            embeddedServer(Netty, 8124) {
                install(ContentNegotiation) {
                    gson {}
                }
                install(ShutDownUrl.ApplicationCallFeature) {
                    // The URL that will be intercepted
                    shutDownUrl = "/ktor/application/shutdown"
                    // A function that will be executed to get the exit code of the process
                    exitCodeSupplier = { 0 } // ApplicationCall.() -> Int
                }
                routing {
                    get("/") {
                        call.respond(mapOf("message" to "Hello world"))
                    }
                }
            }.start(wait = true)
        }

        return START_NOT_STICKY
    }
    override fun onBind(intent: Intent): IBinder? {
        return null
    }
    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val serviceChannel = NotificationChannel(CHANNEL_ID, "Foreground Service Channel",
                NotificationManager.IMPORTANCE_DEFAULT)
            val manager = getSystemService(NotificationManager::class.java)
            manager!!.createNotificationChannel(serviceChannel)
        }
    }
}