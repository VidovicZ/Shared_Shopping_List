package zoran.vidovic.shoppingList;

public class Items {


    private String Items;
    private String Id;
    private Boolean Box;


    public Items(String I, Boolean B,String Id ){
        this.Items = I;
        this.Box = B;
        this.Id = Id;
    }
    public String getItems() {
        return Items;
    }
    public void setItems(String Item) {
        this.Items = Item;
    }

    public Boolean getBox() {
        return Box;
    }
    public void setBox(Boolean Box) {
        this.Box = Box;
    }

    public String getID(){ return Id;}

    public void setID(String uID) {this.Id = Id;}
}

