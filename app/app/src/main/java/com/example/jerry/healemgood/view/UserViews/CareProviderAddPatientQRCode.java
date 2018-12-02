package com.example.jerry.healemgood.view.UserViews;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.controller.UserController;
import com.example.jerry.healemgood.model.user.CareProvider;
import com.example.jerry.healemgood.model.user.Patient;
import com.example.jerry.healemgood.model.user.User;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.example.jerry.healemgood.view.careProviderActivities.CareProviderAllPatientActivity;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

//https://www.youtube.com/watch?v=o69UqAKi47I&t=22s Accessed 2018-11-24
/**
 * An class used by the care provider to scan and add the QR code of a patient
 *
 * @author WeakMill98
 * @version 1.0
 * @since 2.0
 */

public class CareProviderAddPatientQRCode extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView textResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int requestCameraPermissionID = 1001;

    /**
     * Called when the app asks for permissions
     *
     * @param requestCode int
     * @param grantResults int
     * @param permissions String
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case requestCameraPermissionID: {
                // If permission has been granted
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
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
     * @param saveInstanceState Bundle
     */
    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_patient_scan_qrcode);

        // Get the paths to the XML elements
        cameraPreview = (SurfaceView) findViewById(R.id.cameraPreview);
        textResult = (TextView) findViewById(R.id.resultTextView);

        // Build the detector as well as a camera source
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
                    ActivityCompat.requestPermissions(CareProviderAddPatientQRCode.this,
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

        // Set the processor of the barcode detector
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
                            // Set the text result to the text scanned from the code
                            textResult.setText(qrcodes.valueAt(0).displayValue);
                            // Stop the camera after the source has been found
                            cameraSource.stop();

                            // Build a new dialog box
                            AlertDialog.Builder builder = new AlertDialog.Builder(CareProviderAddPatientQRCode.this);
                            builder.setTitle("User Profile Scanned Successfully!");

                            // Set a request to the user
                            builder.setPositiveButton("Add Selected Patient", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    try{
                                        // Get the patient and care provider objects
                                        // Get the username of the care provider stored in shared prefs
                                        User patient = new UserController.SearchPatientTask().execute(qrcodes.valueAt(0).displayValue).get();
                                        String careProviderUsername = SharedPreferenceUtil.get(CareProviderAddPatientQRCode.this, AppConfig.USERID);
                                        CareProvider careprovider = new UserController.SearchCareProviderTask().execute(careProviderUsername).get();
                                        try{
                                            // Add the id of the patient to the patients list of the care provider
                                            // Update the user account and move to the homepage of the doctor
                                            // Check if the user is already a patient
                                            ArrayList<String> patientsUserIds = careprovider.getPatientsUserIds();
                                            if (patientsUserIds.contains(qrcodes.valueAt(0).displayValue)){
                                                // Make a toast and exit
                                                Toast.makeText(CareProviderAddPatientQRCode.this,
                                                        "Patient has already been added",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                            else {
                                                // Add the patient
                                                careprovider.addPatientUserId(patient.getUserId());
                                                new UserController.UpdateUserTask().execute(careprovider);
                                                Toast.makeText(CareProviderAddPatientQRCode.this,
                                                        "Patient Added",
                                                        Toast.LENGTH_LONG).show();
                                            }
                                            // Go back to the home page of the care provider
                                            Intent intent = new Intent(getApplicationContext(), CareProviderAllPatientActivity.class);
                                            startActivity(intent);
                                        }catch (Exception e){}
                                    }catch (Exception e){}
                                }
                            });

                            // If the care provider does not want to add the patient
                            builder.setNeutralButton("Cancel", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Go back to the home page of the care provider
                                    Intent intent = new Intent(getApplicationContext(), CareProviderAllPatientActivity.class);
                                    startActivity(intent);
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
