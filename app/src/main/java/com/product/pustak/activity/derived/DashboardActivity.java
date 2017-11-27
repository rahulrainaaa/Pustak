package com.product.pustak.activity.derived;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.product.pustak.R;
import com.product.pustak.activity.base.BaseActivity;
import com.product.pustak.fragment.base.BaseFragment;
import com.product.pustak.fragment.derived.AddPostFragment;
import com.product.pustak.fragment.derived.FailureFragment;
import com.product.pustak.fragment.derived.MyPostFragment;
import com.product.pustak.fragment.derived.ProfileFragment;
import com.product.pustak.fragment.derived.ViewPostFragment;

public class DashboardActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private NavigationView mNavigationView = null;
    private FragmentManager mFragmentManager = null;
    private FragmentTransaction mFragmentTransaction = null;


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
        
        mNavigationView.setCheckedItem(R.id.nav_find_book);
        navFindBook();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.dashboard, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
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
        } else if (id == R.id.nav_messages) {

            navMessage();
        } else if (id == R.id.nav_share) {


        } else if (id == R.id.nav_about_us) {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void navMyPost() {

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, MyPostFragment.getInstance());
        mFragmentTransaction.commit();
    }

    private void navAddPost() {

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, AddPostFragment.getInstance());
        mFragmentTransaction.commit();
    }

    private void navProfile() {

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, ProfileFragment.getInstance());
        mFragmentTransaction.commit();
    }

    private void navFindBook() {

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, ViewPostFragment.getInstance());
        mFragmentTransaction.commit();
    }

    private void navMessage() {

        mFragmentTransaction = mFragmentManager.beginTransaction();
        mFragmentTransaction.replace(R.id.fragment_container, ViewPostFragment.getInstance());
        mFragmentTransaction.commit();
    }

    private void loadFailureFragment() {

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

        } else if (BaseFragment.FragmentType.VIEW_POST == fragmentType) {

            mNavigationView.setCheckedItem(R.id.nav_messages);

        } else if (BaseFragment.FragmentType.FAILURE == fragmentType) {

            loadFailureFragment();
        }

    }

}
