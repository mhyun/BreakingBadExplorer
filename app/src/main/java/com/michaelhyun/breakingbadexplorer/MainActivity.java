package com.michaelhyun.breakingbadexplorer;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.michaelhyun.breakingbadexplorer.adapters.CharactersAdapter;
import com.michaelhyun.breakingbadexplorer.model.Character;
import com.michaelhyun.breakingbadexplorer.viewmodels.BreakingBadViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements CharactersAdapter.ItemClickListener, SeasonDialog.SeasonDialogListener {

    private BreakingBadViewModel breakingBadViewModel;
    private List<Character> characterList;
    RecyclerView recyclerView;
    TextView noText;
    SearchView searchView;
    CharactersAdapter charactersAdapter;
    ImageButton filterSeasonButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.rv_characters);
        noText = findViewById(R.id.tv_noText);
        searchView = findViewById(R.id.sv_search);
        filterSeasonButton = findViewById(R.id.btn_filter);
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
                search(newText);
                return false;
            }
        });

        filterSeasonButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openDialog();
            }
        });
    }

    private void search(String text) {
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

    public void openDialog() {
        SeasonDialog seasonDialog = new SeasonDialog();
        seasonDialog.show(getSupportFragmentManager(), "Season Dialog");
    }

    @Override
    public void onCharacterClick(Character character) {
    }

    @Override
    public void applyFilter(List<Integer> seasonsList) {
        List<Character> filteredList = new ArrayList<>();
        Boolean toAdd = true;
        for (Character item : characterList) {
            for(int i = 0; i < seasonsList.size(); i++) {
                if(!item.getAppearance().contains(seasonsList.get(i))){
                    toAdd = false;
                }
            }
            if(toAdd == true) {
                filteredList.add(item);
            }
            toAdd = true;
        }
        if (filteredList.isEmpty()) {
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show();
        } else {
            charactersAdapter.filterList(filteredList);
        }
    }
}