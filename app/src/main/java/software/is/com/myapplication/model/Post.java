package software.is.com.myapplication.model;

import java.util.List;

/**
 * Created by root1 on 12/20/15.
 */
public class Post {


    /**
     * status : 1
     * posts : [{"name":"gggg","txt":"แสดงความคิดเห็น","title":"หลักสูตรการสอน ","image":"","count":"12"}]
     */

    private int status;
    /**
     * name : gggg
     * txt : แสดงความคิดเห็น
     * title : หลักสูตรการสอน
     * image :
     * count : 12
     */

    private List<PostsEntity> posts;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPosts(List<PostsEntity> posts) {
        this.posts = posts;
    }

    public int getStatus() {
        return status;
    }

    public List<PostsEntity> getPosts() {
        return posts;
    }

    public static class PostsEntity {
        private String name;
        private String txt;
        private String title;
        private String image;
        private String count;

        public void setName(String name) {
            this.name = name;
        }

        public void setTxt(String txt) {
            this.txt = txt;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getName() {
            return name;
        }

        public String getTxt() {
            return txt;
        }

        public String getTitle() {
            return title;
        }

        public String getImage() {
            return image;
        }

        public String getCount() {
            return count;
        }
    }
}
