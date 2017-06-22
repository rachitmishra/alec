package `in`.ceeq.alec.gallery

import android.Manifest
import android.app.Activity
import android.content.ClipData
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.support.design.widget.BottomSheetDialog
import android.support.design.widget.Snackbar
import android.support.design.widget.Snackbar.Callback
import android.support.v4.content.FileProvider
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.TextUtils
import android.view.View

import java.io.File
import java.io.IOException
import java.util.ArrayList
import java.util.Calendar
import java.util.GregorianCalendar
import java.util.TimeZone

import `in`.ceeq.alec.BuildConfig
import `in`.ceeq.alec.R
import `in`.ceeq.alec.utils.*
import android.support.v4.app.ActivityCompat.startActivityForResult
import java.nio.file.Files.exists

class GalleryActivity : AppCompatActivity() {
    private var mIsItemClicked: Boolean = false
    private var mIsPermissionGranted = true
    private var mSelectedImagePath: String? = null
    private var mBottomSheetDialog: BottomSheetDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_gallery)
        showBottomSheet()
    }

    fun createItems(): List<Item> {
        val items = ArrayList<Item>()
        items.add(Item(R.drawable.ic_photo_camera_black_24dp, resources.getString(R.string.select_camera)))
        items.add(Item(R.drawable.ic_image_black_24dp, resources.getString(R.string.select_gallery)))
        return items
    }

    /**
     * show bottom sheet with options to launch
     * Gallery or Camera to pick an image
     */
    private fun showBottomSheet() {
        mBottomSheetDialog = BottomSheetDialog(this)
        val view = layoutInflater.inflate(R.layout.layout_bottomsheet, null)
        val recyclerView = view.findViewById<View>(R.id.recyclerView) as RecyclerView
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = ItemAdapter(createItems(), ItemAdapter.ItemListener { position ->
            if (mBottomSheetDialog != null) {
                when (position) {
                    0 -> handleActionCamera()
                    1 -> {
                        mIsPermissionGranted = PermissionUtils.requestPermission(this@GalleryActivity,
                                Manifest.permission
                                        .WRITE_EXTERNAL_STORAGE)
                        if (mIsPermissionGranted) {
                            handleActionGallery()
                        }
                    }
                }
                mIsItemClicked = true
                mBottomSheetDialog!!.dismiss()
            }
        })

        mBottomSheetDialog!!.setContentView(view)
        mBottomSheetDialog!!.show()
        mBottomSheetDialog!!.setOnDismissListener {
            if (!mIsItemClicked) {
                finish()
            }
        }
    }

    private val currentTimeInMillisInCurrentTimezone: Long
        get() {
            val currTimeInUtc = Calendar.getInstance().timeInMillis
            val calendar = GregorianCalendar()
            val timeZone = calendar.timeZone
            val mGMTOffset = timeZone.rawOffset
            return currTimeInUtc + mGMTOffset
        }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        super.onActivityResult(requestCode, resultCode, data)
        val selectedImagePaths = ArrayList<String>()

        when (requestCode) {
            REQUEST_SELECT_IMAGE -> if (resultCode == Activity.RESULT_OK) {
                var clipData: ClipData? = null
                if (DeviceUtils.hasJellyBeanPlus()) {
                    clipData = data.clipData
                }
                handleResultFromGallery(data.data, selectedImagePaths, clipData)
            }

            REQUEST_CAPTURE_IMAGE -> if (resultCode == Activity.RESULT_OK) {
                handleResultFromCamera(selectedImagePaths)
            }
        }

        if (!selectedImagePaths.isEmpty()) {
            val resultIntent = Intent()
            val resultBundle = Bundle()
            resultBundle.putStringArrayList(BUNDLE_IMAGE_URL, selectedImagePaths)
            resultIntent.putExtras(resultBundle)
            setResult(Activity.RESULT_OK, resultIntent)
        } else {
            setResult(Activity.RESULT_CANCELED)
        }

        finish()
    }

    private fun handleResultFromGallery(selectedFileUri: Uri?,
                                        selectedImagePaths: ArrayList<String>,
                                        clipData: ClipData?) {
        if (null == selectedFileUri) {
            if (clipData == null) {
                return
            }
            for (i in 0..clipData.itemCount - 1) {
                val pathString = getPathFromUri(clipData.getItemAt(i).uri)
                selectedImagePaths.add(pathString!!)
            }
        } else {
            selectedImagePaths.add(getPathFromUri(selectedFileUri)!!)
        }
    }

    private fun handleResultFromCamera(selectedImagePaths: ArrayList<String>) {
        mSelectedImagePath?.let {
            selectedImagePaths.add(mSelectedImagePath!!)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>,
                                            grantResults: IntArray) {
        when (requestCode) {
            PermissionUtils.REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE -> if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                handleActionGallery()
            } else {
                val snackbar = PermissionUtils.showPermissionDeclineMessage(this)
                snackbar.addCallback(object : Callback() {
                    override fun onDismissed(snackbar: Snackbar?, event: Int) {
                        super.onDismissed(snackbar, event)
                        finish()
                    }
                })
            }
        }
    }

    private fun handleActionGallery() {
        val intentSelect = Intent(Intent.ACTION_GET_CONTENT)
        intentSelect.type = FileUtils.MIME_TYPE_IMAGE
        intentSelect.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        startActivityForResult(Intent.createChooser(intentSelect, getString(R.string
                .select_picture)), REQUEST_SELECT_IMAGE)
    }

    private fun handleActionCamera() {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (cameraIntent.resolveActivity(packageManager) != null) {
            val cameraFileUri = cameraFileUri
            if (cameraFileUri != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUri)
                startActivityForResult(cameraIntent, REQUEST_CAPTURE_IMAGE)
            } else {
                Snackbar.make(findViewById(android.R.id.content),
                        R.string.can_not_capture_photo,
                        Snackbar.LENGTH_SHORT)
                        .show()
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.no_camera, Snackbar.LENGTH_SHORT)
                    .show()
        }
    }

    private // Create an image file name
    val cameraFileUri: Uri?
        @Throws(NullPointerException::class)
        get() {
            val temporaryFile = createTempFile()
            val uploadFileUri: Uri
            try {
                if (DeviceUtils.hasNougat()) {
                    uploadFileUri = FileProvider.getUriForFile(this,
                            BuildConfig.APPLICATION_ID + ".fileprovider", temporaryFile!!.absoluteFile)
                } else {
                    uploadFileUri = Uri.fromFile(temporaryFile)
                }
            } catch (e: NullPointerException) {
                LogUtils.LOG(e)
                return null
            }

            mSelectedImagePath = temporaryFile!!.path
            return uploadFileUri
        }

    private fun createTempFile(): File? {
        val storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return try {
            File.createTempFile(currentTimeInMillisInCurrentTimezone.toString(), ".jpg", storageDir)
        } catch (e: IOException) {
            null
        }
    }

    private fun getPathFromUri(data: Uri): String? {
        val path = FileUtils.getPath(this, data)
        createTempFile()?.apply {
            check(exists()) {
                if (!TextUtils.isEmpty(path) && !TextUtils.isEmpty(this.path)) {
                    return ImageUtils.decodeSampledBitmap(path, this.path,
                            IMAGE_DIMENSION, IMAGE_DIMENSION, java.lang.Boolean.TRUE)
                }
            }
        }

        return null;
    }

    companion object {

        private val BUNDLE_IMAGE_URL = "result_image_url"
        private val IMAGE_DIMENSION = 500
        private val REQUEST_ATTACH_IMAGE = 1001
        private val REQUEST_SELECT_IMAGE = 1002
        private val REQUEST_CAPTURE_IMAGE = 1003

        fun startForSelect(context: AppCompatActivity) {
            val intent = Intent(context, GalleryActivity::class.java)
            val bundle = Bundle()
            intent.putExtras(bundle)
            context.startActivityForResult(intent, REQUEST_ATTACH_IMAGE)
        }

        fun getSelectedImagePath(data: Intent): ArrayList<String> {
            return data.getStringArrayListExtra(GalleryActivity.BUNDLE_IMAGE_URL)
        }
    }
}
