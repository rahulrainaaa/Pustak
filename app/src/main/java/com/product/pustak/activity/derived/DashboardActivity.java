package com.product.pustak.activity.derived;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.product.pustak.R;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.fragment.base.BaseFragment;
import com.product.pustak.fragment.derived.AddPostFragment;
import com.product.pustak.fragment.derived.FailureFragment;
import com.product.pustak.fragment.derived.MyPostFragment;
import com.product.pustak.fragment.derived.ProfileFragment;
import com.product.pustak.fragment.derived.ViewPostFragment;
import com.product.pustak.model.User;
import com.product.pustak.utils.ProfileUtils;

import java.util.HashMap;

import uk.co.samuelwall.materialtaptargetprompt.MaterialTapTargetPrompt;

/**
 * Dashboard Activity to hold all the fragments and handle fragment transactions.
 */
public class DashboardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    public static final String TAG = "DashboardActivity";

    /**
     * Class private data member(s).
     */
    private static final HashMap<String, User> mFetcherUsers = new HashMap<>();
    private NavigationView mNavigationView = null;
    private FragmentManager mFragmentManager = null;
    private FragmentTransaction mFragmentTransaction = null;
    private User user = null;
    private int selectedFragment = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar mToolbar = findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setVisibility(View.GONE);

        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, mToolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        this.mFragmentManager = getSupportFragmentManager();

        this.user = getIntent().getParcelableExtra("user");
        View header = mNavigationView.getHeaderView(0);
        ((TextView) header.findViewById(R.id.txt_user_name)).setText(this.user.getName());
        ((TextView) header.findViewById(R.id.txt_phone)).setText("Mob: " + this.user.getMobile());
        ((TextView) header.findViewById(R.id.txt_email)).setText(this.user.getEmail());

        mNavigationView.setCheckedItem(R.id.nav_my_post);
        navMyPost();

    }

    @Override
    protected void onResume() {
        super.onResume();

        if (getSharedPreferences("target", 0).getBoolean("c", true)) {

            Handler handler = new Handler();
            handler.postDelayed(() -> showSideNavigationTarget(), 900);
        }
    }

    @Override
    public void onBackPressed() {


        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        }

        switch (selectedFragment) {
            case 1:

                navProfile();
                return;
            case 2:

                navProfile();
                return;
            case 4:

                navProfile();
                return;
            case 6:

                navProfile();
                return;
        }

        new AlertDialog.Builder(this)
                .setTitle(R.string.quit)
                .setMessage(R.string.prompt_exit)
                .setPositiveButton(getString(R.string.yes), (dialog, which) -> {

                    dialog.dismiss();
                    DashboardActivity.super.onBackPressed();
                })
                .setNegativeButton(getString(R.string.no), (dialog, which) -> dialog.dismiss())
                .show();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@SuppressWarnings("NullableProblems") MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.nav_my_post) {

            navMyPost();
        } else if (id == R.id.nav_add_post) {

            navAddPost();
        } else if (id == R.id.nav_profile) {

            navProfile();
        } else if (id == R.id.nav_find_book) {

            navFindBook();
        } else if (id == R.id.nav_share) {

            ProfileUtils.shareWith(this);
        } else if (id == R.id.nav_about_us) {

            startActivity(new Intent(this, AboutUsActivity.class));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    /**
     * Method to get the login user data object.
     *
     * @return User reference
     */

    public User getUser() {

        return this.user;
    }

    /**
     * Method to get the list of all cached user's information as {@link java.util.Map}.
     *
     * @return All cached users.
     */
    public HashMap<String, User> getFetchedUsers() {

        return mFetcherUsers;
    }

    /**
     * Method to load {@link MyPostFragment}.
     */
    private void navMyPost() {

        selectedFragment = 1;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, MyPostFragment.getInstance());
        mFragmentTransaction.commit();
    }

    /**
     * Method to load {@link AddPostFragment}.
     */
    private void navAddPost() {

        selectedFragment = 2;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, AddPostFragment.getInstance());
        mFragmentTransaction.commit();
    }

    /**
     * Method to load {@link ProfileFragment}.
     */
    private void navProfile() {

        selectedFragment = 3;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, ProfileFragment.getInstance());
        mFragmentTransaction.commit();
    }

    /**
     * Method to load {@link ViewPostFragment}.
     */
    private void navFindBook() {

        selectedFragment = 4;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, ViewPostFragment.getInstance());
        mFragmentTransaction.commit();
    }

    /**
     * Method to load {@link FailureFragment} in case of issue.
     *
     * @param message message to show on the fragment.
     */
    public void loadFailureFragment(String message) {

        int prevFragment = selectedFragment;
        selectedFragment = 6;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        FailureFragment failureFragment = FailureFragment.getInstance();
        Bundle bundle = new Bundle();
        bundle.putString("message", message);
        bundle.putInt("prevFragment", prevFragment);
        failureFragment.setArguments(bundle);
        mFragmentTransaction.replace(R.id.fragment_container, failureFragment);
        mFragmentTransaction.commit();
    }

    /**
     * Public method to load fragment on signal from outside the class, based on {@link com.product.pustak.fragment.base.BaseFragment.FragmentType} argument.
     *
     * @param fragmentType enum {@link com.product.pustak.fragment.base.BaseFragment.FragmentType}.
     */
    public void loadFragment(BaseFragment.FragmentType fragmentType) {

        if (BaseFragment.FragmentType.ADD_POST == fragmentType) {

            mNavigationView.setCheckedItem(R.id.nav_add_post);
            navAddPost();

        } else if (BaseFragment.FragmentType.MY_POST == fragmentType) {

            mNavigationView.setCheckedItem(R.id.nav_my_post);
            navMyPost();

        } else if (BaseFragment.FragmentType.PROFILE == fragmentType) {

            mNavigationView.setCheckedItem(R.id.nav_profile);
            navProfile();

        } else if (BaseFragment.FragmentType.VIEW_POST == fragmentType) {

            mNavigationView.setCheckedItem(R.id.nav_find_book);
            navFindBook();

        }

    }

    /**
     * Method to show target on navigation drawer icon button.
     */
    private void showSideNavigationTarget() {

        final MaterialTapTargetPrompt.Builder tapTargetPromptBuilder = new MaterialTapTargetPrompt.Builder(this)
                .setPrimaryText("Open Navigation Drawer")
                .setIcon(R.drawable.icon_menu_nav)
                .setBackgroundColour(Color.argb(230, 126, 121, 255))
                .setSecondaryText("Check out all the functionality of this application.")
                .setAnimationInterpolator(new FastOutSlowInInterpolator());

        final Toolbar tb = this.findViewById(R.id.toolbar);
        tapTargetPromptBuilder.setTarget(tb.getChildAt(1));

        tapTargetPromptBuilder.setPromptStateChangeListener((prompt, state) -> {
            if (state == MaterialTapTargetPrompt.STATE_FOCAL_PRESSED) {
                getSharedPreferences("target", 0).edit().putBoolean("c", false).apply();
            }
        });
        tapTargetPromptBuilder
                .showFor(7000);
    }

}
