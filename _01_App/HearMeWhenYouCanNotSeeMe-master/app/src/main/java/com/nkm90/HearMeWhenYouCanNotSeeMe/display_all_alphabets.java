package com.nkm90.HearMeWhenYouCanNotSeeMe;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class display_all_alphabets extends AppCompatActivity {

    private ImageView alphabetImageView;
    private TextView alphabetTextView;
    private TextView descriptionTextView;
    private int currentAlphabetIndex = 0;
    private final char[] alphabets = "abcdefghijklmnopqrstuvwxyz".toCharArray(); // lowercase alphabet images

    // Add descriptions corresponding to each letter
    private final String[] descriptions = {
            "A closed fist, all finger folded against the palm, thumb is straight, alongside the index finger.",
            "All fingers are straight. Thumb is folded across palm.",
            "All fingers partially folded. Thumb is partially folded. Hand is turn slightly to the left so viewer can see backward \"C\" shape formed by thumb and index finger.",
            "Middle, ring and little fingers are partially folded. Tip of thumb is touching tip of middle finger. Index finger is straight. Hand is turned slightly so viewer can see \"d\" shape formed by thumb, middle and index fingers.",
            "Thumb is folded across in front of palm but not touching it. All fingers are partially folded with the tips of index, middle and ring fingers touching the thumb between the knuckle and the tip.",
            "Tip of index finger is touching tip of thumb. Middle, ring and little fingers are straight and slightly spread.",
            " Middle, ring and little fingers are folded down across palm. Thumb is straight but pulled in so that it is in front of the index finger. The index finger is straight and pointing forwards slightly so that it is parallel to the thumb, The thumb and index finger are not touching. The whole hand is turned towards the left and tilted slightly so the thumb and index finger are towards the viewer and pointing up at about 45 degrees.",
            "Ring and little finger are folded down. Thumb is folded over ring and little finger. Index finger and middle finger are straight and together. The hand is tilted over so that the fingers are horizontal and pointing to the left.",
            "Index, middle and ring fingers are folded down. Thumb is folded across index middle and ring fingers. Little finger is straight.",
            "Index, middle and ring fingers are folded down. Thumb is folded across index middle and ring fingers. Little finger is straight. The hand is moved so that little finger draws a \"J\" shape. Motion is a curve moving forward and then right. The hand turns to the right.",
            "Ring and little fingers are folded down. Index and middle finger are straight and slightly spread. Thumb is straight and pointing up to the middle finger. (This is very similar to V the only difference is the position of the thumb.",
            "Middle, ring and little finger are folded down over palm. Index finger and thumb are straight. Thumb is sticking out sideways at 90 degrees to index finger to form \"L\" shape.",
            "Little finger is folded. Thumb is folded across to touch little finger. Index, middle and ring fingers are folded down over thumb.",
            "Little and ring finger are folded. Thumb is folded across ring and little finger. Index finger and middle finger are folded down over thumb.",
            "All fingers are partially folded. Thumb is partially folded and tip of thumb is touching tip of index finger. Hand is turned slightly so viewer can see \"O\" shape formed by thumb and index finger.",
            "Ring and little finger are folded down. Index finger is straight. Middle finger is straight but pointing forward so that is at 90 degrees to index finger. Tip of thumb is touching middle of middle finger. Hand is turned to the left and twisted over so that index finger is horizontal and middle finger is pointing down. Viewer can (sort of) see a \"P\" shape formed by middle finger and thumb.",
            "Ring and little fingers are folded down across palm. Thumb is straight but pulled in so that it is in front of the index finger. The index finger is straight and pointing forwards slightly so that it is parallel to the thumb. The index finger and thumb are not touching. The Middle finger is bent down and across to the right of the thumb (this hurts !). The whole hand is turned towards the left and tilted so the thumb and index finger are towards the viewer and pointing almost straight down.",
            "Ring and little finger are folded against the palm, held down by thumb, index and middle finger are straight and crossed, index finger in front.",
            "Clenched fist. All fingers folded tightly into palm. Thumb is across index and middle fingers.",
            "Middle, ring and little fingers are fold down across palm. Thumb is folded across middle finger. Index finger is folded over thumb.",
            "Ring and little finger are folded against the palm, held down by thumb, index and middle finger are straight and together.",
            "Ring and little finger fold against the palm, held down by thumb, index and middle finger are straight and spread to form a \"V\" shape.",
            "Tip of little finger is touching tip of thumb. Index, middle and ring fingers are straight and slightly spread.",
            "Middle, ring and little fingers are folded down. Index finger is bent at both joints. Thumb is pulled in and slightly bent at the joint. The hand is turned to the left so view can see thumb and index finger.",
            "Index, middle and ring ringers folded against palm. Little finger and thumb are straight and spread wide.",
            "Middle, ring and little fingers are folded. Thumb is folded across middle and ring fingers. Index finger is straight. The hand is moved so that the tip of index finger draws out a \"Z\" shape. The motion is (1) from right to left. (2) from left to right and forward. (3) from right to left."
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.display_all_alphabets);

        alphabetImageView = findViewById(R.id.alphabetImageView);
        alphabetTextView = findViewById(R.id.alphabetTextView);
        descriptionTextView = findViewById(R.id.descriptionTextView);
        Button backButton = findViewById(R.id.backButton);
        Button nextButton = findViewById(R.id.nextButton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"})
        Button homeButton = findViewById(R.id.homeButton);
        homeButton.setOnClickListener(view -> {
            Intent intent = new Intent(this, welcome_screen.class);
            startActivity(intent);
        });

        displayAllAlphabet();

        backButton.setOnClickListener(v -> {
            if (currentAlphabetIndex > 0) {
                currentAlphabetIndex--;
            } else {
                currentAlphabetIndex = alphabets.length - 1;
            }
            displayAllAlphabet();
        });

        nextButton.setOnClickListener(v -> {
            if (currentAlphabetIndex < alphabets.length - 1) {
                currentAlphabetIndex++;
            }else {
                currentAlphabetIndex = 0;
            }
            displayAllAlphabet();
        });
    }
    private void displayAllAlphabet() {
        char currentAlphabet = alphabets[currentAlphabetIndex];
        @SuppressLint("DiscouragedApi") int imageResourceId = getResources().getIdentifier("letter_" + currentAlphabet, "drawable", getPackageName());
        alphabetImageView.setImageResource(imageResourceId);
        alphabetTextView.setText(String.valueOf(currentAlphabet).toUpperCase());
        descriptionTextView.setText(descriptions[currentAlphabetIndex]);
    }
    public void to_display_options() {
        Intent intent = new Intent(this, welcome_screen.class);
        startActivity(intent);
    }
}
