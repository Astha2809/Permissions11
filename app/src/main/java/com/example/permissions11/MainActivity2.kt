package com.example.permissions11

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class MainActivity2 : AppCompatActivity() {

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)

        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "permission available", Toast.LENGTH_SHORT).show()

        } else {
            requestLocationPermission()
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun requestLocationPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION)) {
            Toast.makeText(this, "location permission required", Toast.LENGTH_LONG).show()
//            requestPermissions(
//                arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION),
//                100
//            )
            moveToSettingsDialouge()
            //add dialouge that would move to settings

        } else {
            Toast.makeText(this, "location permission not available", Toast.LENGTH_LONG).show()
            requestPermissions(arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION), 100)
            //moveToSettingsDialouge()

        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 100) {
            if (grantResults.size==1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "location permission granted", Toast.LENGTH_LONG).show()

            } else {
                Toast.makeText(this, "location permission important for following features", Toast.LENGTH_LONG).show()
                //moveToSettingsDialouge()

            }
        }

    }

    fun moveToSettingsDialouge(){
        MaterialAlertDialogBuilder(this)
            .setTitle("Permission Mandatory for the following feature")
            .setMessage("Change settings")
            .setPositiveButton("OK"){dialog,which->
                onPositiveClicked()

            }
            .setNegativeButton("Cancel"){dialog,which->

            }
            .show()

    }

    fun onPositiveClicked(){
        val settingIntent = Intent(
            Settings.ACTION_APPLICATION_DETAILS_SETTINGS,
            Uri.fromParts("package", packageName, null)
        )
        settingIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(settingIntent)
    }


}