package com.example.rssreader;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;
import com.example.rssreader.rss.Feed;
import com.example.rssreader.rss.Item;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.ViewHolder> {

    private final LayoutInflater inflater;
    private final List<Item> items;
    private Context context;
    int selectedPosition=-1;
    public NewsAdapter(Context context, List<Item> items) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.items = items;
    }

    @NonNull
    @NotNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.list_item, parent, false);
        return new ViewHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onBindViewHolder(@NonNull @NotNull ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        Item item = items.get(position);
        holder.nameView.setText(item.getDescription());

        holder.dateView.setText(item.getPubDate().replaceFirst("[+][0-9]*",""));

        holder.authorView.setText(item.getAuthor());
//        holder.capitalView.setText(item.getDescription());

        if(selectedPosition==position)
            holder.itemView.setBackgroundColor(Color.parseColor("#000000"));
        else
            holder.itemView.setBackgroundColor(Color.parseColor("#ffffff"));

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putString("link", items.get(position).getLink());
            Intent intent = new Intent(context, RssActivity.class);
            intent.putExtras(bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView nameView, dateView, authorView;

        ViewHolder(View view){
            super(view);
            nameView = view.findViewById(R.id.name);
            dateView = view.findViewById(R.id.feedDate);
            authorView = view.findViewById(R.id.feedAuthor);

        }

    }
}
