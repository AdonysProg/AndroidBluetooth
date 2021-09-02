package com.example.printoothtest;

import android.widget.Toast;

import com.mazenrashed.printooth.Printooth;
import com.mazenrashed.printooth.data.printable.Printable;
import com.mazenrashed.printooth.data.printable.RawPrintable;
import com.mazenrashed.printooth.data.printable.TextPrintable;
import com.mazenrashed.printooth.data.printer.DefaultPrinter;
import com.mazenrashed.printooth.utilities.Printing;
import com.mazenrashed.printooth.utilities.PrintingCallback;

import java.util.ArrayList;

public class PrintoothService implements PrintingCallback {

    Printing printing;

    @Override
    public void connectingWithPrinter() {
        System.out.println("Connecting to printer");
    }

    @Override
    public void connectionFailed(String s) {
    }

    @Override
    public void onError(String s) {
    }

    @Override
    public void onMessage(String s) {
    }

    @Override
    public void printingOrderSentSuccessfully() {
    }

    public void initPrinting() {
        if(Printooth.INSTANCE.hasPairedPrinter())
            printing = Printooth.INSTANCE.printer();
        if(printing != null)
            printing.setPrintingCallback(this);
    }


    public void printText() {
        ArrayList<Printable> printables = new ArrayList<>();
        printables.add(new RawPrintable.Builder(new byte[]{27,100,4}).build());

        printables.add(new TextPrintable.Builder().setText("Factura testing").setCharacterCode(DefaultPrinter.Companion.getCHARCODE_PC1252()).
                setNewLinesAfter(1).build());

        printables.add(new TextPrintable.Builder()
                .setText("Factura").setLineSpacing(DefaultPrinter.Companion.getLINE_SPACING_60())
                .setAlignment(DefaultPrinter.Companion.getALIGNMENT_CENTER())
                .setEmphasizedMode(DefaultPrinter.Companion.getEMPHASIZED_MODE_BOLD())
                .setUnderlined(DefaultPrinter.Companion.getUNDERLINED_MODE_ON())
                .setNewLinesAfter(1).build());

        printing.print(printables);
    }

}
