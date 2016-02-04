package software.is.com.myapplication;

import android.app.Dialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gcm.GCMRegistrar;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import software.is.com.myapplication.activity.NewsFullActivity;
import software.is.com.myapplication.activity.PostActivity;
import software.is.com.myapplication.adapter.BasesAdapter;
import software.is.com.myapplication.event.ActivityResultBus;
import software.is.com.myapplication.event.ApiBus;
import software.is.com.myapplication.event.ImagesReceivedEvent;
import software.is.com.myapplication.event.ImagesRequestedEvent;
import software.is.com.myapplication.model.Post;

import static software.is.com.myapplication.CommonUtilities.DISPLAY_MESSAGE_ACTION;
import static software.is.com.myapplication.CommonUtilities.EXTRA_MESSAGE;
import static software.is.com.myapplication.CommonUtilities.EXTRA_TYPE;
import static software.is.com.myapplication.CommonUtilities.SENDER_ID;

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
    ProgressBar progressBar2;
    public static String name;
    public static String email;
    public static String vender;
    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;

    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();

    // Connection detector
    ConnectionDetector cd;
    String bg;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_item_list, menu);
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.listView);
        prefManager = IcrmApp.getPrefManager();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        ApiBus.getInstance().postQueue(new ImagesRequestedEvent());
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content_frame = (RelativeLayout) findViewById(R.id.content_frame);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupViews();
        Log.e("zzzx",prefManager.isLogin().getOr(false)+"");

        if (toolbar != null) {
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle("iCommunity");
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
                        break;

                    case R.id.account:

                        drawerLayout.closeDrawers();
                        break;

                    case R.id.enterprise:

                        drawerLayout.closeDrawers();
                        break;

                    case R.id.logout:
                        prefManager.clear();
                        prefManager.commit();
                        finish();
                        drawerLayout.closeDrawers();
                        break;

                    default:
                        drawerLayout.closeDrawers();
                }

                Log.d("MENU ITEM", menuItem.getTitle().toString());
                return false;
            }
        });


        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                boolean enable = false;
                if (listView != null && listView.getChildCount() > 0) {
                    // check if the first item of the list is visible
                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
                    // check if the top of the first item is visible
                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
                    // enabling or disabling the refresh layout
                    enable = firstItemVisible && topOfFirstItemVisible;
                }
                swipeContainer.setEnabled(enable);
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
//            Log.e("event", event.getPost().getPost().get(0).getDetails());
//            Log.e("BGGG", event.getPost().getBg() + "");

            for (int i = 0; i < event.getPost().getPost().size(); i++) {
                bg = event.getPost().getBg();
                int color = Color.parseColor(bg);
                content_frame.setBackgroundColor(color);
                listPost.add(event.getPost());
            }

            setupAdapter();
            progressBar2.setVisibility(View.GONE);
        }

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String title = listPost.get(position).getPost().get(position).getTitle();
                String detail = listPost.get(position).getPost().get(position).getDetails();
                String image_url = listPost.get(position).getPost().get(position).getFile_img();
                String code = listPost.get(position).getPost().get(position).getCode();
                int type = listPost.get(position).getPost().get(position).getStatus_img();
                Intent i = new Intent(getApplicationContext(), NewsFullActivity.class);
                i.putExtra("title", title);
                i.putExtra("detail", detail);
                i.putExtra("image", image_url);
                i.putExtra("type",type);
                i.putExtra("code",code);
                i.putExtra("vender",vender);
                startActivity(i);
            }
        });
    }

    private void setupAdapter() {
        basesAdapter = new BasesAdapter(getApplicationContext(), listPost);
        listView.setAdapter(basesAdapter);

    }




    @Override
    protected void onDestroy() {

        super.onDestroy();
    }
}
