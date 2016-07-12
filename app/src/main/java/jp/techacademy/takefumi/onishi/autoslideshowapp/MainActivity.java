package jp.techacademy.takefumi.onishi.autoslideshowapp;

import android.Manifest;
import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private static final int PERMISSIONS_REQUEST_CODE = 100;
    private static final int DELAY_TIME = 2000;
    ArrayList<Uri> imageUris = new ArrayList<>();
    int pages = 1;
    boolean onOff = true;
    Handler _handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonBack = (Button) findViewById(R.id.buttonback);
        if (permissionCheck()) {
            slideCreate();
            ImageView imageView = (ImageView) findViewById(R.id.image);
            imageView.setImageURI(imageUris.get(pages));
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
        final Button buttonPlay = (Button) findViewById(R.id.buttonplay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId() == R.id.buttonplay) {
                    if (permissionCheck()) {
                        if (onOff) {
                            buttonPlay.setText(R.string.stop);
                            viewPlay();
                        } else {
                            buttonPlay.setText(R.string.start);
                            _handler.removeCallbacksAndMessages(null);
                        }
                        onOff = !onOff;
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
        if (!onOff) {
            Toast toast = Toast.makeText(MainActivity.this, "スライドショー中はこの操作は無効です", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        --pages;
        if (pages == 0) {
            pages = imageUris.size() - 1;
        }

//        Toast toast = Toast.makeText(MainActivity.this, pages + "枚目", Toast.LENGTH_SHORT);
//        toast.show();
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageURI(imageUris.get(pages));

    }

    public void viewNext() {
        if (!onOff) {
            Toast toast = Toast.makeText(MainActivity.this, "スライドショー中はこの操作は無効です", Toast.LENGTH_SHORT);
            toast.show();
            return;
        }
        ++pages;
        if (pages == imageUris.size()) {
            pages = 1;
        }
//        Toast toast = Toast.makeText(MainActivity.this, pages + "枚目", Toast.LENGTH_SHORT);
//        toast.show();
        ImageView imageView = (ImageView) findViewById(R.id.image);
        imageView.setImageURI(imageUris.get(pages));

    }

    public void viewPlay() {

        _handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ++pages;
                if (pages == imageUris.size()) {
                    pages = 1;
                }
//                Toast toast = Toast.makeText(MainActivity.this, pages + "枚目", Toast.LENGTH_SHORT);
//                toast.show();
                ImageView imageView = (ImageView) findViewById(R.id.image);
                imageView.setImageURI(imageUris.get(pages));

                _handler.postDelayed(this, DELAY_TIME);
            }
        }, DELAY_TIME);
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
