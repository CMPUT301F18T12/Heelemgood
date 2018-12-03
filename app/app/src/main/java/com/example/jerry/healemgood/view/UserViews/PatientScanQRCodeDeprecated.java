package com.example.jerry.healemgood.view.UserViews;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.google.zxing.Result;

import me.dm7.barcodescanner.zxing.ZXingScannerView;

import static android.Manifest.permission_group.CAMERA;

/**
 * handles patient scanning QR code
 * @author WeakMill98
 * @version 1.0
 * @since 2.0
 */

public class PatientScanQRCodeDeprecated extends AppCompatActivity implements ZXingScannerView.ResultHandler {

    private static final int REQUEST_CAMERA = 1;
    private ZXingScannerView scannerView;

    /**
     * Called when the QR code is generated
     * @param savedInstanceState Bundle
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      // Get a view for the scanner
        scannerView = new ZXingScannerView(this);
        setContentView(scannerView);

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            // If permission has been granted, execute the following
            if (checkPermission()){
                Toast.makeText(PatientScanQRCodeDeprecated.this, "Permission is Granted", Toast.LENGTH_LONG).show();
            }
            else {
                requestPermission();
            }
        }
    }

    /**
     * Method to check for permission
     * @return if permission is ok
     */

    private boolean checkPermission(){
        return (ContextCompat.checkSelfPermission(PatientScanQRCodeDeprecated.this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }

    /**
     * Request permission from the user
     */

    private void requestPermission(){
        ActivityCompat.requestPermissions(this, new String[]{CAMERA}, REQUEST_CAMERA);
    }

    /**
     * Upon getting permission, create some additional checks
     * @param requestCode int
     * @param permission String
     * @param grantResults int
     */

    public void onRequestPermissionsResult(int requestCode, String permission[], int grantResults[]){
        switch (requestCode){
            case REQUEST_CAMERA:
                if (grantResults.length > 0){
                    // If request to use the camera has been successful, use it
                    boolean cameraAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (cameraAccepted){
                        Toast.makeText(getApplicationContext(), "Permission Granted", Toast.LENGTH_LONG).show();
                    }
                    // If there is no permission to use the camera, request for it
                    else {
                        Toast.makeText(getApplicationContext(), "Permission Not Granted", Toast.LENGTH_LONG).show();
                        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if(shouldShowRequestPermissionRationale(CAMERA)){
                                displayAlertMessage("Please allow access for both permissions",
                                        new DialogInterface.OnClickListener() {
                                            @Override
                                            public void onClick(DialogInterface dialogInterface, int i) {
                                                requestPermissions(new String[]{CAMERA}, REQUEST_CAMERA);
                                            }
                                        });
                                return;
                            }
                        }
                    }
                }
                break;
        }
    }

    /**
     * Override the on resume of the parent, to show the scanner view
     */

    @Override
    public void onResume(){
        super.onResume();
        // Checks for the appropriate SDK version, as well as ensures permissions are present
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
            if (checkPermission()){
                if(scannerView == null){
                    scannerView = new ZXingScannerView(this);
                    setContentView(scannerView);
                }
                scannerView.setResultHandler(this);
                scannerView.startCamera();
            }
            else {
                requestPermission();
            }
        }
    }


    /**
     * Stop camera use once the activity is destroyed
     */

    @Override
    public void onDestroy(){
        super.onDestroy();;
        scannerView.stopCamera();
    }

    /**
     * notify alert message
     * @param message String
     * @param listener OnClickListener
     */

    public void displayAlertMessage(String message, DialogInterface.OnClickListener listener){
        new AlertDialog.Builder(getApplicationContext())
                .setMessage(message)
                .setPositiveButton("Ok", listener)
                .setNegativeButton("Cancel", null)
                .create()
                .show();
    }

    /**
     * Handles the result of the scan
     * @param result Result
     */

    // This is the method getting called when the QR code is being scanned
    @Override
    public void handleResult(final Result result){
        final String scanResult = result.getText();
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Scan Result");
        // Set actions to be taken by various buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                scannerView.resumeCameraPreview(PatientScanQRCodeDeprecated.this);
            }
        });
        // View the profile of the individual after selecting them
        builder.setNeutralButton("View Profile", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(scanResult));
                startActivity(intent);
            }
        });
        builder.setMessage(scanResult);
        AlertDialog alert = builder.create();
        alert.show();
    }

}
