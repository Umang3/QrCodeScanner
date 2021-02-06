package com.example.camscanner

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.budiyev.android.codescanner.AutoFocusMode
import com.budiyev.android.codescanner.CodeScanner
import com.budiyev.android.codescanner.CodeScannerView
import com.budiyev.android.codescanner.DecodeCallback
import com.budiyev.android.codescanner.ErrorCallback
import com.budiyev.android.codescanner.ScanMode
import com.google.android.material.snackbar.Snackbar
import java.util.*


class MainActivity : AppCompatActivity() {

    val constant = 1121
    private lateinit var codeScanner: CodeScanner
    lateinit var scannerView: CodeScannerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

      //  checkPermission()


        //   val relativeLayout = findViewById<RelativeLayout>(R.id.relativeLayout)

         scannerView = findViewById(R.id.scannerView)


        codeScanner = CodeScanner(this, scannerView)
        codeScanner.camera = CodeScanner.CAMERA_BACK // or CAMERA_FRONT or specific camera id
        codeScanner.formats = CodeScanner.ALL_FORMATS // list of type BarcodeFormat,
        // ex. listOf(BarcodeFormat.QR_CODE)
        codeScanner.autoFocusMode = AutoFocusMode.SAFE // or CONTINUOUS
        codeScanner.scanMode = ScanMode.SINGLE // or CONTINUOUS or PREVIEW
        codeScanner.isAutoFocusEnabled = true // Whether to enable auto focus or not
        codeScanner.isFlashEnabled = false // Whether to enable flash or not

        codeScanner.decodeCallback = DecodeCallback {
            runOnUiThread {
                Toast.makeText(this, "Scan result: ${it.text}", Toast.LENGTH_LONG).show()

                val intent = Intent(this,MainActivity2::class.java)
                intent.putExtra("result",it.text.toString())
                startActivity(intent)
                finishAffinity()

            }
        }
        codeScanner.errorCallback = ErrorCallback { // or ErrorCallback.SUPPRESS
            runOnUiThread {

                Toast.makeText(
                        this, "Camera initialization error: ${it.message}",
                        Toast.LENGTH_LONG
                ).show()
            }
        }
        checkPermission()


    }

    //        scannerView.setOnClickListener {
//            codeScanner.startPreview()
//        }
    fun checkPermission(){
        if(ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA),constant)
        }else{
            codeScanner.startPreview()
        }
    }

    override fun onRequestPermissionsResult(
            requestCode: Int,
            permissions: Array<out String>,
            grantResults: IntArray
    ) {
        if(requestCode == constant && grantResults.isEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
            codeScanner.startPreview()
        }else{
            Toast.makeText(
                    this, "Can not scan until you give camera permission",
                    Toast.LENGTH_LONG
            ).show()
        }
    }


    override fun onResume() {
        super.onResume()
        codeScanner.startPreview()
    }

    override fun onPause() {
        codeScanner.releaseResources()

        super.onPause()
    }


}

//    override fun onPause() {
//      //  codeScanner.releaseResources()
//        super.onPause()
//      //  codeScanner.startPreview()
//
//    }

//val snackbar = Snackbar.make(relativeLayout, "Result is ${it.text}",
//        Snackbar.LENGTH_LONG).setAction("Action", null)
//
//val snackbarView = snackbar.view
//
//val textView =
//        snackbarView.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
//textView.textSize = 28f
//snackbar.show()

