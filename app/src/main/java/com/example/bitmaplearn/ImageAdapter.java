package com.example.bitmaplearn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ImageViewHolder> {

    private ExecutorService executorService;
    private List<String> imageUrls;
    private Context context;
    private final Set<Integer> downloadedPositions = new HashSet<>();

    public ImageAdapter(Context context, List<String> imageUrls) {
        this.context = context;
        this.imageUrls = imageUrls;
        this.executorService = Executors.newFixedThreadPool(imageUrls.size());
    }

    public class ImageViewHolder extends RecyclerView.ViewHolder {
        private ImageView imageView;
        private Bitmap currentBitmap;

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
        String fileName = "image_" + position + ".jpg";
        File cacheDir = new File(context.getCacheDir(), "cached_images");
        File file = new File(cacheDir, fileName);

        if (file.exists()) {
            if(("loadedFile").equals(holder.imageView.getTag())){
                return;
            }
            Bitmap cachedBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            holder.imageView.setImageBitmap(cachedBitmap);
            holder.currentBitmap = cachedBitmap;
            holder.imageView.setTag("loadedFile");
            Log.d("Decode", "DecodeFile");
            return;
        }

        if (holder.currentBitmap != null && !holder.currentBitmap.isRecycled()) {
            holder.currentBitmap.recycle();
            holder.currentBitmap = null;
        }

        if (downloadedPositions.contains(position)) {
            return;
        }

        downloadedPositions.add(position);
        executorService.submit(() -> {
            Bitmap bitmap = getBitmapFromURL(url);
            File fileBitmap = saveBitmapToFile(context, bitmap, fileName);
            holder.imageView.post(() -> holder.imageView.setImageBitmap(bitmap));
            holder.imageView.setTag("loadedThread" + position);
            Log.d("Decode", "Executor: " + position);
        });
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
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 150, 150, true);
            return scaledBitmap;
        } catch (IOException e) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
        }
    }

    public File saveBitmapToFile(Context context, Bitmap bitmap, String fileName) {
        File directory = new File(context.getCacheDir(), "cached_images");
        File file = new File(directory, fileName);
        FileOutputStream out = null;

        if(!directory.exists()){
            directory.mkdirs();
        }

        try {
            out = new FileOutputStream(file);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            return file;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}


