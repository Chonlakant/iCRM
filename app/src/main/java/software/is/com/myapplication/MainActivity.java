package software.is.com.myapplication;

import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Handler;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.otto.Subscribe;

import java.util.ArrayList;

import software.is.com.myapplication.activity.SettingActivity;
import software.is.com.myapplication.activity.PostActivity;
import software.is.com.myapplication.adapter.BasesAdapter;
import software.is.com.myapplication.adapter.RecyclerViewTimelineListAdapter;
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
    ImageView imageView2;
    TextView textView4;
    RelativeLayout content_frame;
    ProgressBar progressBar2;
    public static String name;
    public static String email;
    public static String vender;
    RelativeLayout id_background;
    // Asyntask
    AsyncTask<Void, Void, Void> mRegisterTask;
    private static final int DIALOG_ID = 0;
    private static final int PREFERENCE_DIALOG_ID = 1;
    // Alert dialog manager
    AlertDialogManager alert = new AlertDialogManager();

    String title;
    public RecyclerView mRecyclerView;
    RecyclerViewTimelineListAdapter recyclerViewTimelineListAdapter;

    // Connection detector
    ConnectionDetector cd;
    String bg;
    String vendor;
    String picture;
    Uri uri;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // listView = (ListView) findViewById(R.id.listView);
        prefManager = IcrmApp.getPrefManager();
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);

        toolbar = (Toolbar) findViewById(R.id.toolbar);
//        textView4 = (TextView) findViewById(R.id.textView14);
//        imageView2 = (ImageView) findViewById(R.id.imageView6);

        id_background = (RelativeLayout) findViewById(R.id.id_background);
        progressBar2 = (ProgressBar) findViewById(R.id.progressBar2);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        content_frame = (RelativeLayout) findViewById(R.id.content_frame);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        setupViews();
        title = prefManager.title().getOr("iCommunity");
        vendor = prefManager.vendeName().getOr("");
        picture = prefManager.picture().getOr("aaaaaaa");
        ApiBus.getInstance().postQueue(new ImagesRequestedEvent(vendor));
        Log.e("zzzx", prefManager.isLogin().getOr(false) + "");
        Log.e("COlor", vendor);
        Log.e("picture", picture);
        // id_background.setBackgroundResource(prefManager.color().getOr(0));

        if (toolbar != null) {
            uri = Uri.parse(picture);
            setSupportActionBar(toolbar);
            getSupportActionBar().setTitle(title);
//            textView4.setText(title);
//            imageView2.setImageURI(uri);
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
                        ApiBus.getInstance().postQueue(new ImagesRequestedEvent(vendor));
                        listPost.clear();
                        if (recyclerViewTimelineListAdapter != null) {
                            recyclerViewTimelineListAdapter.notifyDataSetChanged();
                        }
                        if (listPost != null) {

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
                    case R.id.setting:
                        Intent intent = new Intent(getApplicationContext(), SettingActivity.class);
                        startActivity(intent);
                        drawerLayout.closeDrawers();
                        break;


                    default:
                        drawerLayout.closeDrawers();
                }

                Log.d("MENU ITEM", menuItem.getTitle().toString());
                return false;
            }
        });

//        mRecyclerView.setOnScrollListener(new AbsListView.OnScrollListener() {
//            @Override
//            public void onScrollStateChanged(AbsListView view, int scrollState) {
//
//            }
//
//            @Override
//            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
//                boolean enable = false;
//                if (listView != null && listView.getChildCount() > 0) {
//                    // check if the first item of the list is visible
//                    boolean firstItemVisible = listView.getFirstVisiblePosition() == 0;
//                    // check if the top of the first item is visible
//                    boolean topOfFirstItemVisible = listView.getChildAt(0).getTop() == 0;
//                    // enabling or disabling the refresh layout
//                    enable = firstItemVisible && topOfFirstItemVisible;
//                }
//                swipeContainer.setEnabled(enable);
//            }
//        });

        mRecyclerView = (RecyclerView) findViewById(R.id.rvFeed);
        mRecyclerView.setHasFixedSize(true);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(linearLayoutManager);

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
                recyclerViewTimelineListAdapter = new RecyclerViewTimelineListAdapter(getApplicationContext(), listPost);
                mRecyclerView.setAdapter(recyclerViewTimelineListAdapter);
            }
            progressBar2.setVisibility(View.GONE);
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.action_logout:

                break;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();
    }

}
