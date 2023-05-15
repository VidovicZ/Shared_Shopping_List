package zoran.vidovic.shoppingList;

public class Lists {


    private String Name;
    private String Shared;


    public Lists(String N, String S ){
        this.Name = N;
        this.Shared = S;
    }
    public String getName() {
        return Name;
    }
    public void setName(String name) {
        this.Name = name;}

    public String getShared() {
        return Shared;
    }

    public void setShared(String Shared) {
        this.Shared = Shared;}
}
