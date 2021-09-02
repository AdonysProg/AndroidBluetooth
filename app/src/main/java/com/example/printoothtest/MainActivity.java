package com.example.printoothtest;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.ui.ScanningActivity;
import com.mazenrashed.printooth.utilities.Printing;


public class MainActivity extends AppCompatActivity {

    Printing printing;
    Button btn_unpair_pair, btn_print;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView(){
        btn_print = (Button)findViewById(R.id.btnPrint);
        btn_unpair_pair = (Button)findViewById(R.id.btnPairUnpair);

        if(!Printooth.INSTANCE.hasPairedPrinter())
            startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);

        // Event
        btn_unpair_pair.setOnClickListener(v -> {
            if(Printooth.INSTANCE.hasPairedPrinter())
                Printooth.INSTANCE.removeCurrentPrinter();
            else{
                startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
                changePairAndUnpair();
            }
        });
        
        btn_print.setOnClickListener(v -> {
         if(!Printooth.INSTANCE.hasPairedPrinter())
             startActivityForResult(new Intent(MainActivity.this, ScanningActivity.class), ScanningActivity.SCANNING_FOR_PRINTER);
         else{
             PrintoothService ps = new PrintoothService();
             ps.initPrinting();
             ps.printText();
         }
        });
        changePairAndUnpair();
    }



    private void changePairAndUnpair() {
        if(Printooth.INSTANCE.hasPairedPrinter())
            btn_unpair_pair.setText(new StringBuilder("Unpair ").append(Printooth.INSTANCE.getPairedPrinter().getAddress()));
        else
            btn_unpair_pair.setText("Pair with Printer");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == ScanningActivity.SCANNING_FOR_PRINTER && resultCode == Activity.RESULT_OK)
            initPrinting();
        changePairAndUnpair();
    }

    private void initPrinting() {
        if(Printooth.INSTANCE.hasPairedPrinter())
            printing = Printooth.INSTANCE.printer();

    }
}