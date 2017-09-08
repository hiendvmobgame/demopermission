package hatiboy.com.myapplication;

import android.Manifest;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements DialogClickInterface {

    private static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;
    private static final String TAG = "MainActivity";
    private static final String PREFS_FILE_NAME = "M48_PREFERENCE";
    private static final int REQUEST_EXTERNAL_STORAGE = 1234;


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

    @Override
    protected void onResume() {
        super.onResume();
        requestPermission();
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
        PermissionUtil.checkPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                new PermissionUtil.PermissionAskListener() {
                    @Override
                    public void onNeedPermission() {
                        ActivityCompat.requestPermissions(
                                MainActivity.this,
                                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, },
                                REQUEST_EXTERNAL_STORAGE
                        );
                    }

                    @Override
                    public void onPermissionPreviouslyDenied() {
                        //show a dialog explaining permission and then request permission
                        showConfirmDialog();
                    }

                    @Override
                    public void onPermissionDisabled() {
                        //user clicked to dont show request permission dialog again
                        // the game cannot request permission again.
                        Toast.makeText(getApplicationContext(), "Permission Disabled.", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onPermissionGranted() {
                        //extract obb
                    }
                });
    }

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
                }
                return;
            }
            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

    @Override
    public void onClickPositiveButton(DialogInterface pDialog, int pDialogIntefier) {
        //add your requestPermission function here:
        ActivityCompat.requestPermissions(
                MainActivity.this,
                new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},
                REQUEST_EXTERNAL_STORAGE
        );
        pDialog.dismiss();
    }

    @Override
    public void onClickNegativeButton(DialogInterface pDialog, int pDialogIntefier) {
        pDialog.dismiss();
    }
}
