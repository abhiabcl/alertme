package alertme.flavortech.com.alertme.util;

/**
 * Created by etbdefi on 1/12/2016.
 */
public class TripData {

    private long id;
    public String TripTitle;
    public String TripPickup;
    public String TripDrop;
    public String TripDuration;
    public String TripImgUri;
    public String TripStatus;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTripTitle() {
        return TripTitle;
    }

    public void setTripTitle(String tripTitle) {
        TripTitle = tripTitle;
    }

    public String getTripPickup() {
        return TripPickup;
    }

    public void setTripPickup(String tripPickup) {
        TripPickup = tripPickup;
    }

    public String getTripDrop() {
        return TripDrop;
    }

    public void setTripDrop(String tripDrop) {
        TripDrop = tripDrop;
    }

    public String getTripDuration() {
        return TripDuration;
    }

    public void setTripDuration(String tripDuration) {
        TripDuration = tripDuration;
    }

    public String getTripImgUri() {
        return TripImgUri;
    }

    public void setTripImgUri(String tripImgUri) {
        TripImgUri = tripImgUri;
    }

    public String getTripStatus() {
        return TripStatus;
    }

    public void setTripStatus(String tripStatus) {
        TripStatus = tripStatus;
    }
}
