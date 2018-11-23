package com.example.jerry.healemgood.view.UserViews;

import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.example.jerry.healemgood.R;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.journeyapps.barcodescanner.BarcodeEncoder;

public class PatientQRCode extends AppCompatActivity {

    private Button createQRCodeButton;
    private ImageView qrCodeImageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_qrcode);

        // Get the xml elements on the UI
        createQRCodeButton = findViewById(R.id.generateCodeButton);
        qrCodeImageView = findViewById(R.id.qrCodeImageView);

        createQRCodeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String text = "Hello";

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
    }
}

