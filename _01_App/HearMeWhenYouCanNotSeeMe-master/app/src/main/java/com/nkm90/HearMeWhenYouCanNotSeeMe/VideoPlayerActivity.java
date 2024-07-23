package com.nkm90.HearMeWhenYouCanNotSeeMe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.VideoView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Locale;

public class VideoPlayerActivity extends AppCompatActivity {

    private String videoResourceId;
    private String textViewContent;
    private VideoView videoView;
    private Button replayButton;
    private Button readButton;
    private TextToSpeech textToSpeech;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_player);

        videoResourceId = getIntent().getStringExtra("video_resource_id");
        textViewContent = getIntent().getStringExtra("selected_item");
        TextView textView = findViewById(R.id.videoDescription);
        textView.setText(textViewContent);

        videoView = findViewById(R.id.videoView);
        replayButton = findViewById(R.id.replayButton);
        readButton = findViewById(R.id.readButton);
        Button backbutton = findViewById(R.id.buttonBack);
        backbutton.setOnClickListener(v -> {
            Intent intent = new Intent(VideoPlayerActivity.this, VideoPlayer.class);
            startActivity(intent);
        });

        replayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Restart the video playback
                videoView.seekTo(0);
                videoView.start();
            }
        });

        readButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Read the text out loud
                speakOut();
            }
        });

        textToSpeech = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status != TextToSpeech.ERROR) {
                    textToSpeech.setLanguage(Locale.US);
                }
            }
        });

        Uri uri = Uri.parse("android.resource://" + getPackageName() + "/" + getResources().getIdentifier(videoResourceId, "raw", getPackageName()));
        videoView.setVideoURI(uri);
        videoView.start();
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void speakOut() {
        String text = textViewContent;
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null, null);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (videoView != null) {
            videoView.stopPlayback();
            videoView = null;
        }
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
    }
}
