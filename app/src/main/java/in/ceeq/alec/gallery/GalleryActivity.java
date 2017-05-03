package in.ceeq.alec.gallery;

import android.Manifest;
import android.app.Activity;
import android.content.ClipData;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.Snackbar;
import android.support.design.widget.Snackbar.Callback;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.View;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.TimeZone;

import in.ceeq.alec.BuildConfig;
import in.ceeq.alec.R;
import in.ceeq.alec.utils.DeviceUtils;
import in.ceeq.alec.utils.FileUtils;
import in.ceeq.alec.utils.ImageUtils;
import in.ceeq.alec.utils.LogUtils;
import in.ceeq.alec.utils.PermissionUtils;

public class GalleryActivity extends AppCompatActivity {

    private static final String BUNDLE_IMAGE_URL = "result_image_url";
    private static final int IMAGE_DIMENSION = 500;
    private static final int REQUEST_ATTACH_IMAGE = 1001;
    private static final int REQUEST_SELECT_IMAGE = 1002;
    private static final int REQUEST_CAPTURE_IMAGE = 1003;
    private boolean mIsItemClicked;
    private boolean mIsPermissionGranted = true;
    private String mSelectedImagePath;
    private BottomSheetDialog mBottomSheetDialog;

    public static void startForSelect(AppCompatActivity context) {
        Intent intent = new Intent(context, GalleryActivity.class);
        Bundle bundle = new Bundle();
        intent.putExtras(bundle);
        context.startActivityForResult(intent, REQUEST_ATTACH_IMAGE);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);
        showBottomSheet();
    }

    public List<Item> createItems() {
        ArrayList<Item> items = new ArrayList<>();
        items.add(new Item(R.drawable.ic_photo_camera_black_24dp, getResources().getString(R.string.select_camera)));
        items.add(new Item(R.drawable.ic_image_black_24dp, getResources().getString(R.string.select_gallery)));
        return items;
    }

    /**
     * show bottom sheet with options to launch
     * Gallery or Camera to pick an image
     */
    private void showBottomSheet() {
        mBottomSheetDialog = new BottomSheetDialog(this);
        View view = getLayoutInflater().inflate(R.layout.layout_bottomsheet, null);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new ItemAdapter(createItems(), new ItemAdapter.ItemListener() {

            @Override
            public void onItemClick(int position) {
                if (mBottomSheetDialog != null) {
                    switch (position) {
                        case 0:
                            handleActionCamera();
                            break;
                        case 1:
                            mIsPermissionGranted =
                                    PermissionUtils.requestPermission(GalleryActivity.this,
                                            Manifest.permission
                                                    .WRITE_EXTERNAL_STORAGE);
                            if (mIsPermissionGranted) {
                                handleActionGallery();
                            }
                    }
                    mIsItemClicked = true;
                    mBottomSheetDialog.dismiss();
                }
            }
        }));

        mBottomSheetDialog.setContentView(view);
        mBottomSheetDialog.show();
        mBottomSheetDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (!mIsItemClicked) {
                    finish();
                }
            }

        });
    }

    private long getCurrentTimeInMillisInCurrentTimezone() {
        long currTimeInUtc = Calendar.getInstance().getTimeInMillis();
        Calendar calendar = new GregorianCalendar();
        TimeZone timeZone = calendar.getTimeZone();
        int mGMTOffset = timeZone.getRawOffset();
        return currTimeInUtc + mGMTOffset;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        ArrayList<String> selectedImagePaths = new ArrayList<>();

        switch (requestCode) {
            case REQUEST_SELECT_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    ClipData clipData = null;
                    if (DeviceUtils.hasJellyBeanPlus()) {
                        clipData = data.getClipData();
                    }
                    handleResultFromGallery(data.getData(), selectedImagePaths, clipData);
                }
                break;

            case REQUEST_CAPTURE_IMAGE:
                if (resultCode == Activity.RESULT_OK) {
                    handleResultFromCamera(selectedImagePaths);
                }
                break;
        }

        if (!selectedImagePaths.isEmpty()) {
            Intent resultIntent = new Intent();
            Bundle resultBundle = new Bundle();
            resultBundle.putStringArrayList(BUNDLE_IMAGE_URL, selectedImagePaths);
            resultIntent.putExtras(resultBundle);
            setResult(RESULT_OK, resultIntent);
        } else {
            setResult(RESULT_CANCELED);
        }

        finish();
    }

    private void handleResultFromGallery(@Nullable Uri selectedFileUri,
                                         ArrayList<String> selectedImagePaths,
                                         @Nullable ClipData clipData) {
        if (null == selectedFileUri) {
            if (clipData == null) {
                return;
            }
            for (int i = 0; i < clipData.getItemCount(); i++) {
                String pathString = getPathFromUri(clipData.getItemAt(i).getUri());
                selectedImagePaths.add(pathString);
            }
        } else {
            selectedImagePaths.add(getPathFromUri(selectedFileUri));
        }
    }

    private void handleResultFromCamera(ArrayList<String> selectedImagePaths) {
        if (null != mSelectedImagePath) {
            selectedImagePaths.add(mSelectedImagePath);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case PermissionUtils.REQUEST_CODE_PERMISSION_EXTERNAL_STORAGE:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    handleActionGallery();
                } else {
                    Snackbar snackbar = PermissionUtils.showPermissionDeclineMessage(this);
                    snackbar.addCallback(new Callback() {
                        @Override
                        public void onDismissed(Snackbar snackbar, int event) {
                            super.onDismissed(snackbar, event);
                            finish();
                        }
                    });
                }
                break;
        }
    }

    private void handleActionGallery() {
        Intent intentSelect = new Intent(Intent.ACTION_GET_CONTENT);
        intentSelect.setType(FileUtils.MIME_TYPE_IMAGE);
        intentSelect.putExtra(Intent.EXTRA_LOCAL_ONLY, true);
        startActivityForResult(Intent.createChooser(intentSelect, getString(R.string
                        .select_picture)), REQUEST_SELECT_IMAGE);
    }

    private void handleActionCamera() {
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (cameraIntent.resolveActivity(getPackageManager()) != null) {
            Uri cameraFileUri = getCameraFileUri();
            if (cameraFileUri != null) {
                cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, cameraFileUri);
                startActivityForResult(cameraIntent, REQUEST_CAPTURE_IMAGE);
            } else {
                Snackbar.make((findViewById(android.R.id.content)),
                        R.string.can_not_capture_photo,
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        } else {
            Snackbar.make(findViewById(android.R.id.content),
                    R.string.no_camera, Snackbar.LENGTH_SHORT)
                    .show();
        }
    }

    private Uri getCameraFileUri() throws NullPointerException {
        // Create an image file name
        File temporaryFile = createTempFile();
        Uri uploadFileUri;
        try {
            if (DeviceUtils.hasNougat()) {
                uploadFileUri = FileProvider.getUriForFile(this,
                        BuildConfig.APPLICATION_ID + ".fileprovider", temporaryFile.getAbsoluteFile());
            } else {
                uploadFileUri = Uri.fromFile(temporaryFile);
            }
        } catch (NullPointerException e) {
            LogUtils.logException(e);
            return null;
        }

        mSelectedImagePath = temporaryFile.getPath();
        return uploadFileUri;
    }

    private File createTempFile() {
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File temporaryFile = null;
        try {
            temporaryFile = File.createTempFile(
                    String.valueOf(getCurrentTimeInMillisInCurrentTimezone()),
                    ".jpg",
                    storageDir);
        } catch (IOException e) {
            LogUtils.logException(e);
        }

        return temporaryFile;
    }

    private String getPathFromUri(Uri data) {
        String path = FileUtils.getPath(this, data);
        File temporaryFile = createTempFile();
        if (null != temporaryFile && temporaryFile.exists()) {
            if (!TextUtils.isEmpty(path) && !TextUtils.isEmpty(temporaryFile.getPath())) {
                return ImageUtils.decodeSampledBitmap(path, temporaryFile.getPath(),
                        IMAGE_DIMENSION, IMAGE_DIMENSION, Boolean.TRUE);
            }
        }
        return null;
    }

    public static ArrayList<String> getSelectedImagePath(Intent data) {
        return data.getStringArrayListExtra(GalleryActivity.BUNDLE_IMAGE_URL);
    }
}
