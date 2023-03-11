package com.example.rssreader;

import android.os.Handler;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rssreader.rss.Feed;
import com.example.rssreader.rss.Item;
import com.facebook.shimmer.ShimmerFrameLayout;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ApplicationActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_application);
        recyclerView = findViewById(R.id.recyclerView);
        shimmer = findViewById(R.id.loadingg);
        shimmer.setVisibility(View.VISIBLE);
        shimmer.startShimmerAnimation();
        recyclerView.setVisibility(View.INVISIBLE);
        Handler handler = new Handler();

        ExecutorService executorService = Executors.newSingleThreadExecutor();
        Future<List<Item>> items = executorService.submit(this::startPerform);

        handler.postDelayed(()->{
            shimmer.stopShimmerAnimation();
            shimmer.setVisibility(View.INVISIBLE);
            recyclerView.setVisibility(View.VISIBLE);
            try {
                recyclerView.setAdapter(new NewsAdapter(this, items.get()));
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
            }
        }, 3000);

    }

    public List<Item> startPerform() {
        List<Item> items = new ArrayList<>();
        DataFormatter dataFormatter = new DataFormatter();
        String s = loadData("https://rss.nytimes.com/services/xml/rss/nyt/World.xml");
        if (dataFormatter.format(s)) {
            items = dataFormatter.getItems();
        } else {
            System.out.println("failed...");
        }
       return items;
    }

    public String loadData(String address) {
        String response = "";
        while (true) {
            try {
                URL url = new URL(address);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                try (BufferedReader bufferedReader =
                             new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
                    StringBuilder sb = new StringBuilder();
                    String line;
                    while ((line = bufferedReader.readLine()) != null) {
                        sb.append(line);
                    }
                    response = sb.toString();
                }
                return response;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}