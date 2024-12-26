package Entity;

public class Admin {
    private int id;
    private String mail;
    private String passWord;

    //Boş Constructor
    public Admin() {
    }

    //Getter ve Setter'lar
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getMail() {return mail;}

    public void setMail(String mail) {this.mail = mail;}

    public String getPassWord() {return passWord;}

    public void setPassWord(String passWord) {this.passWord = passWord;}

}
