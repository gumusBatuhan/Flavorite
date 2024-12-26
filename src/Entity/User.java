package Entity;

public class User {
    private int id;
    private String name;
    private String surName;
    private String mail;
    private String passWord;
    private GENDER gender;

    public enum GENDER{
        Erkek,
        Kadın
    }

    //Getter ve Setter'lar
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getSurName() {return surName;}

    public void setSurName(String surName) {this.surName = surName;}

    public String getMail() {return mail;}

    public void setMail(String mail) {this.mail = mail;}

    public String getPassWord() {return passWord;}

    public void setPassWord(String passWord) {this.passWord = passWord;}

    public GENDER getGender() {return gender;}

    public void setGender(GENDER gender) {this.gender = gender;}

}
