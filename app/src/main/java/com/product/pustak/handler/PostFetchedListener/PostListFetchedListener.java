package com.product.pustak.handler.PostFetchedListener;


import com.product.pustak.handler.BaseHandler.BaseHandler;
import com.product.pustak.model.Post;

import java.util.ArrayList;

public interface PostListFetchedListener {

    public void postListFetchedCallback(ArrayList<Post> list, BaseHandler.CODE code, String message);
}
