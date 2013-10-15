package pe.javapetit.apps.porlosNinos.model.entity;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 06/10/13
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Child {

    private int idChild;
    private String name;
    private String description;
    private String defaultPhotoPath;
    private double latitude;
    private double longitude;

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public int getIdChild() {
        return idChild;
    }

    public void setIdChild(int idChild) {
        this.idChild = idChild;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDefaultPhotoPath() {
        return defaultPhotoPath;
    }

    public void setDefaultPhotoPath(String defaultPhotoPath) {
        this.defaultPhotoPath = defaultPhotoPath;
    }
}
