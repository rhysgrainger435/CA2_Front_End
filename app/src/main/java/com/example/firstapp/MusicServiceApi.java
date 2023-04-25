package com.example.firstapp;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MusicServiceApi {
    @GET("/songs")
    Call<List<Song>> getSongs();

    @GET("/artists")
    Call<List<Artist>> getArtists();
}
