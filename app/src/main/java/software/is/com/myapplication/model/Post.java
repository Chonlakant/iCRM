package software.is.com.myapplication.model;

import java.util.List;

/**
 * Created by root1 on 12/20/15.
 */
public class Post {

    /**
     * status : 0
     * bg : #70DEED
     * post : [{"code":"213","title":"jkk","details":"jjjm","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"212","title":"okok","details":"okok","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"211","title":"ggg","details":"gghhn","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"210","title":"","details":"","file_img":"http://192.168.1.141/i_community/uploads/1453258893348.jpg","status_img":1,"OWNER":"IS","count":"120"},{"code":"209","title":"hh","details":"hhhh","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"208","title":"kkk","details":"hhh","file_img":"http://192.168.1.141/i_community/uploads/1453258714732.jpg","status_img":1,"OWNER":"IS","count":"120"},{"code":"207","title":"yyy","details":"iiio","file_img":"http://192.168.1.141/i_community/uploads/1453258701260.jpg","status_img":1,"OWNER":"IS","count":"120"},{"code":"206","title":"","details":"","file_img":"http://192.168.1.141/i_community/uploads/1453258687014.jpg","status_img":1,"OWNER":"IS","count":"120"},{"code":"205","title":"","details":"","file_img":"http://192.168.1.141/i_community/uploads/1453258652851.jpg","status_img":1,"OWNER":"IS","count":"120"},{"code":"204","title":"yhhh","details":"fggh","file_img":"http://192.168.1.141/i_community/uploads/1453258470649.jpg","status_img":1,"OWNER":"IS","count":"120"},{"code":"203","title":"","details":"","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"202","title":"","details":"","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"201","title":"gggg","details":"fff","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"200","title":"ù","details":"","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"199","title":"ù","details":"","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"198","title":"ù","details":"","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"197","title":"","details":"","file_img":"http://192.168.1.141/i_community/uploads/1453174044666.jpg","status_img":1,"OWNER":"IS","count":"120"},{"code":"196","title":"ù","details":"","file_img":null,"status_img":0,"OWNER":"IS","count":"120"},{"code":"195","title":"ù","details":"","file_img":null,"status_img":0,"OWNER":"IS","count":"120"}]
     */

    private int status;
    private String bg;
    /**
     * code : 213
     * title : jkk
     * details : jjjm
     * file_img : null
     * status_img : 0
     * OWNER : IS
     * count : 120
     */

    private List<PostEntity> post;

    public void setStatus(int status) {
        this.status = status;
    }

    public void setBg(String bg) {
        this.bg = bg;
    }

    public void setPost(List<PostEntity> post) {
        this.post = post;
    }

    public int getStatus() {
        return status;
    }

    public String getBg() {
        return bg;
    }

    public List<PostEntity> getPost() {
        return post;
    }

    public static class PostEntity {
        private String code;
        private String title;
        private String details;
        private Object file_img;
        private int status_img;
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

        public void setFile_img(Object file_img) {
            this.file_img = file_img;
        }

        public void setStatus_img(int status_img) {
            this.status_img = status_img;
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

        public Object getFile_img() {
            return file_img;
        }

        public int getStatus_img() {
            return status_img;
        }

        public String getOWNER() {
            return OWNER;
        }

        public String getCount() {
            return count;
        }
    }
}
