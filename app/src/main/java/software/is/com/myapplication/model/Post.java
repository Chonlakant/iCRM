package software.is.com.myapplication.model;

import java.util.List;

/**
 * Created by root1 on 12/20/15.
 */
public class Post {

    /**
     * status : 0
     * post : [{"code":"001","title":"","details":"220-240V.","file_img":"http://192.168.1.141/i_community/uploads/1453258652851.jpg","OWNER":"IS","count":"001"}]
     */

    private int status;
    /**
     * code : 001
     * title :
     * details : 220-240V.
     * file_img : http://192.168.1.141/i_community/uploads/1453258652851.jpg
     * OWNER : IS
     * count : 001
     */

    private List<PostEntity> post;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setPost(List<PostEntity> post) {
        this.post = post;
    }

    public int getStatus() {
        return status;
    }

    public List<PostEntity> getPost() {
        return post;
    }

    public static class PostEntity {
        private String code;
        private String title;
        private String details;
        private String file_img;
        private String OWNER;
        private String count;

        public void setCode(String code) {
            this.code = code;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setDetails(String details) {
            this.details = details;
        }

        public void setFile_img(String file_img) {
            this.file_img = file_img;
        }

        public void setOWNER(String OWNER) {
            this.OWNER = OWNER;
        }

        public void setCount(String count) {
            this.count = count;
        }

        public String getCode() {
            return code;
        }

        public String getTitle() {
            return title;
        }

        public String getDetails() {
            return details;
        }

        public String getFile_img() {
            return file_img;
        }

        public String getOWNER() {
            return OWNER;
        }

        public String getCount() {
            return count;
        }
    }
}
