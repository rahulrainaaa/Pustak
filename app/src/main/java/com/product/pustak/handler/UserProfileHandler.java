package com.product.pustak.handler;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.handler.UserProfileListener.UserProfileFetchedListener;
import com.product.pustak.handler.UserProfileListener.UserProfileUpdatedListener;
import com.product.pustak.model.User;

/**
 * Handler class for handling the user profile for fetching.
 */
public class UserProfileHandler {

    public static final String TAG = "UserProfileHandler";


    public enum CODE {SUCCESS, IllegalStateException, Exception}

    /**
     * Class private data member(s).
     */
    private BaseActivity mActivity = null;
    private boolean mShowProgress = false;
    private UserProfileFetchedListener mFetchedListener = null;
    private UserProfileUpdatedListener mUpdatedListener = null;
    private User mUser = null;

    public UserProfileHandler(BaseActivity activity) {

        this.mActivity = activity;
    }

    public void getUser(UserProfileFetchedListener listener, boolean showProgress, String phone) {

        this.mFetchedListener = listener;

        if (phone == null) {

            phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }

        FirebaseFirestore.getInstance()
                .collection("users")
                .document(phone.trim())
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {

                        try {

                            User user = documentSnapshot.toObject(User.class);
                            sendFetchedCallback(CODE.SUCCESS, "Success", user);

                        } catch (IllegalStateException iss) {

                            iss.printStackTrace();
                            sendFetchedCallback(CODE.IllegalStateException, iss.getMessage(), null);

                        } catch (Exception e) {

                            e.printStackTrace();
                            sendFetchedCallback(CODE.Exception, e.getMessage(), null);

                        }
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        sendFetchedCallback(CODE.Exception, e.getMessage(), null);
                    }
                });

        if (showProgress && (mActivity != null)) {
            mShowProgress = true;
            mActivity.showProgressDialog();
        }
    }

    private void sendFetchedCallback(CODE code, String message, User user) {

        this.mUser = user;

        try {

            if (mShowProgress) {

                mShowProgress = false;
                mActivity.hideProgressDialog();
            }

            if (mFetchedListener != null) {

                mFetchedListener.userProfileFetchedCallback(user, code, message);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }

    public void setUser(User user, UserProfileUpdatedListener updatedListener, boolean showProgress) {

        this.mUpdatedListener = updatedListener;
        this.mShowProgress = showProgress;
        this.mUser = user;

        FirebaseFirestore.getInstance().collection("users").document(FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber())
                .set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {

                        sendUpdatedCallback(CODE.SUCCESS, "");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {

                        sendUpdatedCallback(CODE.Exception, e.getMessage());
                    }
                });

        if (showProgress && (mActivity != null)) {

            mActivity.showProgressDialog();
            mShowProgress = true;
        }

    }

    private void sendUpdatedCallback(CODE code, String message) {

        try {

            if (mShowProgress) {

                mShowProgress = false;
                mActivity.hideProgressDialog();
            }

            if (mUpdatedListener != null) {

                mUpdatedListener.userProfileUpdatedCallback(mUser, code, message);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


}
