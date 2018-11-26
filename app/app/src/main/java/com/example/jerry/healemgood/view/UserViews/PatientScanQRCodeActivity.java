package com.example.jerry.healemgood.view.UserViews;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.SparseArray;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.model.request.Request;
import com.google.android.gms.vision.CameraSource;
import com.google.android.gms.vision.Detector;
import com.google.android.gms.vision.barcode.Barcode;
import com.google.android.gms.vision.barcode.BarcodeDetector;

import org.w3c.dom.Text;

import java.io.IOException;

//https://www.youtube.com/watch?v=o69UqAKi47I&t=22s Accessed 2018-11-24

public class PatientScanQRCodeActivity extends AppCompatActivity {

    SurfaceView cameraPreview;
    TextView textResult;
    BarcodeDetector barcodeDetector;
    CameraSource cameraSource;
    final int requestCameraPermissionID = 1001;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case requestCameraPermissionID: {
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

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_patient_scan_qrcode);

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
                    ActivityCompat.requestPermissions(PatientScanQRCodeActivity.this,
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
                            AlertDialog.Builder builder = new AlertDialog.Builder(PatientScanQRCodeActivity.this);
                            builder.setTitle("User Profile Scanned Successfully!");

                            // View the profile of the individual after selecting them
                            builder.setNeutralButton("Login to a New Device", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Set the new intent here
                                    // ADD the scan function to the main page
                                }
                            });

                            // Set a request to the user
                            builder.setPositiveButton("Add Patient", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    // Add the user tp the patient's array of the current user

                                    // Check that the current user is a care provider


                                    // Get the object
                                    // Store it

                                    // If the user is not a care provider

                                    // Set the new intent here
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
