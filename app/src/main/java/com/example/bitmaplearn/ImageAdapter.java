package com.example.bitmaplearn;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

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
        this.executorService = Executors.newFixedThreadPool(3);
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
        File cacheDir = new File(context.getCacheDir(), "images");
        File file = new File(cacheDir, fileName);

        holder.imageView.setImageResource(R.drawable.ic_launcher_background);

        holder.imageView.setOnClickListener(v -> {
            Toast.makeText(context, "Image at position " + position + " clicked.", Toast.LENGTH_SHORT).show();
        });

        if (file.exists()) {
            Bitmap cachedBitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
            holder.imageView.setImageBitmap(cachedBitmap);
            holder.currentBitmap = cachedBitmap;
            Log.d("Decode", "DecodeFile");
            return;
        }

        if (downloadedPositions.contains(position)) {
            holder.imageView.setImageResource(R.drawable.ic_launcher_background);
            return;
        }

        downloadedPositions.add(position);
        executorService.submit(() -> {
            Bitmap bitmap = getBitmapFromURL(url);
            File fileBitmap = saveBitmapToFile(bitmap, fileName, cacheDir);
            holder.imageView.post(() -> holder.imageView.setImageBitmap(bitmap));
            Log.d("Decode", "Executor: " + position);
        });
    }

    @Override
    public int getItemCount() {
        if (imageUrls != null) {
            return imageUrls.size();
        }
        return 0;
    }

    public Bitmap getBitmapFromURL(String src) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url(src)
                .build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful()) {
                throw new IOException("Unexpected code " + response);
            }
            InputStream input = response.body().byteStream();
            Bitmap bitmap = BitmapFactory.decodeStream(input);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(bitmap, 250, 250, true);
            return scaledBitmap;
        } catch (IOException e) {
            return BitmapFactory.decodeResource(context.getResources(), R.drawable.ic_launcher_background);
        }
    }

    public File saveBitmapToFile(Bitmap bitmap, String fileName, File directory) {
        File file = new File(directory, fileName);
        FileOutputStream out = null;

        if (!directory.exists()) {
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

    public static void deleteDir(File dir) {
        if (dir != null && dir.exists()) {
            File[] files = dir.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDir(file);
                    } else {
                        file.delete();
                    }
                }
            }
            dir.delete();
            Log.d("Cache", "Đã xóa: " + dir.getAbsolutePath());
        }
    }

    public void clearAllData() {
        File cacheDir = new File(context.getCacheDir(), "images");
        deleteDir(cacheDir);
        downloadedPositions.clear();
        notifyDataSetChanged();
        Toast.makeText(context, "Đã xóa cache", Toast.LENGTH_SHORT).show();
    }


}


