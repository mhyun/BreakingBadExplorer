package com.michaelhyun.breakingbadexplorer;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.michaelhyun.breakingbadexplorer.adapters.CharactersAdapter;
import com.michaelhyun.breakingbadexplorer.model.Character;
import com.michaelhyun.breakingbadexplorer.repositories.BreakingBadService;
import com.michaelhyun.breakingbadexplorer.repositories.BreakingBadRepo;
import com.michaelhyun.breakingbadexplorer.viewmodels.BreakingBadViewModel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.function.UnaryOperator;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements CharactersAdapter.ItemClickListener{

    private BreakingBadViewModel breakingBadViewModel;
    private List<Character> characterList;
    RecyclerView recyclerView;
    TextView noText;
    SearchView searchView;
    CharactersAdapter charactersAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_characters);
        noText = findViewById(R.id.tv_noText);
        searchView = findViewById(R.id.sv_search);
        LinearLayoutManager layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        charactersAdapter = new CharactersAdapter(this, characterList, this);
        recyclerView.setAdapter(charactersAdapter);
        ArrayList<Character> characterArrayList;

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

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filter(newText);
                return false;
            }
        });
    }

    private void filter(String text) {
        List<Character> filteredList = new ArrayList<>();
        for (Character item : characterList) {
            if (item.getName().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            charactersAdapter.filterList(filteredList);
        }
    }

    @Override
    public void onCharacterClick(Character character) {
    }
}