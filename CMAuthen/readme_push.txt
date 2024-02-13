# Enable push feature by firebase tools menu
- Tools -> Firebase -> Cloud Messaging
- Link firebase console and select project
- automate configure build.grade

# Add lib at build.gradle (app)

plugins {
     ....
     id("com.google.gms.google-services")
}


// Push Notification
implementation("com.google.firebase:firebase-messaging-ktx:23.4.1")

# Add lib at build.gradle (project)
classpath("com.google.gms:google-services:4.3.14")

# Create service extend from FirebaseMessagingService
- MyFirebaseMessagingService.kt

# Update AndroidManifest.xml
          <service
              android:name=".services.MyFirebaseMessagingService"
              android:stopWithTask="false"
              android:exported="false">
              <intent-filter>
                  <action android:name="com.google.firebase.MESSAGING_EVENT" />
              </intent-filter>
          </service>


# Get Token at MainActivity.kt
- FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
             if (task.isComplete) {
                 val token = task.result;
                 Log.i(Constants.APP_TAG, "token: $token")
             }
         }

# Test push
- open https://console.firebase.google.com/
- select messaging
- new campaign -> notification
- set title and subtitle
- send test message
- wait for the notification

# case of updating ui MainActivity
-----------

To update an activity when you receive a push notification in Kotlin, you can use a combination of Firebase Cloud Messaging (FCM) and Android's broadcast mechanism. Here's how you can do it:

1. Create a custom BroadcastReceiver in your activity to receive messages from FCM:

```kotlin
class MyBroadcastReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        // This method will be called when a message is received from FCM
        // You can update your activity here
        if (intent?.action == "com.example.myapp.UPDATE_ACTIVITY") {
            // Handle the received message and update the activity
            // For example, you can extract data from the intent and update the UI
            val message = intent.getStringExtra("message")
            // Update your activity UI with the message data
        }
    }
}
```

2. Register the custom BroadcastReceiver in your activity's `onResume()` method:

```kotlin
class MyActivity : AppCompatActivity() {

    private val receiver = MyBroadcastReceiver()

    override fun onResume() {
        super.onResume()
        val intentFilter = IntentFilter("com.example.myapp.UPDATE_ACTIVITY")
        registerReceiver(receiver, intentFilter)
    }

    override fun onPause() {
        super.onPause()
        unregisterReceiver(receiver)
    }
}
```

3. In the `onMessageReceived` method of your FirebaseMessagingService, send a broadcast to your activity with the received message data:

```kotlin
class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // Handle the received message
        val message = remoteMessage.data["message"]

        // Create a new intent with the action for your custom broadcast receiver
        val intent = Intent("com.example.myapp.UPDATE_ACTIVITY")
        intent.putExtra("message", message)

        // Send the broadcast to the activity
        LocalBroadcastManager.getInstance(this).sendBroadcast(intent)
    }
}
```

By following these steps, your activity will receive the broadcast with the message data whenever a push notification is received, and you can update the activity's UI accordingly. Make sure to adjust the code according to your app's package name and other requirements.