package software.is.com.myapplication.service;


import retrofit.Callback;
import retrofit.http.GET;
import software.is.com.myapplication.model.Post;


public interface ApiService {

    @GET("/i_community/service/news_list.php")
    void getImage(Callback<Post> callback);

}
