package jp.techacademy.takefumi.onishi.autoslideshowapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    ArrayList<Uri> imageUris = new ArrayList<>();
    int pages=1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonBack = (Button) findViewById(R.id.buttonback);
        if (permissionCheck()){
            slideCreate();
        }

        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.buttonback) {

                    if (permissionCheck()) {
                        viewBack();
                    }
                }
            }
        });
        Button buttonPlay = (Button) findViewById(R.id.buttonplay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.buttonplay) {
                    if (permissionCheck()) {
                        viewPlay();
                    }
                }
            }
        });
        Button buttonNext = (Button) findViewById(R.id.buttonnext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.buttonnext) {
                    if (permissionCheck()) {
                        viewNext();
                    }
                }
            }
        });
    }

    public void viewBack() {



        Toast toast = Toast.makeText(MainActivity.this,pages+"枚目", Toast.LENGTH_SHORT);
        toast.show();
        ImageView imageView=(ImageView)findViewById(R.id.image);
        imageView.setImageURI(imageUris.get(pages));

        --pages;
        if (pages==0){
            pages=imageUris.size()-1;
        }

    }

    public void viewNext() {

        Toast toast = Toast.makeText(MainActivity.this,pages+"枚目", Toast.LENGTH_SHORT);
        toast.show();
        ImageView imageView=(ImageView)findViewById(R.id.image);
        imageView.setImageURI(imageUris.get(pages));

        ++pages;
        if (pages==imageUris.size()){
            pages=1;
        }

    }

    public void viewPlay() {


        --pages;
        if (pages==0){
            pages=imageUris.size();
        }
        Toast toast = Toast.makeText(MainActivity.this, "viewPlay"+pages, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void slideCreate() {
        ContentResolver contentResolver = getContentResolver();
        Cursor cursor = contentResolver.query(
                MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                null,
                null,
                null,
                null
        );
        if (cursor.moveToFirst()) {
            int i = 0;
            do {
                int fieldIndex = cursor.getColumnIndex(MediaStore.Images.Media._ID);
                Long id = cursor.getLong(fieldIndex);
                imageUris.add(ContentUris.withAppendedId(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, id));

//                ImageView imageView = (ImageView) findViewById(R.id.image);
//                imageView.setImageURI(imageUri);
            } while (cursor.moveToNext());
        }
        cursor.close();

    }


    private Boolean permissionCheck() {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            /*次にパーミッションチェック*/
            if (checkSelfPermission
                    (Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                return true;
            } else {
                requestPermissions(new String[]
                        {Manifest.permission.READ_EXTERNAL_STORAGE}, PERMISSIONS_REQUEST_CODE);
            /*許可なし*/
                if (checkSelfPermission
                        (Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_CODE:
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            /*許可*/
                }
                break;
            default:
                break;
        }
    }


}
