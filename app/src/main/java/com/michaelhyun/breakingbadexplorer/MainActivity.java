package com.michaelhyun.breakingbadexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.michaelhyun.breakingbadexplorer.adapters.CharactersAdapter;
import com.michaelhyun.breakingbadexplorer.model.Character;
import com.michaelhyun.breakingbadexplorer.repositories.BreakingBadService;
import com.michaelhyun.breakingbadexplorer.repositories.BreakingBadRepo;
import com.michaelhyun.breakingbadexplorer.viewmodels.BreakingBadViewModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CharactersAdapter.ItemClickListener{

    private BreakingBadViewModel breakingBadViewModel;
    private List<Character> characterList;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.rv_characters);
        TextView noText = findViewById(R.id.tv_noText);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        CharactersAdapter charactersAdapter = new CharactersAdapter(this, characterList, this);
        recyclerView.setAdapter(charactersAdapter);

        breakingBadViewModel = ViewModelProviders.of(this).get(BreakingBadViewModel.class);
        breakingBadViewModel.getCharactersObserver().observe(this, new Observer<List<Character>>() {
            @Override
            public void onChanged(List<Character> characters) {
                if(characters != null) {
                    characterList = characters;
                    charactersAdapter.setCharacterList(characters);
                    noText.setVisibility(View.GONE);
                }
                else {
                    noText.setVisibility(View.VISIBLE);
                }
            }
        });
        breakingBadViewModel.makeAPICall();
    }

    @Override
    public void onCharacterClick(Character character) {

        Toast.makeText(this, character.getName(), Toast.LENGTH_SHORT).show();
    }
}