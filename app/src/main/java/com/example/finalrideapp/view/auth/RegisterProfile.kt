package com.example.finalrideapp.view.auth

import android.Manifest
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.media.MediaScannerConnection
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.finalrideapp.R
import com.example.finalrideapp.databinding.ActivityRegisterProfileBinding
import com.example.finalrideapp.model.preferences.PreferenceProvider
import com.example.finalrideapp.viewmodel.RegisterProfileViewModel
import com.theartofdev.edmodo.cropper.CropImage
import com.theartofdev.edmodo.cropper.CropImageView
import kotlinx.android.synthetic.main.activity_register_profile.*
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.*

class RegisterProfile : AppCompatActivity() {

    private val CAMERA_PERMISSION_CODE = 1000
    private val GALLARY_PERMISSION_CODE = 1001

    private var ivGallery: ImageView? = null
    private var ivTakePhoto: ImageView? = null
    private var imageview: ImageView? = null
    private val GALLERY = 1
    private val CAMERA = 2


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_register_profile)



        val binding = DataBindingUtil.setContentView<ActivityRegisterProfileBinding>(this, R.layout.activity_register_profile)
        val viewModel = ViewModelProvider(this).get(RegisterProfileViewModel::class.java)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        val toolbarRegisterProfile = findViewById(R.id.tbRegisterProfile) as Toolbar
        setSupportActionBar(toolbarRegisterProfile)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

       // viewModel.displayUserName(this)
        val bundle :Bundle ?=intent.extras
        if (bundle!=null){
            tvRiderName.text = getString(R.string.hey) + " " + bundle.getString("Rider Name") + getString(R.string.exclamation)
        }


        tvRegisterProfileSkip.setOnClickListener() {
            val skipToNext = Intent(this, RegisterProfileSuccess::class.java)
            startActivity(skipToNext)
        }

        imageview = findViewById(R.id.imageView) as ImageView
        ivTakePhoto = findViewById(R.id.ivTakePhoto) as ImageView
        ivGallery = findViewById(R.id.ivGallery) as ImageView

        ivTakePhoto?.setOnClickListener(){

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.CAMERA)
                    == PackageManager.PERMISSION_DENIED ||
                    checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_DENIED){
                    //permission was not enabled
                    val permission = arrayOf(Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE)
                    //show popup to request permission
                    requestPermissions(permission, CAMERA_PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    takePhotoFromCamera()
                }
            }
            else{
                //system os is < marshmallow
                takePhotoFromCamera()
            }
        }
        ivGallery?.setOnClickListener() {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) ==
                    PackageManager.PERMISSION_DENIED){
                    //permission denied
                    val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE)
                    //show popup to request runtime permission
                    requestPermissions(permissions, GALLARY_PERMISSION_CODE)
                }
                else{
                    //permission already granted
                    choosePhotoFromGallary()
                }
            }
            else{
                //system OS is < Marshmallow
                choosePhotoFromGallary()
            }

        }


    }


    private fun choosePhotoFromGallary() {
        val galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(galleryIntent, GALLERY)
    }
    private fun takePhotoFromCamera() {
        val intent = Intent("android.media.action.IMAGE_CAPTURE")
        startActivityForResult(intent, CAMERA)
        //CropImage.startPickImageActivity(this)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        //called when user presses ALLOW or DENY from Permission Request Popup
        when(requestCode){
            CAMERA_PERMISSION_CODE -> {
                if (grantResults.size > 0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup was granted
                    takePhotoFromCamera()
                }
                else{
                    //permission from popup was denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }

            GALLARY_PERMISSION_CODE -> {
                if (grantResults.size >0 && grantResults[0] ==
                    PackageManager.PERMISSION_GRANTED){
                    //permission from popup granted
                    choosePhotoFromGallary()
                }
                else{
                    //permission from popup denied
                    Toast.makeText(this, "Permission denied", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    public override fun onActivityResult(requestCode:Int, resultCode:Int, data: Intent?) {

        super.onActivityResult(requestCode, resultCode, data)
        /* if (resultCode == this.RESULT_CANCELED)
         {
         return
         }*/
        val uri = data?.data
        if(requestCode==GALLERY && resultCode==RESULT_OK) {
            CropImage.activity(uri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMaxCropResultSize(1500, 1500)
                .setAspectRatio(1, 1)
                .start(this)

        }

        else if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK && data != null) {

            val result = CropImage.getActivityResult(data)
            try {
                val resultPath = result.uri
                val bitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, resultPath)
                val path = saveImage(bitmap)
                //saveImage(bitmap)
                //Toast.makeText(this@MainActivity, "File Saved::--->", Toast.LENGTH_SHORT).show()
                //imageview!!.setImageBitmap(bitmap)

                //lateinit var bundle: Bundle
                //bundle.putString("picture", path)
                PreferenceProvider(this).savePath(path)
                val nextPage = Intent(this, RegisterProfileSuccess::class.java)
                //nextPage.putExtra("picture", path)
                startActivity(nextPage)


                /*
                //val stream = ByteArrayOutputStream()
                //bitmap.compress(Bitmap.CompressFormat.PNG, 90, stream)
                //val byteArray = stream.toByteArray()
                val nextPage = Intent(this, Main2Activity::class.java)
                nextPage.putExtra("picture", bitmap)
                startActivity(nextPage)

                 */

            } catch (e: IOException) {
                e.printStackTrace()
                Toast.makeText(this, "Failed!", Toast.LENGTH_SHORT).show()
            }

        } else if (requestCode == CAMERA && resultCode == Activity.RESULT_OK && data != null) {

            //var result = CropImage.getActivityResult(data)
            //var resultPath = result.uri
            //val thumbnail = MediaStore.Images.Media.getBitmap(this.contentResolver, resultPath)
            val thumbnail = data.extras!!.get("data") as Bitmap
            //imageview!!.setImageBitmap(thumbnail)
            val imagePath = saveImage(thumbnail)
            //Toast.makeText(this@MainActivity, "Image Saved!", Toast.LENGTH_SHORT).show()

            PreferenceProvider(this).savePath(imagePath)
            val nextPage = Intent(this, RegisterProfileSuccess::class.java)
            //nextPage.putExtra("picture", imagePath)
            startActivity(nextPage)

            /*
            //val stream = ByteArrayOutputStream()
            //thumbnail.compress(Bitmap.CompressFormat.PNG, 90, stream)
            //val byteArray = stream.toByteArray()
            val nextPage = Intent()
            nextPage.setClass(this,Main2Activity::class.java)
            nextPage.putExtra("picture", thumbnail)
            startActivity(nextPage)

             */


        }

    }



    fun saveImage(myBitmap: Bitmap):String {
        val bytes = ByteArrayOutputStream()
        myBitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes)
        val wallpaperDirectory = File(
            (Environment.getExternalStorageDirectory()).toString() + IMAGE_DIRECTORY)
        // have the object build the directory structure, if needed.
        //Log.d("fee",wallpaperDirectory.toString())
        if (!wallpaperDirectory.exists())
        {

            wallpaperDirectory.mkdirs()
        }

        try
        {
            //Log.d("heel",wallpaperDirectory.toString())
            val f = File(wallpaperDirectory, ((Calendar.getInstance()
                .getTimeInMillis()).toString() + ".jpg"))
            f.createNewFile()
            val fo = FileOutputStream(f)
            fo.write(bytes.toByteArray())
            MediaScannerConnection.scanFile(this,
                arrayOf(f.getPath()),
                arrayOf("image/jpeg"), null)
            fo.close()
            //Log.d("TAG", "File Saved::--->" + f.getAbsolutePath())

            return f.getAbsolutePath()
        }
        catch (e1: IOException) {
            e1.printStackTrace()
        }

        return ""
    }

    companion object {
        private val IMAGE_DIRECTORY = "/rideapp"
    }


}
