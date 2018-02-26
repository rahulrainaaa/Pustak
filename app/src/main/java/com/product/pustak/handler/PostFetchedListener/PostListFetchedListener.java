package com.product.pustak.handler.PostFetchedListener;


import com.google.firebase.firestore.DocumentSnapshot;
import com.product.pustak.handler.BaseHandler.BaseHandler;
import com.product.pustak.model.Post;

import java.util.ArrayList;

/**
 * Interface for {@link Post} fetched listener event.
 */
public interface PostListFetchedListener {

    /**
     * Callback method, for notifying the response with result set.
     *
     * @param list      {@link ArrayList<Post>}
     * @param snapshots {@link ArrayList<DocumentSnapshot>}
     * @param code      {@link com.product.pustak.handler.BaseHandler.BaseHandler.CODE}
     * @param message   {@link String}
     */
    void postListFetchedCallback(ArrayList<Post> list, ArrayList<DocumentSnapshot> snapshots, BaseHandler.CODE code, String message);
}
