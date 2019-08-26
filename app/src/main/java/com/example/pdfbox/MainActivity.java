package com.example.pdfbox;

import android.Manifest;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.tom_roush.pdfbox.pdmodel.PDDocument;
import com.tom_roush.pdfbox.pdmodel.PDDocumentCatalog;
import com.tom_roush.pdfbox.pdmodel.interactive.form.PDAcroForm;
import com.tom_roush.pdfbox.pdmodel.interactive.form.PDTextField;
import com.tom_roush.pdfbox.util.PDFBoxResourceLoader;

import java.io.File;
import java.io.IOException;

import static android.Manifest.permission.READ_EXTERNAL_STORAGE;

public class MainActivity extends AppCompatActivity {

    File root;

    Bitmap pageImage;
    TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        PDFBoxResourceLoader.init(getApplicationContext());

        setup();


        ////////////////////////////////////////////////////////////////////////////////////////////


        int MY_PERMISSIONS_REQUEST_READ_CONTACTS=0;
// Here, thisActivity is the current activity
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.READ_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                    Manifest.permission.READ_EXTERNAL_STORAGE)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        MY_PERMISSIONS_REQUEST_READ_CONTACTS);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }


        ////////////////////////////////////////////////////////////////////////////////////////////


        try {

// Load the document and get the AcroForm
            AssetManager assetManager = getAssets();

            PDDocument document = PDDocument.load(assetManager.open("minor.pdf"));
            System.out.println("downloaded marker ????????????????????????");
            PDDocumentCatalog docCatalog = document.getDocumentCatalog();
            PDAcroForm acroForm = docCatalog.getAcroForm();
// Fill the text field
            PDTextField field = (PDTextField) acroForm.getField("Description_of_works");
            field.setValue("Filled Text Field");
// Optional: don't allow this field to be edited
            field.setReadOnly(true);
            //PDField checkbox = acroForm.getField("Checkbox");
            //((PDCheckbox) checkbox).check();  /////??????
            //PDField radio = acroForm.getField("Radio");
            //((PDRadioButton)radio).setValue("Second");
            //PDField listbox = acroForm.getField("ListBox");
            //List<Integer> listValues = new ArrayList<>();
            //listValues.add(1);
            //listValues.add(2);
            //((PDListBox) listbox).setSelectedOptionsIndex(listValues);
            //PDField dropdown = acroForm.getField("Dropdown");
            //((PDComboBox) dropdown).setValue("Hello");


            System.out.println(root.getAbsolutePath());
            String path = root.getAbsolutePath() + "/FilledForm.pdf";
          //  tv.setText("Saved filled form to " + path);
            System.out.println(path);
            document.save(path);
            document.close();
            System.out.println("downloaded marker 222222222222222222222222");
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void setup () {
// Enable Android-style asset loading (highly recommended)
        PDFBoxResourceLoader.init(getApplicationContext());
// Find the root of the external storage.
        root = android.os.Environment.getExternalStorageDirectory();
        AssetManager assetManager;
        assetManager = getAssets();
       //tv = (TextView) findViewById(R.id.statusTextView);
    }

}
