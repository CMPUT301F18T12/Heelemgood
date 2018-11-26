package com.example.jerry.healemgood.view.UserViews;

import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.jerry.healemgood.R;
import com.example.jerry.healemgood.config.AppConfig;
import com.example.jerry.healemgood.utils.SharedPreferenceUtil;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PatientGenerateQRCode extends AppCompatActivity {

    private Button createQRCodeButton;
    private Button scanQRCode;
    private ImageView qrCodeImageView;
    private String text;

    // https://www.youtube.com/watch?v=6HfUk5AJIn8 Accessed 2018-11-23
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_qrcode);

        // Get the xml elements on the UI
        createQRCodeButton = findViewById(R.id.generateCodeButton);
        qrCodeImageView = findViewById(R.id.qrCodeImageView);
        text = SharedPreferenceUtil.get(this,AppConfig.USERID);
        scanQRCode = findViewById(R.id.scanQRCodeButton);

        createQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(text != null){
                    // Encode the string into a QR code
                    // Need to also use a bitmap inorder to display it properly
                    MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
                    try {
                        BitMatrix bitMatrix = multiFormatWriter.encode(text, BarcodeFormat.QR_CODE,
                                500, 500);
                        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
                        Bitmap bitmap = barcodeEncoder.createBitmap(bitMatrix);
                        qrCodeImageView.setImageBitmap(bitmap);
                    }catch (WriterException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        scanQRCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(PatientGenerateQRCode.this, PatientScanQRCodeActivity.class);
                startActivity(intent);
            }
        });
    }
}

