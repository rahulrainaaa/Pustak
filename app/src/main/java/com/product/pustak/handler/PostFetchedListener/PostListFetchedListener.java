package com.product.pustak.handler.PostFetchedListener;


import com.google.firebase.firestore.DocumentSnapshot;
import com.product.pustak.handler.BaseHandler.BaseHandler;
import com.product.pustak.model.Post;

import java.util.ArrayList;

public interface PostListFetchedListener {

    public void postListFetchedCallback(ArrayList<Post> list, ArrayList<DocumentSnapshot> snapshots, BaseHandler.CODE code, String message);
}
