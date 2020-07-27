package com.example.permissions11

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button_main.setOnClickListener {
            selectImage()


        }
        next_main.setOnClickListener {
            val intent=Intent(this,MainActivity2::class.java)
            startActivity(intent)
        }
    }
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == 100) {
            if (grantResults.size==1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "camera permission granted", Toast.LENGTH_LONG).show()
                val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(cameraIntent, 9347)
            } else {
                Toast.makeText(this, "camera permission denied", Toast.LENGTH_LONG).show()

            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    fun selectImage() {
        val options = arrayOf("Take Photo", "Choose From Gallery", "Cancel")
        val builder = MaterialAlertDialogBuilder(this)
        with(builder) {
            setItems(options) { dialog, which ->
                if (options[which] == "Take Photo") {
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED) {
                        //requestPermissions(String(){ Manifest.permission.CAMERA }, 100);
                        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                        startActivityForResult(takePictureIntent, 9347)
                        dialog.dismiss()
                    } else {

                         requestCameraPermission()

                    }
                    //Log.i("REquest code", targetRequestCode.toString())

                } else if (options[which] == "Choose From Gallery") {
                    dialog.dismiss()
                    val takeImageFromGallery = Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI
                    )
                    startActivityForResult(takeImageFromGallery, 8516)
                    //Log.i("REquest code", targetRequestCode.toString())
                } else if (options[which] == "Cancel") {
                    dialog.dismiss()
                }
            }


        }
        builder.show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            9347 -> if (resultCode == Activity.RESULT_OK && data != null) {

                Log.i("image added from camera", resultCode.toString())
                //image.setImageBitmap(attr.data.getExtras().get("data"))
                val photo: Bitmap = data.extras?.get("data") as Bitmap
                image.setImageBitmap(photo)
//                Bitmap photo = (Bitmap) data.getExtras().get("data");
//                imageView.setImageBitmap(photo);
                // image.setImageURI  uj


            }

            8516 -> if (resultCode == Activity.RESULT_OK && data != null && data.data != null) {


                Log.i("image added fromgallery", resultCode.toString())


                image.setImageURI(data.data)


            }
        }
    }



    @RequiresApi(Build.VERSION_CODES.M)
    fun requestCameraPermission() {
        if (shouldShowRequestPermissionRationale(Manifest.permission.CAMERA)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            Toast.makeText(this, "camera permission required", Toast.LENGTH_LONG).show()

            requestPermissions(
                arrayOf(Manifest.permission.CAMERA),
                100
            )


        } else {

            // Request the permission. The result will be received in onRequestPermissionResult().
            Toast.makeText(this, "camera permission not available", Toast.LENGTH_LONG).show()
            requestPermissions(arrayOf(Manifest.permission.CAMERA), 100)
        }
    }
}


