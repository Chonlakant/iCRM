package software.is.com.myapplication.service;

import android.content.Context;
import android.util.Log;

import com.squareup.otto.Subscribe;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import software.is.com.myapplication.event.ApiBus;
import software.is.com.myapplication.event.ImagesReceivedEvent;
import software.is.com.myapplication.event.ImagesRequestedEvent;
import software.is.com.myapplication.model.Post;


public class ApiHandler {

    public Context context;
    private ApiService api;
    private ApiBus apiBus;

    public ApiHandler(Context context, ApiService api,
                      ApiBus apiBus) {

        this.context = context;
        this.api = api;
        this.apiBus = apiBus;
    }

    public void registerForEvents() {
        apiBus.register(this);
    }



    @Subscribe public void onGetConversationGroup(final ImagesRequestedEvent event) {
    Log.e("ssss",event.getVendor());
        api.getImage(event.getVendor(),new Callback<Post>() {
            @Override
            public void success(Post post, Response response) {

                if(post.getPost() != null){
                    Log.e("aaaa",post.getPost().size()+"");
                    ApiBus.getInstance().postQueue(new ImagesReceivedEvent(post));
                }

            }

            @Override
            public void failure(RetrofitError error) {
//                Log.e("error",error.getLocalizedMessage());
//                Log.e("error",error.getUrl());
            }
        });
    }

}
