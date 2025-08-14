package com.example.homelibrary;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.mlkit.vision.barcode.common.Barcode;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanner;
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions;
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        GmsBarcodeScannerOptions options = new GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(
                        Barcode.FORMAT_EAN_13)
                .build();
        GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(this);
// Or with a configured options
// GmsBarcodeScanner scanner = GmsBarcodeScanning.getClient(context, options);
        scanner
                .startScan()
                .addOnSuccessListener(
                        barcode -> {
                            // Task completed successfully
                        })
                .addOnCanceledListener(
                        () -> {
                            // Task canceled
                        })
                .addOnFailureListener(
                        e -> {
                            // Task failed with an exception
                        });

        GoogleBooksApiRequestExecutor executor = new GoogleBooksApiRequestExecutor();
        GoogleBooksApiRequestRunnable task = new GoogleBooksApiRequestRunnable("isbn", "9780306406157");
//        executor.execute(task);
    }

}