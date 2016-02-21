package software.is.com.myapplication.event;

/**
 * Created by marcus on 4/20/2015
 */

public class ImagesRequestedEvent {
        String vendor;

    public ImagesRequestedEvent(String vendor) {
        this.vendor = vendor;
    }

    public String getVendor() {
        return vendor;
    }

    public void setVendor(String vendor) {
        this.vendor = vendor;
    }
}
