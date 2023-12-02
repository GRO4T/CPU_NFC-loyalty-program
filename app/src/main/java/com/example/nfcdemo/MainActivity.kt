package com.example.nfcdemo

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NdefRecord.createMime
import android.nfc.NfcAdapter
import android.nfc.NfcEvent
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NfcA
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.nfcdemo.ui.theme.NFCDemoTheme

private var TAG = "MyActivity"

class MainActivity : NfcAdapter.ReaderCallback, ComponentActivity() { //, NfcAdapter.CreateNdefMessageCallback {
    private var nfcAdapter: NfcAdapter? = null;
    private lateinit var pendingIntent: PendingIntent
    var myTag: Tag? = null

    override fun onTagDiscovered(tag: Tag) {
        Log.d(TAG, "Discovered $tag!");

//        val nfc = NfcA.get(tag)
//        nfc.connect()
//
//        val isConnected = nfc.isConnected()
//        Log.d(TAG, "isConnected=$isConnected")
//        if (isConnected) {
//            val command: ByteArray = byteArrayOf(0x30.toByte(), (0 and 0x0ff).toByte(), (42 and 0x0ff).toByte())
//            val receivedData = nfc.transceive(command)
//            Log.d(TAG, "recv_data=$receivedData")
//        }
        val ndef = Ndef.get(tag)

        val ndefMessage = ndef.cachedNdefMessage

        val records = ndefMessage.records

        Log.d(TAG, "records=$records")

        for (ndefRecord in records) {
            if (ndefRecord.tnf == NdefRecord.TNF_WELL_KNOWN) {
                Log.d(TAG, "ndefRecord=$ndefRecord")
                val decoded = ndefRecord.payload.decodeToString()
                Log.d(TAG, "decoded=$decoded")
                val payload = ndefRecord.payload
                Log.d(TAG, "payload=$payload size=${payload.size}")
                val text = payload.copyOfRange(3, payload.size).decodeToString()
                Log.d(TAG, "text=$text size=${text.length}")
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            NFCDemoTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android")
                }
            }
        }

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)
        if (nfcAdapter == null) {
            Toast.makeText(this, "NFC is not available", Toast.LENGTH_LONG).show()
            finish()
            return
        }
        val feature = packageManager.hasSystemFeature("com.nxp.mifare")
        Log.d(TAG, "system_feauter=$feature")
        nfcAdapter.let { it }
        Log.d(TAG, "Obtained NFC default adapter")
//        nfcAdapter?.setNdefPushMessageCallback(this, this);

//        readFromIntent(intent)
//
//        pendingIntent = PendingIntent.getActivity(
//            this,
//            0,
//            Intent(this, javaClass).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP),
//            PendingIntent.FLAG_IMMUTABLE
//        )
//        val tagDetected = IntentFilter(NfcAdapter.ACTION_TAG_DISCOVERED)
//        tagDetected.addCategory(Intent.CATEGORY_DEFAULT)
    }

//    override fun createNdefMessage(event: NfcEvent): NdefMessage {
//        val text = "Beam me up, Android!\n\n" +
//                "Beam Time: " + System.currentTimeMillis()
//        return NdefMessage(
//            arrayOf(
//                createMime("application/vnd.com.example.android.beam", text.toByteArray())
//            )
//            /**
//             * The Android Application Record (AAR) is commented out. When a device
//             * receives a push with an AAR in it, the application specified in the AAR
//             * is guaranteed to run. The AAR overrides the tag dispatch system.
//             * You can add it back in to guarantee that this
//             * activity starts when receiving a beamed message. For now, this code
//             * uses the tag dispatch system.
//             */
//            /**
//             * The Android Application Record (AAR) is commented out. When a device
//             * receives a push with an AAR in it, the application specified in the AAR
//             * is guaranteed to run. The AAR overrides the tag dispatch system.
//             * You can add it back in to guarantee that this
//             * activity starts when receiving a beamed message. For now, this code
//             * uses the tag dispatch system.
//             *///,NdefRecord.createApplicationRecord("com.example.android.beam")
//        )
//    }

