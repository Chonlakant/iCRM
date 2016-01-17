package software.is.com.myapplication.event;


import software.is.com.myapplication.model.Post;

/**
 * Created by marcus on 22/04/15
 */

public class ImagesReceivedEvent {

    private static final String TAG = ImagesReceivedEvent.class.getSimpleName();
    private Post post;

    public ImagesReceivedEvent(Post post){
        this.post = post;
    }

    public Post getPost() {
        return post;
    }
}