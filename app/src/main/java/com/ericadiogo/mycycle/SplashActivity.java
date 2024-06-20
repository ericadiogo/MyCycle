package com.ericadiogo.mycycle;


import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class SplashActivity extends AppCompatActivity {
    VideoView splashScreen;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_splash);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        splashScreen = (VideoView) findViewById(R.id.splashScreen);

        Uri video = Uri.parse("android.resource://" + getPackageName() + "/" + R.raw.splash_screen4);
        splashScreen.setVideoURI(video);

        splashScreen.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
            @Override
            public void onCompletion(MediaPlayer mediaPlayer) {
                startNextActivity();
            }
        });

        splashScreen.start();
    }

    private void startNextActivity(){
        if(isFinishing()){
            return;
        }
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }
}