package search.Model;

public class Person {
    private String name , aboutUser, image;

    public Person() {

    }

    public Person(String name, String aboutUser, String image) {
        this.name = name;
        this.aboutUser = aboutUser;
        this.image = image;
        }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAboutUser() {
        return aboutUser;
    }

    public void setAboutUser(String aboutUser) {
        this.aboutUser = aboutUser;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}

