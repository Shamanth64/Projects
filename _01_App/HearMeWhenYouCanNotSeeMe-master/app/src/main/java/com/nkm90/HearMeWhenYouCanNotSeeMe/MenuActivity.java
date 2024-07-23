package com.nkm90.HearMeWhenYouCanNotSeeMe;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Locale;

public class MenuActivity extends AppCompatActivity {
    private TextToSpeech mTTS;
    public EditText mEditText;
    private SeekBar mSeekBarPitch;
    private SeekBar mSeekBarSpeed;
    private Button mButtonSpeak;
    private static final int REQUEST_CODE = 100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        Button mButtonMP = findViewById(R.id.btn_MP);
        Button mButtonListen = findViewById(R.id.btn_stt);
        mButtonSpeak = findViewById(R.id.btn_tts);
        mEditText = findViewById(R.id.etResult);
        mSeekBarPitch = findViewById(R.id.seek_bar_pitch);
        mSeekBarSpeed = findViewById(R.id.seek_bar_speed);
        Button btnBack1 = findViewById(R.id.btnBack1);
        String actualLanguage = getLanguage();

        btnBack1.setOnClickListener(view -> {
            Intent intent = new Intent(MenuActivity.this, welcome_screen.class);
            startActivity(intent);
        });

        mTTS = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
                if (status == TextToSpeech.SUCCESS){
                    int result = 0;
                    if (actualLanguage.equals("en")){
                        result = mTTS.setLanguage(Locale.forLanguageTag(actualLanguage));
                    } else if (actualLanguage.equals("es")){
                        result = mTTS.setLanguage(Locale.forLanguageTag(actualLanguage));
                    }

                    if(result == TextToSpeech.LANG_MISSING_DATA ||
                            result == TextToSpeech.LANG_NOT_SUPPORTED){
                        Log.e("TTS", "Language not supported");
                    } else{
                        mButtonSpeak.setEnabled(true);
                    }
                } else {
                    Log.e("TTS", "Initialization failed");
                }
            }
        });

        mButtonMP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMP(v, MediaPipeActivity.class);
            }
        });

        mButtonListen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakIn(actualLanguage);
            }
        });

        mButtonSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                speakOut();
            }
        });
    }

    private void speakIn(String language){
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        if (language.equals("en")){
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "en-GB");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to transfer into text");
        }else if (language.equals("es")){
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Habla para convertir en texto");
        }

        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak to transfer into text");

        try {
            startActivityForResult(intent, 2);
        } catch (Exception exception){
            Toast.makeText(this, ""+exception.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public void openMP(View view, Class<MediaPipeActivity> activity) {
        Intent intent = new Intent(this, activity);
        startActivityForResult(intent, 1);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==1){
            String message = data.getStringExtra("MESSAGE");
            assert message != null;
            if (!message.equals("Back")){
                mEditText.setText(message);
            }
        }else if (requestCode==2){
            if (resultCode== RESULT_OK && null!=data){
                ArrayList<String> result = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                mEditText.setText(result.get(0));
            }
        }
    }

    private void speakOut(){
        String text = mEditText.getText().toString();

        float pitch = (float)mSeekBarPitch.getProgress() / 50;
        if (pitch < 0.1) pitch = 0.1f;

        float speed = (float)mSeekBarSpeed.getProgress() / 50;
        if (speed < 0.1) speed =0.1f;

        mTTS.setPitch(pitch);
        mTTS.setSpeechRate(speed);

        mTTS.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }

    public String getLanguage(){
        SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
        return prefs.getString("My_Lang", "");
    }

    @Override
    protected void onStart()
    {
        super.onStart();
        Log.d("ActivityLifeCycle", "Menu Activity - onStart");
    }

    @Override
    protected void onRestart()
    {
        Log.d("ActivityLifeCycle", "Menu Activity - onRestart");
        super.onRestart();
    }

    @Override
    protected void onResume()
    {
        Log.d("ActivityLifeCycle", "Menu Activity - onResume");
        super.onResume();
    }

    @Override
    protected void onPause()
    {
        Log.d("ActivityLifeCycle", "Menu Activity - onPause");
        super.onPause();
    }

    @Override
    protected void onStop()
    {
        Log.d("ActivityLifeCycle", "Menu Activity - onStop");
        super.onStop();
    }
    @Override
    protected void onDestroy() {
        if (mTTS != null){
            mTTS.stop();
            mTTS.shutdown();
        }
        Log.d("ActivityLifeCycle", "Menu Activity - onDestroy");
        super.onDestroy();
    }
}