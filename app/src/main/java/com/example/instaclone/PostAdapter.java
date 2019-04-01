package com.example.instaclone;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.parse.ParseFile;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.ViewHolder>
{
    private Context context;
    private List<Post> post;

    public PostAdapter(Context context, List<Post> post) {
        this.context = context;
        this.post = post;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i)
    {
        View view = LayoutInflater.from(context).inflate(R.layout.item_post,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        Post posts = post.get(i);
        viewHolder.bind(posts);
    }

    @Override
    public int getItemCount() {
        return post.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder
    {
        private TextView tvHandle;
        private TextView tvDescription;
        private ImageView ivImage;

        public ViewHolder(View itemView)
        {
            super(itemView);
            tvHandle = itemView.findViewById(R.id.tvHandle);
            ivImage = itemView.findViewById(R.id.ivImage);
            tvDescription = itemView.findViewById(R.id.tvDescription);
        }

        public void bind(Post post)
        {
            tvHandle.setText(post.getUser().getUsername());
            ParseFile image = post.getImage();
            if(image != null)
            {
                Glide.with(context).load(image.getUrl().replace("http","https")).into(ivImage);
            }
            tvDescription.setText(post.getDescription());
        }
    }
}
