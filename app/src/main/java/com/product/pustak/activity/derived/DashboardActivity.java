package com.product.pustak.activity.derived;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
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
import com.product.pustak.fragment.derived.MessageFragment;
import com.product.pustak.fragment.derived.MyPostFragment;
import com.product.pustak.fragment.derived.ProfileFragment;
import com.product.pustak.fragment.derived.ViewPostFragment;
import com.product.pustak.model.User;

import java.util.HashMap;

public class DashboardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private static HashMap<String, User> mFetcherUsers = new HashMap<String, User>();
    private NavigationView mNavigationView = null;
    private FragmentManager mFragmentManager = null;
    private FragmentTransaction mFragmentTransaction = null;
    private User user = null;
    private int selectedFragment = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setVisibility(View.GONE);
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        mNavigationView = (NavigationView) findViewById(R.id.nav_view);
        mNavigationView.setNavigationItemSelectedListener(this);

        this.mFragmentManager = getSupportFragmentManager();
        this.mFragmentTransaction = mFragmentManager.beginTransaction();

        this.user = getIntent().getParcelableExtra("user");
        View header = mNavigationView.getHeaderView(0);
        ((TextView) header.findViewById(R.id.txt_user_name)).setText(this.user.getName());
        ((TextView) header.findViewById(R.id.txt_phone)).setText("Mob: " + this.user.getMobile());
        ((TextView) header.findViewById(R.id.txt_email)).setText(this.user.getEmail());

        mNavigationView.setCheckedItem(R.id.nav_profile);
        navProfile();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);

        } else {

            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle("Alert");
            dialog.setMessage("Do you want to exit?");
            dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                    DashboardActivity.super.onBackPressed();
                }
            });
            dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    dialog.dismiss();
                }
            });
            dialog.show();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

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

            
        } else if (id == R.id.nav_about_us) {

            startActivity(new Intent(this, AboutUsActivity.class));
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public User getUser() {

        return this.user;
    }

    public HashMap<String, User> getFetchedUsers() {

        return mFetcherUsers;
    }

    private void navMyPost() {

        selectedFragment = 1;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, MyPostFragment.getInstance());
        mFragmentTransaction.commit();
    }

    private void navAddPost() {

        selectedFragment = 2;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, AddPostFragment.getInstance());
        mFragmentTransaction.commit();
    }

    private void navProfile() {

        selectedFragment = 3;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, ProfileFragment.getInstance());
        mFragmentTransaction.commit();
    }

    private void navFindBook() {

        selectedFragment = 4;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, ViewPostFragment.getInstance());
        mFragmentTransaction.commit();
    }

    private void navMessage() {

        selectedFragment = 5;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, MessageFragment.getInstance());
        mFragmentTransaction.commit();
    }

    private void loadFailureFragment() {

        selectedFragment = 6;
        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, FailureFragment.getInstance());
        mFragmentTransaction.commit();
    }

    public void loadFragment(BaseFragment.FragmentType fragmentType) {

        if (BaseFragment.FragmentType.ADD_POST == fragmentType) {

            mNavigationView.setCheckedItem(R.id.nav_my_post);
            navMyPost();

        } else if (BaseFragment.FragmentType.MESSAGE == fragmentType) {

            mNavigationView.setCheckedItem(R.id.nav_add_post);
            navAddPost();

        } else if (BaseFragment.FragmentType.MY_POST == fragmentType) {

            mNavigationView.setCheckedItem(R.id.nav_profile);
            navMyPost();

        } else if (BaseFragment.FragmentType.PROFILE == fragmentType) {

            mNavigationView.setCheckedItem(R.id.nav_find_book);
            navProfile();

        } else if (BaseFragment.FragmentType.FAILURE == fragmentType) {

            loadFailureFragment();
        }

    }

}
