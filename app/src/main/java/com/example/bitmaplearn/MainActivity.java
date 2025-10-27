package com.example.bitmaplearn;

import android.os.Bundle;
import android.widget.ImageView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ImageAdapter adapter;
    private List<String> imageUrls = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/1.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/2.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/3.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/4.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/5.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/6.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/7.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/8.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/9.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/10.jpg");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageAdapter(this, imageUrls);
        recyclerView.setAdapter(adapter);

    }


}