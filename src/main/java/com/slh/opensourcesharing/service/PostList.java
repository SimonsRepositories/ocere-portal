package com.slh.opensourcesharing.service;

import com.slh.opensourcesharing.model.Post;
import org.springframework.stereotype.Service;
import com.slh.opensourcesharing.model.User;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class PostList
{
    public List<Post> posts = new ArrayList<>(Arrays.asList(
            new Post(1, "Simon Huster", "This is my code"),
            new Post(2, "Sascha Huber", "tutorial, how to...")
    ));

    public List<Post> getAllPosts()
    {
        if (posts.isEmpty()) {
            return null;
        }
        return posts;
    }

    public void addPost(Post value) {
        posts.add(value);
    }

    public Post getPost(long id) {
        Post returnPost = null;
        for(Post value: posts) {
            if(value.getId() == id) {
                returnPost = value;
                break;
            }
        }
        return returnPost;
    }

    public void removePost(long id)
    {
        for(Post post: posts) {
            if(post.getId() == id) {
                posts.remove(post);
                break;
            }
        }
    }

    public void updatePost(long id, Post post)
    {
        for(int i = 0; i < posts.size(); i++) {
            Post tmpValue = posts.get(i);
            if (tmpValue.getId() == id) {
                posts.set(i, post);
                return;
            }
        }
    }
}
