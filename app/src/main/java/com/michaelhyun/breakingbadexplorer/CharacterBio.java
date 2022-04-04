package com.michaelhyun.breakingbadexplorer;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.michaelhyun.breakingbadexplorer.model.Character;

public class CharacterBio extends AppCompatActivity {
    ImageView characterImage;
    TextView characterBio;

    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_character_bio);
        getIncomingIntent();

    }

    private void getIncomingIntent() {
        if(getIntent().hasExtra("clicked_character")) {
            characterImage = findViewById(R.id.iv_imageBio);
            characterBio = findViewById(R.id.tv_bio);

            Character character = (Character) getIntent().getSerializableExtra("clicked_character");

            Glide.with(CharacterBio.this)
                    .load(character.getImg())
                    .apply(RequestOptions.centerCropTransform())
                    .into(characterImage);

            characterBio.setText(character.getName() + "\n" +
                    character.getOccupation() + "\n" +
                    character.getStatus() + "\n" +
                    character.getNickname() + "\n" +
                    character.getAppearance());
        }
    }
}