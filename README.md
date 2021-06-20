# KTServe: kotlin_hidden_server
Android Application, Kotlin Server using netty framework and ktor Framework, Foreground Service, hidden from Recent Apps


* Only Files edited/created:
1. app-debug.apk        ->   AndroidStudioProjects\KTServe\app\build\outputs\apk\debug
2. build.gradle         ->   AndroidStudioProjects\KTServe\app
3. MainActivity.kt      ->   AndroidStudioProjects\KTServe\app\src\main\java\com\example\ktserve
4. MyService.kt         ->   AndroidStudioProjects\KTServe\app\src\main\java\com\example\ktserve
5. AndroidManifest.xml  ->   AndroidStudioProjects\KTServe\app\src\main
6. strings.xml          ->   AndroidStudioProjects\KTServe\app\src\main\res\values
7. activity_main.xml    ->   AndroidStudioProjects\KTServe\app\src\main\res\layout

* Disclaimer:
1. This program is an amalgam of several tutorials and example scripts available online.
2. This script can serve as a base for all those developers who have been searching for something similar.
3. This code is for development purposes, it cannot be used as is, as it currently serves a simple JSON output at port 8124.
4. To be tested over WIFI, either http://mobile_ip:8124 (from any device in same network) or http://localhost:8124 (from same mobile device)

* Used concepts:
1. kotlin threads and coroutines
2. ktor framework client and server scripts
3. foreground services and notifications
