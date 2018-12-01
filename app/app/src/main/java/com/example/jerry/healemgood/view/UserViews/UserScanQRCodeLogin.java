package com.example.jerry.healemgood.view.UserViews;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.jerry.healemgood.MainActivity;
import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.careProviderActivities.CareProviderAllPatientActivity;
import com.example.jerry.healemgood.view.patientActivities.PatientAllProblemActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
//https://www.youtube.com/watch?v=o69UqAKi47I&t=22s Accessed 2018-11-24
/**
 * An class used by the User to scan a Qr code for login
 *
 * @author WeakMill98
 * @version 1.0
 * @since 2.0
 */

public class UserScanQRCodeLogin extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView textResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int requestCameraPermissionID = 1001;

    /**
     * Called when the Activity starts, requests for permissions from the user
     *
     * @param requestCode
     * @param permissions
     * @param grantResults
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case requestCameraPermissionID: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // If permission has been granted
                    try {
                        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                            return;
                        }
                        cameraSource.start(cameraPreview.getHolder());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    /**
     * Called when the QR code is generated
     *
     * @param saveInstanceState
     */
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_patient_scan_qrcode);
        setTitle("QR Code");

        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        textResult = (TextView) findViewById(R.id.resultTextView);

        barcodeDetector = new BarcodeDetector.Builder(this)
                .setBarcodeFormats(Barcode.QR_CODE)
                .build();

        cameraSource = new CameraSource
                .Builder(this, barcodeDetector)
                .setRequestedPreviewSize(640, 480)
                .build();

        // Add an event
        cameraPreview.getHolder().addCallback(new SurfaceHolder.Callback() {

            @Override
            public void surfaceCreated(SurfaceHolder surfaceHolder) {
                if (ActivityCompat.checkSelfPermission(getApplicationContext(),
                        Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                    // Request permission from the user
                    ActivityCompat.requestPermissions(UserScanQRCodeLogin.this,
                            new String[]{Manifest.permission.CAMERA}, requestCameraPermissionID);
                    return;
                }
                try {
                    cameraSource.start(cameraPreview.getHolder());
                }catch (IOException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void surfaceChanged(SurfaceHolder surfaceHolder, int i, int i1, int i2) {

            }

            // Stop the camera once the surface holder is destroyed
            @Override
            public void surfaceDestroyed(SurfaceHolder surfaceHolder) {
                cameraSource.stop();
            }
        });

        // Set Processor
        barcodeDetector.setProcessor(new Detector.Processor<Barcode>() {
            @Override
            public void release() {

            }

            @Override
            public void receiveDetections(Detector.Detections<Barcode> detections) {
                final SparseArray<Barcode> qrcodes = detections.getDetectedItems();
                if(qrcodes.size() != 0){
                    textResult.post(new Runnable() {
                        @Override
                        public void run() {
                            // Create a vibrate for the phone
                            //Vibrator vibrator = (Vibrator)getApplicationContext().getSystemService(Context.VIBRATOR_SERVICE);
                            //vibrator.vibrate(500);

                            // Set the text result to the text scanned from the code
                            textResult.setText(qrcodes.valueAt(0).displayValue);
                            // Stop the camera after the source has been found
                            cameraSource.stop();

                            // Build a new dialog box
                            AlertDialog.Builder builder = new AlertDialog.Builder(UserScanQRCodeLogin.this);
                            builder.setTitle("User Profile Scanned Successfully!");

                            // View the profile of the individual after selecting them
                            // Will retrieve the username from the QR code
                            builder.setNeutralButton("Login to a New Device", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Search for the user account
                                    try{
                                        Patient patient = new UserController.SearchPatientTask().execute(qrcodes.valueAt(0).displayValue).get();
                                        try{
                                            if (patient.getUserId().equals(qrcodes.valueAt(0).displayValue)){
                                                //Store userid other important info into shared preference
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this,AppConfig.USERID,patient.getUserId());
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this,AppConfig.EMAIL,patient.getEmail());
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this,AppConfig.BIRTHDAY,patient.getBirthday().toString());
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this,AppConfig.NAME,patient.getFullName());
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this,AppConfig.PHONE,patient.getPhoneNum());
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this,AppConfig.ISPATIENT,AppConfig.TRUE);

                                                // Go to the home page of the patient
                                                Intent intent = new Intent(getApplicationContext(), PatientAllProblemActivity.class);
                                                startActivity(intent);
                                            }
                                        }catch (Exception e){
                                            User careprovider = new UserController.SearchCareProviderTask().execute(qrcodes.valueAt(0).displayValue.toString()).get();
                                            if (careprovider.getUserId().equals(qrcodes.valueAt(0).displayValue)) {
                                                //Store userid and other important info into shared preference
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this, AppConfig.USERID, careprovider.getUserId());
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this, AppConfig.EMAIL, careprovider.getEmail());
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this, AppConfig.BIRTHDAY, careprovider.getBirthday().toString());
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this, AppConfig.NAME, careprovider.getFullName());
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this, AppConfig.PHONE, careprovider.getPhoneNum());
                                                SharedPreferenceUtil.store(UserScanQRCodeLogin.this,AppConfig.ISPATIENT,AppConfig.FALSE);

                                                // Go to the home page of the care provider
                                                Intent intent = new Intent(getApplicationContext(), CareProviderAllPatientActivity.class);
                                                startActivity(intent);
                                            }
                                        }
                                    }catch (Exception e){}
                                }
                            });

                            // Set the message on the dialog box to be username of the individual
                            builder.setMessage("User ID: " + qrcodes.valueAt(0).displayValue);
                            AlertDialog alert = builder.create();
                            alert.show();
                            cameraSource.stop();
                        }
                    });
                }
            }
        });
    }

}
