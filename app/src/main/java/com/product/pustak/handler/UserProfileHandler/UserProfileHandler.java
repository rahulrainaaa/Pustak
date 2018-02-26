package com.product.pustak.handler.UserProfileHandler;

import android.support.annotation.NonNull;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.handler.BaseHandler.BaseHandler;
import com.product.pustak.handler.UserProfileListener.UserProfileFetchedListener;
import com.product.pustak.handler.UserProfileListener.UserProfileUpdatedListener;
import com.product.pustak.model.User;

/**
 * Handler class for handling the user profile for fetching.
 */
public class UserProfileHandler extends BaseHandler {

    public static final String TAG = "UserProfileHandler";

    /**
     * Class private data member(s).
     */
    private UserProfileFetchedListener mFetchedListener = null;
    private UserProfileUpdatedListener mUpdatedListener = null;
    private User mUser = null;

    /**
     * Constructor to initialize the data member(s).
     *
     * @param activity {@link BaseActivity}
     */
    public UserProfileHandler(BaseActivity activity) {

        super(activity);
    }

    /**
     * Method to fetch user profile wrt phone, from DB.
     *
     * @param listener     {@link UserProfileFetchedListener} reference.
     * @param showProgress boolean
     * @param phone        String
     */
    public void getUser(UserProfileFetchedListener listener, boolean showProgress, String phone) {

        this.mFetchedListener = listener;

        if (phone == null) {

            //noinspection ConstantConditions
            phone = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        }

        assert phone != null;
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
                            sendFetchedCallback(CODE.NEW_REGISTER, iss.getMessage(), null);

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

    /**
     * Method to send {@link UserProfileFetchedListener} callback to the caller class with the response data.
     *
     * @param code    {@link com.product.pustak.handler.BaseHandler.BaseHandler.CODE}
     * @param message {@link String}
     * @param user    {@link User} model object reference.
     */
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

    /**
     * Method to add/override login user profile data {@link User}.
     *
     * @param user            Model class with all the user profile details initialized.
     * @param updatedListener Listener for activity callback.
     * @param showProgress    If progress bar has to be shown while fetching.
     */
    public void setUser(User user, UserProfileUpdatedListener updatedListener, @SuppressWarnings("SameParameterValue") boolean showProgress) {

        this.mUpdatedListener = updatedListener;
        this.mShowProgress = showProgress;
        this.mUser = user;

        //noinspection ConstantConditions
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

    /**
     * Method to send {@link UserProfileUpdatedListener} callback to the caller class with the response data.
     *
     * @param code    user profile fetching status.
     * @param message status message to be sent.
     */
    private void sendUpdatedCallback(CODE code, String message) {

        try {

            if (mShowProgress) {                // hide progress, if visible.

                mShowProgress = false;
                mActivity.hideProgressDialog();
            }

            if (mUpdatedListener != null) {     // Send callback to subscriber class.

                mUpdatedListener.userProfileUpdatedCallback(mUser, code, message);
            }

        } catch (Exception e) {

            e.printStackTrace();
        }

    }


}
