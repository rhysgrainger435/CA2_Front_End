package com.example.firstapp;

import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.os.Bundle;
import retrofit2.converter.gson.GsonConverterFactory;
import com.example.firstapp.Artist;
import com.example.firstapp.Song;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.Retrofit;
import com.example.firstapp.MusicServiceApi;
import android.widget.Spinner;
import retrofit2.Callback;
import retrofit2.Response;


public class ApiActivity extends AppCompatActivity implements View.OnClickListener{

    private TextView responseTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);

        responseTextView = findViewById(R.id.responseText);
        Button callApiButton = findViewById(R.id.callAPI);

        callApiButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        // TODO: Call the API and display the response in TextView
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.example.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        MusicServiceApi service = retrofit.create(MusicServiceApi.class);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);

        String endpoint;
        String selection = spinner.getSelectedItem().toString();

        if (selection.equals("Artists")) {
            endpoint = "artists";
        } else {
            endpoint = "songs";
        }

        Call<List<?>> call;
        if (endpoint.equals("artists")) {
            call = (Call<List<?>>) (Call<?>) service.getArtists();
        } else {
            call = (Call<List<?>>) (Call<?>) service.getSongs();
        }

        call.enqueue(new Callback<List<?>>() {
            @Override
            public void onResponse(Call<List<?>> call, Response<List<?>> response) {
                if (response.isSuccessful()) {
                    List<?> items = response.body();
                    String responseText = "";
                    if (endpoint.equals("artists")) {
                        for (Object item : items) {
                            Artist artist = (Artist) item;
                            responseText += artist.getName() + "\n";
                        }
                    } else {
                        for (Object item : items) {
                            Song song = (Song) item;
                            responseText += song.getName() + "\n";
                        }
                    }
                    responseTextView.setText(responseText);
                } else {
                    responseTextView.setText("Failed to get " + endpoint);
                }
            }


            @Override
            public void onFailure(Call<List<?>> call, Throwable t) {
                responseTextView.setText("Failed to get " + endpoint);
            }
        });
    }

}