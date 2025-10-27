package com.example.bitmaplearn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {
    private List<String> imageUrls;
    private Context context;

    public ImageAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;

        public ImageViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageViewItem);
        }
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View itemView = inflater.inflate(R.layout.item, parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(itemView);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        String url = imageUrls.get(position);
        new Thread(() -> {
            Bitmap bitmap = getBitmapFromURL(url);
            holder.imageView.post(() -> holder.imageView.setImageBitmap(bitmap));
        }).start();
    }

    @Override
    public int getItemCount() {
        if(imageUrls != null){
            return imageUrls.size();
        }
        return 0;
    }

    public Bitmap getBitmapFromURL(String src) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(src)
                .build();
        try(Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            InputStream input = response.body().byteStream();
            return BitmapFactory.decodeStream(input);
        } catch (IOException e) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
        }
    }
}


