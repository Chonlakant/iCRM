package software.is.com.myapplication;

import java.util.List;

/**
 * Created by ELMTRIX on 5/2/2559.
 */
public class Data {
    private int success;

    private List<PostEntity> post;

    public void setStatus(int status) {
        this.success = status;
    }

    public void setPost(List<PostEntity> post) {
        this.post = post;
    }

    public int getStatus() {
        return success;
    }

    public List<PostEntity> getPost() {
        return post;
    }

    public static class PostEntity {
        private String create_date;
        private String create_by;
        private String img_path;
        private String comment_content;

        public void setCreate_date(String create_date) {
            this.create_date = create_date;
        }

        public void setCreate_by(String create_by) {
            this.create_by = create_by;
        }

        public void setImg_path(String img_path) {
            this.img_path = img_path;
        }

        public void setComment_content(String img_path) {
            this.comment_content = comment_content;
        }


        public String getCreate_date() {
            return create_date;
        }

        public String getCreate_by() {
            return create_by;
        }

        public String getImg_path() {
            return img_path;
        }

        public String getComment_content() {
            return comment_content;
        }
    }
}
