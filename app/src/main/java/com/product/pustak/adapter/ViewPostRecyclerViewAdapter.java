package com.product.pustak.adapter;

import android.content.Intent;
import android.graphics.Color;
import android.os.Handler;
import android.os.Looper;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.product.pustak.R;
import com.product.pustak.activity.derived.DashboardActivity;
import com.product.pustak.activity.derived.UserProfileActivity;
import com.product.pustak.handler.UserProfileHandler.UserProfileHandler;
import com.product.pustak.holder.base.CellHolder;
import com.product.pustak.holder.derived.viewPost.CollapsedCellHolder;
import com.product.pustak.holder.derived.viewPost.ExpandedCellHolder;
import com.product.pustak.model.Post;
import com.product.pustak.model.User;
import com.product.pustak.utils.ProfileUtils;

import java.util.ArrayList;
import java.util.HashMap;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

/**
 * Adapter class to handle the RecyclerView in {@link com.product.pustak.fragment.derived.ViewPostFragment}.
 */
public class ViewPostRecyclerViewAdapter extends RecyclerView.Adapter<CellHolder> implements View.OnClickListener {

    private static final String TAG = "ViewPostRecyclerViewAdapter";

    /**
     * Private constant fields.
     */
    private static final int EXPANDED_CELL = 2;
    private static final int COLLAPSED_CELL = 1;
    private final int mExpandedCell = R.layout.item_expanded_rv_mypost;
    private final int mCollapsedCell = R.layout.item_collapsed_rv_mypost;
    private int lastPosition = -1;
    private boolean target_a = true;
    private boolean target_b = true;
    /**
     * private class Data members.
     */
    private Animation animation = null;
    private ArrayList<Post> mPostList = null;
    private DashboardActivity mActivity = null;
    private RecyclerView mRecyclerView = null;
    private int mExpandedCellPosition = -1;

    public ViewPostRecyclerViewAdapter(DashboardActivity activity, ArrayList<Post> postList, RecyclerView recyclerView) {

        this.mActivity = activity;
        this.mPostList = postList;
        this.mRecyclerView = recyclerView;
        animation = AnimationUtils.loadAnimation(mActivity, R.anim.anim_list_item_add);

        target_a = mActivity.getSharedPreferences("target", 0).getBoolean("a", true);
        target_b = mActivity.getSharedPreferences("target", 0).getBoolean("b", true);
    }

