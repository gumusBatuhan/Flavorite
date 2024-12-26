package Entity;

public class Product {
    private int id;
    private String name;
    private String materials;
    private String preparation;
    private CATEGORY category;

    //Databse'deki enum tipiyle birebir aynı olmak zorunda
    public enum CATEGORY{
        Etyemeği,
        Çorba,
        Aperatifler,
        Bakliyatyemeği,
        Hamurişi,
        Makarna,
        Pilav,
        Tatlılar
    }

    //Boş Constructor
    public Product() {
    }

    //Getter ve Setter'lar
    public int getId() {return id;}

    public void setId(int id) {this.id = id;}

    public String getName() {return name;}

    public void setName(String name) {this.name = name;}

    public String getMaterials() {return materials;}

    public void setMaterials(String materials) {this.materials = materials;}

    public String getPreparation() {return preparation;}

    public void setPreparation(String preparation) {this.preparation = preparation;}

    public CATEGORY getCategory() {return category;}

    public void setCategory(CATEGORY category) {this.category = category;}

}
