package software.is.com.myapplication;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Handler;
import android.support.customtabs.CustomTabsIntent;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import software.is.com.myapplication.activity.PostActivity;
import software.is.com.myapplication.adapter.BasesAdapter;
import software.is.com.myapplication.event.ActivityResultBus;
import software.is.com.myapplication.event.ApiBus;
import software.is.com.myapplication.event.ImagesReceivedEvent;
import software.is.com.myapplication.event.ImagesRequestedEvent;
import software.is.com.myapplication.model.Post;

public class MainActivity extends AppCompatActivity {
    BasesAdapter basesAdapter;
    ArrayList<Post> listPost = new ArrayList<>();
    ListView listView;
    private SwipeRefreshLayout swipeContainer;
    private NavigationView navigationView;
    private DrawerLayout drawerLayout;
    private Toolbar toolbar;
    PrefManager prefManager;
    RelativeLayout content_frame;
    Dialog dialog2;
    ProgressBar progressBar2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        dialog2 = new Dialog(getApplicationContext());
        listView = (ListView) findViewById(R.id.listView);
        prefManager = IcrmApp.getPrefManager();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        ApiBus.getInstance().postQueue(new ImagesRequestedEvent());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        dialog2 = new Dialog(getApplicationContext(), R.style.FullHeightDialog);
        dialog2.setContentView(R.layout.dialog_loading);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content_frame = (RelativeLayout) findViewById(R.id.content_frame);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupViews();

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("iCRM");
            toolbar.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
            getSupportActionBar().setHomeButtonEnabled(true);

            ActionBarDrawerToggle drawerToggle = new ActionBarDrawerToggle(
                    this,
                    drawerLayout,
                    toolbar,
                    R.string.drawer_open,
                    R.string.drawer_close);
            drawerLayout.setDrawerListener(drawerToggle);
            drawerToggle.setDrawerIndicatorEnabled(true);
            drawerToggle.syncState();
        }
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        ApiBus.getInstance().postQueue(new ImagesRequestedEvent());
                        listPost.clear();
                        if (basesAdapter != null) {
                            basesAdapter.notifyDataSetChanged();
                        }
                        if (listPost != null) {

                            setupAdapter();
                        }

                        swipeContainer.setRefreshing(false);
                    }
                }, 2500);
            }
        });


    }

    private void setupViews() {

        navigationView.addHeaderView(new DrawerHeaderView(this));
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem menuItem) {
                int id = menuItem.getItemId();
                switch (id) {

                    case R.id.qr_code:
                        Intent i = new Intent(getApplicationContext(), PostActivity.class);
                        startActivity(i);
                        finish();
                        break;

                    case R.id.account:
                        dialog2.show();
                        drawerLayout.closeDrawers();
                        break;

                    case R.id.enterprise:

                        drawerLayout.closeDrawers();
                        break;

                    default:
                        drawerLayout.closeDrawers();
                }

                Log.d("MENU ITEM", menuItem.getTitle().toString());
                return false;
            }
        });

    }

    @Override
    protected void onResume() {
        super.onResume();
        ActivityResultBus.getInstance().register(this);
        ApiBus.getInstance().register(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        ActivityResultBus.getInstance().unregister(this);
        ApiBus.getInstance().unregister(this);
    }

    @Subscribe
    public void GetList(final ImagesReceivedEvent event) {
        if (event != null) {
            Log.e("event", event.getPost().getPosts().get(0).getName());
            listPost.add(event.getPost());
            setupAdapter();
            progressBar2.setVisibility(View.GONE);
        }
    }

    private void setupAdapter() {
        basesAdapter = new BasesAdapter(getApplicationContext(), listPost);
        listView.setAdapter(basesAdapter);
    }
}