//    override fun onResume() {
//        super.onResume()
//        // Check to see that the Activity started due to an Android Beam
//        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent.action) {
//            processIntent(intent)
//        }
//    }

    override fun onNewIntent(intent: Intent) {
        Log.d(TAG, "onNewIntent executed")
        super.onNewIntent(intent)
        readFromIntent(intent)

//        var tagFromIntent: Tag? = intent?.getParcelableExtra(NfcAdapter.EXTRA_TAG)
//        val nfc = NfcA.get(tagFromIntent)
//
//        nfc.connect()
//
//        val isConnected = nfc.isConnected()
//
//        if (isConnected) {
//            val data = byteArrayOf(0x48, 0x48)
//            val receivedData: ByteArray = nfc.transceive(data)
//            Log.d(TAG, "Received data: $receivedData")
//        } else {
//            Log.d(TAG, "Not connected")
//        }
    }

    private fun readFromIntent(intent: Intent) {
        Log.d(TAG, "readFromIntent executed")
        Log.d(TAG, "intent=$intent")
        val action = intent.action
        Log.d(TAG, "action=$action")
        if (NfcAdapter.ACTION_TAG_DISCOVERED == action || NfcAdapter.ACTION_TECH_DISCOVERED == action || NfcAdapter.ACTION_NDEF_DISCOVERED == action) {
            myTag = intent.getParcelableExtra<Parcelable>(NfcAdapter.EXTRA_TAG) as Tag?
            Log.d(TAG, "tag=$myTag")
            val rawMsgs = intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)
            Log.d(TAG, "rawMsgs=$rawMsgs")
            var msgs = mutableListOf<NdefMessage>()
            if (rawMsgs != null) {
                for (i in rawMsgs.indices) {
                    msgs.add(i, rawMsgs[i] as NdefMessage)
                    Log.d(TAG, "rasMsgs[$i] = ${rawMsgs[i]}")
                }
//                buildTagViews(msgs.toTypedArray())
            }
        }
    }

    private fun enableForegroundDispatch(activity: ComponentActivity, adapter: NfcAdapter?) {

        val intent = Intent(activity.applicationContext, activity.javaClass)
        intent.flags = Intent.FLAG_ACTIVITY_SINGLE_TOP

        val pendingIntent = PendingIntent.getActivity(activity.applicationContext, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)

        val filters = arrayOfNulls<IntentFilter>(1)
        val techList = arrayOf<Array<String>>()

        filters[0] = IntentFilter()
        with(filters[0]) {
            this?.addAction(NfcAdapter.ACTION_NDEF_DISCOVERED)
            this?.addCategory(Intent.CATEGORY_DEFAULT)
            try {
                this?.addDataType("text/plain")
            } catch (ex: IntentFilter.MalformedMimeTypeException) {
                throw RuntimeException(ex)
            }
        }

        adapter?.enableForegroundDispatch(activity, pendingIntent, filters, techList)
    }
    override fun onResume() {
        Log.d(TAG, "onResume executed")
        super.onResume()

//        enableForegroundDispatch(this, this.nfcAdapter)
        val pendingIntent = PendingIntent.getActivity(this, 0, intent,
            PendingIntent.FLAG_IMMUTABLE)
        Log.d(TAG, "pendingIntent=$pendingIntent")
        Log.d(TAG, "nfcAdapter=$nfcAdapter")
        nfcAdapter?.enableForegroundDispatch(this, pendingIntent, null, null)
    }

    override fun onPause() {
        super.onPause()
        Log.d(TAG, "onPause nfcAdapter=$nfcAdapter")
        nfcAdapter?.disableForegroundDispatch(this)
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "onStart nfcAdapter=$nfcAdapter")
        nfcAdapter?.enableReaderMode(
            this,
            this,
            NfcAdapter.FLAG_READER_NFC_A
                    or NfcAdapter.FLAG_READER_NFC_B
                    or NfcAdapter.FLAG_READER_NFC_F
                    or NfcAdapter.FLAG_READER_NFC_V
                    or NfcAdapter.FLAG_READER_NFC_BARCODE,
            null
        )
    }

    override fun onStop() {
        super.onStop()
        nfcAdapter?.disableReaderMode(this)
    }

//    /**
//     * Parses the NDEF Message from the intent and prints to the TextView
//     */
//    private fun processIntent(intent: Intent) {
//        // only one message sent during the beam
//        intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMsgs ->
//            (rawMsgs[0] as NdefMessage).apply {
//                // record 0 contains the MIME type, record 1 is the AAR, if present
//                Log.d(TAG, String(records[0].payload))
//            }
//        }
//    }
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
    NFCDemoTheme {
        Greeting("Android")
    }
}