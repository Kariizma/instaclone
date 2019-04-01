package com.example.instaclone.fragments;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.instaclone.Post;
import com.example.instaclone.PostAdapter;
import com.example.instaclone.R;
import com.parse.FindCallback;
import com.parse.ParseException;
import com.parse.ParseQuery;

import java.util.ArrayList;
import java.util.List;

import static com.example.instaclone.Post.KEY_CREATED_AT;

public class PostFragment extends Fragment
{
    private final String TAG = "Post Fragment";
    private RecyclerView rvPost;
    protected PostAdapter adapter;
    protected List<Post> mPosts;

    SwipeRefreshLayout swipeRefreshLayout;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState)
    {
        return inflater.inflate(R.layout.fragment_post,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState)
    {
        rvPost = view.findViewById(R.id.rvPost);
        mPosts = new ArrayList<>();
        adapter = new PostAdapter(getContext(),mPosts);
        rvPost.setAdapter(adapter);
        rvPost.setLayoutManager(new LinearLayoutManager(getContext()));
        queryPost();
    }

    protected void queryPost()
    {
        ParseQuery<Post> postQuery = new ParseQuery<Post>(Post.class);
        postQuery.include(Post.KEY_USER);
        postQuery.setLimit(20);
        postQuery.addDescendingOrder(Post.KEY_CREATED_AT);
        postQuery.findInBackground(new FindCallback<Post>() {
            @Override
            public void done(List<Post> objects, ParseException e)
            {
                if(e != null)
                {
                    Log.d(TAG,"Error in queryPost");
                    e.printStackTrace();
                    return;
                }
                mPosts.addAll(objects);
                adapter.notifyDataSetChanged();
            }
        });
    }
}
