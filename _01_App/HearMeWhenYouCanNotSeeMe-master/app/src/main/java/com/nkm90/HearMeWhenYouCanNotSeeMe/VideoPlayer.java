package com.nkm90.HearMeWhenYouCanNotSeeMe;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class VideoPlayer extends AppCompatActivity {

    private ListView itemListView;
    private EditText searchEditText;
    private ArrayAdapter<String> adapter;
    private List<String> itemList;
    private List<String> filteredItemList;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video);

        String[] items = {
                "can i offer anything to eat",
                "come here",
                "come",
                "did you eat",
                "eat",
                "go",
                "hi",
                "how",
                "how are you",
                "how do you sign",
                "how much",
                "how far is it",
                "how old are you",
                "my name is",
                "name",
                "what is your name",
                "where did you come from",
                "which",
                "bless you",
                "food",
                "food is delicious",
                "I",
                "i have not seen him from ages",
                "i love you",
                "i see",
                "i understand",
                "i am sorry",
                "my",
                "my god",
                "my goodness",
                "my head aches",
                "rest",
                "thank you",
                "time",
                "which",
                "who took my",
                "you"
        };
        itemList = new ArrayList<>(Arrays.asList(items));
        filteredItemList = new ArrayList<>(itemList);

        itemListView = findViewById(R.id.itemListView);
        searchEditText = findViewById(R.id.searchEditText);
        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, filteredItemList);
        itemListView.setAdapter(adapter);
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filterItems(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {}
        });

        itemListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String selectedItem = filteredItemList.get(position);
                playVideo(selectedItem);
            }
        });
    }

    private void filterItems(String query) {
        filteredItemList.clear();
        for (String item : itemList) {
            if (item.toLowerCase().contains(query.toLowerCase())) {
                filteredItemList.add(item);
            }
        }
        adapter.notifyDataSetChanged();
    }

    private void playVideo(String selectedItem) {
        String videoResourceId = selectedItem.replaceAll("\\s+", "_").toLowerCase();
        intent = new Intent(VideoPlayer.this, VideoPlayerActivity.class);
        intent.putExtra("video_resource_id", videoResourceId);
        intent.putExtra("selected_item", selectedItem);
        startActivity(intent);
    }
}
