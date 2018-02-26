package com.product.pustak.handler.PostHandler;


import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.handler.BaseHandler.BaseHandler;
import com.product.pustak.handler.PostFetchedListener.PostListFetchedListener;
import com.product.pustak.model.Post;

import java.util.ArrayList;

/**
 * Handler class to handle the Post related query from database.
 */
public class PostHandler extends BaseHandler {

    public static final String TAG = "PostHandler";

    /**
     * class private data member(s).
     */
    private PostListFetchedListener mFetchListener = null;

    public PostHandler(BaseActivity activity) {

        super(activity);
    }

    /**
     * Method to call for all my {@link Post} added by logged in {@link com.product.pustak.model.User}.
     *
     * @param phone         String
     * @param postArrayList {@link ArrayList<Post>}
     * @param snapshots     {@link ArrayList<DocumentSnapshot>}
     * @param fetchListener {@link PostListFetchedListener}
     * @param showProgress  boolean
     */
    public void fetchMyPostList(String phone, final ArrayList<Post> postArrayList, final ArrayList<DocumentSnapshot> snapshots, PostListFetchedListener fetchListener, @SuppressWarnings("SameParameterValue") boolean showProgress) {

        mFetchListener = fetchListener;

        if (showProgress) {

            mShowProgress = true;
            mActivity.showProgressDialog();
        }

        FirebaseFirestore.getInstance()
                .collection("posts")
                .whereEqualTo("mobile", phone)
                .get()
                .addOnCompleteListener(task -> {

                    if (task.isSuccessful()) {

                        try {

                            for (DocumentSnapshot document : task.getResult()) {

                                Post tempPost = document.toObject(Post.class);
                                snapshots.add(document);
                                postArrayList.add(tempPost);
                            }

                            sendListFetchedCallback(postArrayList, snapshots, CODE.SUCCESS, "Success");

                        } catch (Exception e) {

                            e.printStackTrace();
                            sendListFetchedCallback(null, null, CODE.Exception, e.getMessage());
                        }
                    } else {

                        sendListFetchedCallback(null, null, CODE.FAILED, "Unable to fetch");
                    }
                });
    }

    /**
     * Method to send the callback to listening instance, with response and result set.
     *
     * @param list      {@link ArrayList<Post>}
     * @param snapshots {@link ArrayList<DocumentSnapshot>}
     * @param code      {@link com.product.pustak.handler.BaseHandler.BaseHandler.CODE}
     * @param message   {@link String}
     */
    private void sendListFetchedCallback(ArrayList<Post> list, ArrayList<DocumentSnapshot> snapshots, CODE code, String message) {

        try {
            if (mShowProgress) {

                mShowProgress = false;
                mActivity.hideProgressDialog();
            }

            if (mFetchListener != null) {

                mFetchListener.postListFetchedCallback(list, snapshots, code, message);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

}
