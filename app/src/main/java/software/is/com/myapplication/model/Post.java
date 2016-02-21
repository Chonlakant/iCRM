package software.is.com.myapplication.model;

import java.util.List;

/**
 * Created by root1 on 12/20/15.
 */
public class Post {

    /**
     * status : 0
     * bg : #ECECEC
     * post : [{"code":"16","title":"jjj","details":"jj","file_img":"","status_img":0,"OWNER":"IS","count":"120"},{"code":"15","title":"thanks ","details":"oo","file_img":"","status_img":0,"OWNER":"IS","count":"120"},{"code":"13","title":"ทดสอบ","details":"ทดสอบ","file_img":"","status_img":0,"OWNER":"IS","count":"120"},{"code":"11","title":"เพชร\u201d ที่ขึ้นชื่อว่าแข็งแกร่งที่สุ","details":"การสังเคราะห์เพชรสามารถทำได้จากวัสดุหลายชนิด อาทิเช่น แดน ฟรอสท์ นักธรณีวิทยาชาวเยอรมัน ได้ทดลองสร้างเพชรจากเนยถั่วซึ่งถือเป็นแหล่งที่มีคาร์บอนและแก๊สคาร์บอนไดออกไซด์มาก โดยใช้อุณหภูมิและความดันสูงในการเปลี่ยนแก๊สให้กลายเป็นเพชรได้ นอกจากนี้ยังมีรายงานว่า เหล้าเตกีล่าร์สามารถใช้สร้างเพชรได้ ด้วยวิธีเผาเหล้าภายใต้ความดันจนกลายเป็นเพชรที่สามารถใช้เป็นฉนวนไฟฟ้าที่ดีจึงกล่าวได้ว่า เพชรมีประโยชน์มากมายทั้งจากความสวยงาม และความแข็งแกร่ง หากแต่มีราคาแพงและสังเคราะห์ได้ยาก","file_img":"http://todayissoftware.com/i_community/uploads/1453867647301.jpg","status_img":1,"OWNER":"IS","count":"120"},{"code":"9","title":"ทดสอบ","details":"ทดสอบ","file_img":"","status_img":0,"OWNER":"IS","count":"120"}]
     */

    private int status;
    private String bg;
    /**
     * code : 16
     * title : jjj
     * details : jj
     * file_img :
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
        private String file_img;
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

        public void setFile_img(String file_img) {
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

        public String getFile_img() {
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