    /**
     * {@link RecyclerView.Adapter} adapter class override methods.
     */
    @Override
    public CellHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (viewType == EXPANDED_CELL) {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(this.mExpandedCell, parent, false);
            ExpandedCellHolder holder = new ExpandedCellHolder(itemView, this);
            holder.setIsRecyclable(true);
            return holder;

        } else {

            View itemView = LayoutInflater.from(parent.getContext()).inflate(this.mCollapsedCell, parent, false);
            CollapsedCellHolder holder = new CollapsedCellHolder(itemView, this);
            holder.setIsRecyclable(true);
            return holder;
        }

    }

    @Override
    public void onBindViewHolder(final CellHolder cellHolder, int position) {

        if (getItemViewType(position) == EXPANDED_CELL) {

            ExpandedCellHolder holder = (ExpandedCellHolder) cellHolder;
            holder.setTag(position);
            holder.setPositionTag(position);
            holder.setData(mPostList.get(position));

            if (!target_b && target_a) {

                showTargetHelp(holder, 1);
            }

        } else {

            CollapsedCellHolder holder = (CollapsedCellHolder) cellHolder;
            holder.setTag(position);
            holder.setPositionTag(position);
            holder.setData(mPostList.get(position));
            setAnimation(holder.cardView, position);

            if (target_b && position == 0) {

                mRecyclerView.stopScroll();
                mRecyclerView.scrollTo(0, 0);
                Handler handler = new Handler(Looper.getMainLooper());
                handler.postDelayed(() -> {
                    showTargetHelp(holder, 2);
                }, 700);

            }

        }

    }

    @Override
    public int getItemCount() {

        return mPostList.size();
    }

    @Override
    public int getItemViewType(int position) {

        if (position == mExpandedCellPosition) {

            return EXPANDED_CELL;
        } else {

            return COLLAPSED_CELL;
        }
    }

    @Override
    public void onClick(View view) {

        int oldExpandedCellPosition = mExpandedCellPosition;
        int position = (int) view.getTag();

        switch (view.getId()) {

            case R.id.cell_collapsed_header_layout:     // Expand the cell from header click.

                mExpandedCellPosition = position;
                break;

            case R.id.btn_collapsing:       // Collapse the cell from button click.

                mExpandedCellPosition = -1;
                break;

            case R.id.cell_expanded_header_layout:      // Collapse the cell from header click.

                mExpandedCellPosition = -1;
                break;

            case R.id.btn_call:             // Make a call to the post owner.

                respondToUser(1, position);
                break;
            case R.id.btn_message:          // Send message to the post owner.

                respondToUser(2, position);
                break;
            case R.id.btn_email:            // send an email to this post owner.

                respondToUser(3, position);
                break;
            case R.id.btn_location:             // show the geo location of this post owner.

                respondToUser(4, position);
                break;
            default:

                Toast.makeText(mActivity, "Unhandled onClickListener() callback.", Toast.LENGTH_SHORT).show();
                break;
        }

        if (oldExpandedCellPosition != mExpandedCellPosition) {

            notifyItemChanged(oldExpandedCellPosition);
            notifyItemChanged(mExpandedCellPosition);
        }
    }

    /**
     * @param buttonEvent call = 1, message = 2, email = 3, location = 4.
     */
    private void respondToUser(final int buttonEvent, int position) {

        String phone = mPostList.get(position).getMobile().trim();
        HashMap<String, User> map = mActivity.getFetchedUsers();
        boolean exist = mActivity.getFetchedUsers().containsKey(phone);

        // Check if the user info is present in cache Map, then proceed with user action.
        if (exist) {

            if (buttonEvent == 1) {

                ProfileUtils.call(mActivity, phone);
            } else if (buttonEvent == 2) {

                ProfileUtils.sendMessage(mActivity, phone);
            } else if (buttonEvent == 3) {

                Intent intent = new Intent(mActivity, UserProfileActivity.class);
                intent.putExtra("user", map.get(phone));
                mActivity.startActivity(intent);
                // ProfileUtils.email(mActivity, map.get(phone).getEmail());
            } else if (buttonEvent == 4) {

                ProfileUtils.mapLocation(mActivity, map.get(phone).getGeo());
            }

            return;
        }

        // Else, fetch the user info.
        UserProfileHandler userProfileHandler = new UserProfileHandler(mActivity);

        userProfileHandler.getUser((user, code, message) -> {

            if (code == UserProfileHandler.CODE.SUCCESS) {

                // Save the fetched user info into cache and proceed with user action.
                mActivity.getFetchedUsers().put(user.getMobile(), user);

                if (buttonEvent == 1) {

                    ProfileUtils.call(mActivity, user.getMobile());
                } else if (buttonEvent == 2) {

                    ProfileUtils.sendMessage(mActivity, user.getMobile());
                } else if (buttonEvent == 3) {

                    Intent intent = new Intent(mActivity, UserProfileActivity.class);
                    intent.putExtra("user", user);
                    mActivity.startActivity(intent);
                    // ProfileUtils.email(mActivity, user.getEmail());
                } else if (buttonEvent == 4) {

                    ProfileUtils.mapLocation(mActivity, user.getGeo());
                }

            } else {

                Toast.makeText(mActivity, "" + message, Toast.LENGTH_SHORT).show();
            }
        }, true, phone);

    }

    /**
     * Method to apply the animation on items once.
     */
    private void setAnimation(View viewToAnimate, int position) {

        // If the bound view wasn't previously displayed on screen, it is animated.
        if (position > lastPosition) {

            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
    }

    /**
     * Method to show target button and illustrate new users about the UI.
     *
     * @param cellHolder {@link CellHolder} derived holder class reference in {@link CellHolder}.
     */
    private void showTargetHelp(CellHolder cellHolder, int target) {

        if (target == 1) {

            ExpandedCellHolder holder = (ExpandedCellHolder) cellHolder;
            new MaterialTapTargetPrompt.Builder(mActivity)
                    .setTarget(holder.iBtnProfile)
                    .setBackgroundColour(Color.argb(235, 126, 121, 255))
                    .setPrimaryText("Seller's profile")
                    .setSecondaryText("Tap the button to see details of owner who posted this book add.")
                    .setPromptStateChangeListener((prompt, state) -> {

                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {

                            mActivity.getSharedPreferences("target", 0).edit().putBoolean("a", false).apply();
                            target_a = false;

                        }
                    })
                    .showFor(7000);

        } else if (target == 2) {

            CollapsedCellHolder holder = (CollapsedCellHolder) cellHolder;
            new MaterialTapTargetPrompt.Builder(mActivity)
                    .setTarget(holder.txtAvailability)
                    .setBackgroundColour(Color.argb(235, 126, 121, 255))
                    .setPrimaryText("Rent / Sell")
                    .setSecondaryText("Tap the button to see details of book posted for Rent or Sell.\n\nR = Rent\nS = Sell")
                    .setPromptStateChangeListener((prompt, state) -> {

                        if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {

                            mActivity.getSharedPreferences("target", 0).edit().putBoolean("b", false).apply();
                            target_b = false;
                        }
                    })
                    .showFor(7000);

        }

    }
}
