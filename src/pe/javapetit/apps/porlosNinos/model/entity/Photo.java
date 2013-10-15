package pe.javapetit.apps.porlosNinos.model.entity;

import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: JavaPetit
 * Date: 06/10/13
 * Time: 10:49 PM
 * To change this template use File | Settings | File Templates.
 */
public class Photo {

    private int idPhoto;
    private String name;
    private String filePath;
    private Child child;
    private Date creationDate;


    public int getIdPhoto() {
        return idPhoto;
    }

    public void setIdPhoto(int idPhoto) {
        this.idPhoto = idPhoto;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    public Child getChild() {
        return child;
    }

    public void setChild(Child child) {
        this.child = child;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }
}
