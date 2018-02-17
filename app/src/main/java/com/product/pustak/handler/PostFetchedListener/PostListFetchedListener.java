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
     * @param list
     * @param snapshots
     * @param code
     * @param message
     */
    void postListFetchedCallback(ArrayList<Post> list, ArrayList<DocumentSnapshot> snapshots, BaseHandler.CODE code, String message);
}
