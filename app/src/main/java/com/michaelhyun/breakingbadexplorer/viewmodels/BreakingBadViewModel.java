package com.michaelhyun.breakingbadexplorer.viewmodels;

import android.widget.TextView;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.michaelhyun.breakingbadexplorer.model.Character;
import com.michaelhyun.breakingbadexplorer.repositories.BreakingBadRepo;
import com.michaelhyun.breakingbadexplorer.repositories.BreakingBadService;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BreakingBadViewModel extends ViewModel {

    private MutableLiveData<List<Character>> characters;

    public BreakingBadViewModel() {
        characters = new MutableLiveData<>();
    }

    public MutableLiveData<List<Character>> getCharactersObserver() {
        return characters;
    }

    public void makeAPICall() {
        BreakingBadService breakingBadService = BreakingBadRepo.getRetrofitClient().create(BreakingBadService.class);
        Call<List<Character>> call = breakingBadService.getCharacters();
        call.enqueue(new Callback<List<Character>>() {
            @Override
            public void onResponse(Call<List<Character>> call, Response<List<Character>> response) {
                characters.postValue(response.body());
            }

            @Override
            public void onFailure(Call<List<Character>> call, Throwable t) {
                characters.postValue(null);
            }
        });
    }
}
