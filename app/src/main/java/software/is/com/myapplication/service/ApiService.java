package software.is.com.myapplication.service;


import retrofit.Callback;
import retrofit.http.GET;
import software.is.com.myapplication.model.Post;


public interface ApiService {

    @GET("/api/get_comment.php?uid=1&cat=1")
    void getImage(Callback<Post> callback);

}
