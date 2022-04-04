package com.michaelhyun.breakingbadexplorer.repositories;

import com.michaelhyun.breakingbadexplorer.model.Character;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface BreakingBadService {
    @GET("api/characters")
    Call<List<Character>> getCharacters();
}
