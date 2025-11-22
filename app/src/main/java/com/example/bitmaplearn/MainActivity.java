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
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/11.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/12.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/13.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/14.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/15.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/16.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/17.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/18.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/19.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/20.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/21.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/22.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/23.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/24.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/25.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/26.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/27.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/28.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/29.jpg");
        imageUrls.add("https://vltrfdvwnuioloivsfvh.supabase.co/storage/v1/object/public/Image/Images/30.jpg");

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ImageAdapter(this, imageUrls);
        recyclerView.setAdapter(adapter);

    }


}