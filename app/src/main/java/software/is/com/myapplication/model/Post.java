package software.is.com.myapplication.model;

import java.util.List;

/**
 * Created by root1 on 12/20/15.
 */
public class Post {


    /**
     * status : 1
     * posts : [{"name":"ชลกันต์","txt":"แสดงความคิดเห็น","title":"สอบถามหลักสูตรการจัดอบรม","image":"http://www.munjeed.com/image_news/2012-07-08/Thairath_78201283357PM.jpg"},{"name":"Sattaboot","txt":"แสดงความคิดเห็น","title":"มีกี่หลักสูตครับ","image":""},{"name":"Jony","txt":"แสดงความคิดเห็น","title":"หลักสูตรการสอน ","image":""},{"name":"Jony","txt":"แสดงความคิดเห็น","title":"หลักสูตรการสอน ","image":""},{"name":"Jony wallker","txt":"แสดงความคิดเห็น","title":"หลักสูตรการสอน ","image":""}]
     */

    private int status;
    /**
     * name : ชลกันต์
     * txt : แสดงความคิดเห็น
     * title : สอบถามหลักสูตรการจัดอบรม
     * image : http://www.munjeed.com/image_news/2012-07-08/Thairath_78201283357PM.jpg
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
    }
}
