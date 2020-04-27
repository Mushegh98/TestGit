package com.musheghmap.myapplication

import android.app.PendingIntent
import android.content.Intent
import android.content.IntentFilter
import android.nfc.NdefMessage
import android.nfc.NdefRecord
import android.nfc.NdefRecord.TNF_ABSOLUTE_URI
import android.nfc.NfcAdapter
import android.nfc.Tag
import android.nfc.tech.Ndef
import android.nfc.tech.NdefFormatable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import java.lang.Exception
import java.nio.charset.Charset
import java.util.*

class MainActivity : AppCompatActivity() {


    var nfcAdapter : NfcAdapter? = null


    fun enableForegroundDispatchSystem()
    {
        var intent = Intent(this,MainActivity::class.java).addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)
        var pendingIntent = PendingIntent.getActivity(this,0,intent,0)
        var intentFilter = arrayOf<IntentFilter>()
        nfcAdapter?.enableForegroundDispatch(this,pendingIntent,intentFilter,null)
    }

    fun disableForegroundDispatchSystem()
    {
        nfcAdapter?.disableForegroundDispatch(this)
    }

fun formatTag(tag : Tag , ndefMessage: NdefMessage)
{
    try {
        var ndefFormatable : NdefFormatable? = NdefFormatable.get(tag)
        if(ndefFormatable == null)
        {
            Toast.makeText(this,"Tag is not ndef formatable!",Toast.LENGTH_SHORT).show()
            return
        }
        ndefFormatable.connect()
        ndefFormatable.format(ndefMessage)
        ndefFormatable.close()

        Toast.makeText(this,"Tag writen",Toast.LENGTH_SHORT).show()
    }
    catch (e : Exception)
    {
        Log.e("TAG",e.message!!)
    }
}


    fun writeNdefMessage(tag : Tag? , ndefMessage: NdefMessage)
    {
        try {
            if(tag==null)
            {
                Toast.makeText(this,"Tag object cannot be null",Toast.LENGTH_SHORT).show()
                return
            }
            var ndef : Ndef? = Ndef.get(tag)
            if(ndef == null)
            {
                formatTag(tag,ndefMessage)
            }
            else
            {
                ndef.connect()
                if(!ndef.isWritable)
                {
                    Toast.makeText(this,"Tag is not writable",Toast.LENGTH_SHORT).show()
                    ndef.close()
                    return
                }

                ndef.writeNdefMessage(ndefMessage)
                ndef.close()
                Toast.makeText(this,"Tag writen",Toast.LENGTH_SHORT).show()

            }
        }
        catch (e : Exception)
        {
            Log.e("TAG",e.message!!)

        }
    }


    fun createTextRecord(content : String)
    {
        var language  = Locale.getDefault().language

    }

    fun createNdefMessage()
    {

        var ndefRecord : NdefRecord = creat

        var ndefMessage = NdefMessage(arrayOf<NdefRecord>(ndefRecord))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        nfcAdapter = NfcAdapter.getDefaultAdapter(this)

        //stugum enq NFC miacraca te che
        if(nfcAdapter!=null && nfcAdapter!!.isEnabled)
        {
            Toast.makeText(this,"NFC available",Toast.LENGTH_LONG).show()
        }
        else
        {
            Toast.makeText(this,"NFC not available",Toast.LENGTH_LONG).show()
        }


//        val uriRecord = ByteArray(0).let { emptyByteArray ->
//            NdefRecord(
//                TNF_ABSOLUTE_URI,
//                "http://developer.android.com/index.html".toByteArray(Charset.forName("US-ASCII")),
//                emptyByteArray,
//                emptyByteArray
//            )
//        }
        /*es el petqa avelacnenq manifestum`
 <intent-filter>
    <action android:name="android.nfc.action.NDEF_DISCOVERED" />
    <category android:name="android.intent.category.DEFAULT" />
    <data android:scheme="http"
        android:host="developer.android.com"
        android:pathPrefix="/index.html" />
</intent-filter>
         */



        val mimeRecord = NdefRecord.createMime(
            "application/vnd.com.example.android.beam",
            "Beam me up, Android".toByteArray(Charset.forName("US-ASCII"))
        )


    }

    override fun onNewIntent(intent: Intent?) {
//        Toast.makeText(this,"NFC intent received!",Toast.LENGTH_LONG).show()
        super.onNewIntent(intent)

        if(intent!!.hasExtra(NfcAdapter.EXTRA_TAG))
        {
            Toast.makeText(this,"NfcIntent!",Toast.LENGTH_SHORT).show()
        }

//        if (NfcAdapter.ACTION_NDEF_DISCOVERED == intent?.action) {
//            intent.getParcelableArrayExtra(NfcAdapter.EXTRA_NDEF_MESSAGES)?.also { rawMessages ->
//                val messages: List<NdefMessage> = rawMessages.map { it as NdefMessage }
//                // Process the messages array.
//            }
//        }
    }

    override fun onResume() {
//        val intent = Intent(this,MainActivity::class.java)
//        intent.addFlags(Intent.FLAG_RECEIVER_REPLACE_PENDING)
//        var pendingIntent = PendingIntent.getActivity(this,0,intent,0)
//        var intentFilter = arrayOf<IntentFilter>()
//        nfcAdapter?.enableForegroundDispatch(this,pendingIntent,intentFilter,null)

        super.onResume()
        enableForegroundDispatchSystem()
    }


    override fun onPause() {
//        nfcAdapter?.disableForegroundDispatch(this)
        super.onPause()
        disableForegroundDispatchSystem()
    }


}
