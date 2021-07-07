/*Basic Plant Image processing.....
...............................
Created on 7/7/2021
BY K.V.HAREESH BABU
https://hareeshsmarttechnologies.in/
https://hareeshsmarttechnologies.in/portfolio/hari/
................................
 */
package hareeshsmarttechnologies.com.plantdoctor

import android.os.Bundle
import android.widget.Toast
import android.app.Activity
import android.content.Intent
import android.content.pm.ActivityInfo
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.support.annotation.RequiresApi
import android.support.v7.app.AppCompatActivity
import android.view.Gravity
import kotlinx.android.synthetic.main.activity_main.*
import java.io.IOException

class MainActivity : AppCompatActivity() {
    private lateinit var mClassifier: Classifier
    private lateinit var mBitmap: Bitmap

    private val mCameraRequestCode = 0
    private val mGalleryRequestCode = 2

    private val mInputSize = 224
    private val mModelPath = "model.tflite"
    private val mLabelPath = "labels.txt"
    private val mSamplePath = "skin-icon.jpg"


    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
        setContentView(R.layout.activity_main)
        mClassifier = Classifier(assets, mModelPath, mLabelPath, mInputSize)

        resources.assets.open(mSamplePath).use {
            mBitmap = BitmapFactory.decodeStream(it)
            mBitmap = Bitmap.createScaledBitmap(mBitmap, mInputSize, mInputSize, true)
            mPhotoImageView.setImageBitmap(mBitmap)
        }

        mCameraButton.setOnClickListener {
            val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(callCameraIntent, mCameraRequestCode)
        }

        mGalleryButton.setOnClickListener {
            val callGalleryIntent = Intent(Intent.ACTION_PICK)
            callGalleryIntent.type = "image/*"
            startActivityForResult(callGalleryIntent, mGalleryRequestCode)
        }
        mDetectButton.setOnClickListener {
                val results = mClassifier.recognizeImage(mBitmap).firstOrNull()
               // mResultTextView.text= results?.title+"\n Confidence:"+results?.confidence
            mResultTextView.text= results?.title
          val planttitle =mResultTextView.text

//            if(planttitle.equals("Neem")){
//                Toast.makeText(this, "Result Neem URL", Toast.LENGTH_LONG).show()
//                }
            val   baseurl = "https://hareeshsmarttechnologies.in/planturls/"
            when(planttitle) {

                "Aloe_vera" -> try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("$baseurl$planttitle.php")
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "The current phone does not have a browser installed", Toast.LENGTH_LONG).show()
            }
                "Guava" -> try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("$baseurl$planttitle.php")
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "The current phone does not have a browser installed", Toast.LENGTH_LONG).show()
                }
                "Jasmine" -> try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("$baseurl$planttitle.php")
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "The current phone does not have a browser installed", Toast.LENGTH_LONG).show()
                }


                "Lemon" -> try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("$baseurl$planttitle.php")
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "The current phone does not have a browser installed", Toast.LENGTH_LONG).show()
                }

                "Mango" -> try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("$baseurl$planttitle.php")
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "The current phone does not have a browser installed", Toast.LENGTH_LONG).show()
                }

                "Mint" -> try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("$baseurl$planttitle.php")
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "The current phone does not have a browser installed", Toast.LENGTH_LONG).show()
                }

                "Neem" -> try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("$baseurl$planttitle.php")
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "The current phone does not have a browser installed", Toast.LENGTH_LONG).show()
                }

                "Tulsi" -> try {
                    val intent = Intent(Intent.ACTION_VIEW)
                    intent.addCategory(Intent.CATEGORY_BROWSABLE)
                    intent.data = Uri.parse("$baseurl$planttitle.php")
                    startActivity(intent)
                } catch (e: Exception) {
                    Toast.makeText(this, "The current phone does not have a browser installed", Toast.LENGTH_LONG).show()
                }


                else -> Toast.makeText(this, "This is not a TRAINED MODULE", Toast.LENGTH_LONG).show()
            }


        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == mCameraRequestCode){
            //Considérons le cas de la caméra annulée
            if(resultCode == Activity.RESULT_OK && data != null) {
                mBitmap = data.extras!!.get("data") as Bitmap
                mBitmap = scaleImage(mBitmap)
                val toast = Toast.makeText(this, ("Image crop to: w= ${mBitmap.width} h= ${mBitmap.height}"), Toast.LENGTH_LONG)
                toast.setGravity(Gravity.BOTTOM, 0, 20)
                toast.show()
                mPhotoImageView.setImageBitmap(mBitmap)
                mResultTextView.text= "Your photo image set now."
            } else {
                Toast.makeText(this, "Camera cancel..", Toast.LENGTH_LONG).show()
            }
        } else if(requestCode == mGalleryRequestCode) {
            if (data != null) {
                val uri = data.data

                try {
                    mBitmap = MediaStore.Images.Media.getBitmap(this.contentResolver, uri)
                } catch (e: IOException) {
                    e.printStackTrace()
                }

                println("Success!!!")
                mBitmap = scaleImage(mBitmap)
                mPhotoImageView.setImageBitmap(mBitmap)

            }
        } else {
            Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_LONG).show()


        }
    }


    fun scaleImage(bitmap: Bitmap?): Bitmap {
        val orignalWidth = bitmap!!.width
        val originalHeight = bitmap.height
        val scaleWidth = mInputSize.toFloat() / orignalWidth
        val scaleHeight = mInputSize.toFloat() / originalHeight
        val matrix = Matrix()
        matrix.postScale(scaleWidth, scaleHeight)
        return Bitmap.createBitmap(bitmap, 0, 0, orignalWidth, originalHeight, matrix, true)
    }

}

