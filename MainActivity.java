package hatiboy.com.myapplication;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogClickInterface {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final String TAG = "MainActivity";
    private static final String PREFS_FILE_NAME = "M48_PREFERENCE";



//    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
//    public boolean acceptReadStoragePermission() {
//        boolean check = ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED;
//        Log.d(TAG, "acceptReadStoragePermission: checkself : " + check);
//        if (check) {
//            // Should we show an explanation?
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
//                    Manifest.permission.READ_EXTERNAL_STORAGE)) {
//                Toast.makeText(MainActivity.this, "shouldShowRequestPermissionRationale true", Toast.LENGTH_SHORT).show();
//                // Show an explanation to the user *asynchronously* -- don't block
//                // this thread waiting for the user's response! After the user
//                // sees the explanation, try again to request the permission.
//            } else {
//                // No explanation needed, we can request the permission.
//                Toast.makeText(MainActivity.this, "shouldShowRequestPermissionRationale false", Toast.LENGTH_SHORT).show();
//                // showConfirmDialog();
//            }
//            return false;
//        } else {
//            return true;
//        }
//    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Toast.makeText(this, "MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE Grant", Toast.LENGTH_SHORT).show();

                    // permission was granted
                    // we will extracy obb below

                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    showConfirmDialog();

                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewById(R.id.requestPermissionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                requestPermission();
            }
        });
    }

    public static void firstTimeAskingPermission(Context context, String permission, boolean isFirstTime) {
        SharedPreferences sharedPreference = context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE);
        sharedPreference.edit().putBoolean(permission, isFirstTime).apply();
    }

    public static boolean isFirstTimeAskingPermission(Context context, String permission) {
        return context.getSharedPreferences(PREFS_FILE_NAME, MODE_PRIVATE).getBoolean(permission, true);
    }

    private void showConfirmDialog() {
        if ((Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)) {
            RequestPermissionDialog.getInstance()
                    .showConfirmDialog(getResources().getString(R.string.request_permission_title),
                            getResources().getString(R.string.app_name) + " " + getResources().getString(R.string.request_permission_message),
                            "OK", "CANCEL", MainActivity.this, MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
        }
    }

    private void requestPermission() {
        // Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {
            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_CONTACTS)) {
                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.
                showConfirmDialog();
            } else {
                if (isFirstTimeAskingPermission(this, Manifest.permission.READ_CONTACTS)) {
                    firstTimeAskingPermission(this,
                            Manifest.permission.READ_CONTACTS, false);
                    // No explanation needed, we can request the permission.
                    ActivityCompat.requestPermissions(this,
                            new String[]{Manifest.permission.READ_CONTACTS},
                            123);
                    // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                    // app-defined int constant. The callback method gets the
                    // result of the request.
                } else {
                    //Permission disable by device policy or user denied permanently. Show proper error message
                    Toast.makeText(getApplicationContext(), "OOP! You blocked this permission, you couldnot play game", Toast.LENGTH_SHORT).show();
                }
            }
        } else {
            //permission granted. do your stuff
        }
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
    }

    @Override
    public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier) {
        //add your requestPermission function here:
        requestPermission();
        pDialog.dismiss();
    }

    @Override
    public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier) {
        pDialog.dismiss();
    }
}
