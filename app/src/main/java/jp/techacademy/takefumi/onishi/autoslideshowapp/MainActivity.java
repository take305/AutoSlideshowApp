package jp.techacademy.takefumi.onishi.autoslideshowapp;

import android.Manifest;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;


public class MainActivity extends AppCompatActivity {
    private static int PERMISSION_REQUEST_CODE =100;
    ImageView mImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button buttonBack = (Button) findViewById(R.id.buttonback);
        /*まずバージョンチェックから*/
        buttonBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId()==R.id.buttonback){
                    if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.M){
                        /*次にパーミッションチェック*/
                        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)==)
                     /*許可あり*/
                    }else{
                        /*許可なし*/
                    }
                }
                viewBack();
            }
        });
        Button buttonPlay = (Button) findViewById(R.id.buttonplay);
        buttonPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId()==R.id.buttonplay){

                }
                viewPlay();
            }
        });
        Button buttonNext = (Button) findViewById(R.id.buttonnext);
        buttonNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (view.getId()==R.id.buttonnext){

                }
            }
        });
    }

    public void viewBack() {

    }

    public void viewNext() {

    }

    public void viewPlay() {

    }
}
