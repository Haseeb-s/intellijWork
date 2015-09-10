package sample;

import java.util.Arrays;
import java.util.Random;

/**
 * Created by Haseeb on 9/8/2015.
 */
public class Dispenser {
    public int selection;
    public String Name;
    public String Type;
    public String Price;
    public String Nutrition;
    public String itemList[][];

    public Dispenser (int selection, String Name, String Type, String Price,String Nutrition){
        this.selection = selection;
        this.Name = Name;
        this.Type = Type;
        this.Price = Price;
        this.Nutrition = Nutrition;
    }
    public Dispenser() {
        Random rand = new Random();
        int randomMachine = rand.nextInt((1) + 1);
        FoodInfo test = new FoodInfo(randomMachine);
        itemList = test.fileInfo;
        for (int i = 0; i < 10; i++) {
            this.selection = (i + 1);
            this.Name = itemList[i][0];
            this.Type = itemList[i][1];
            this.Price = itemList[i][2];
            this.Nutrition = itemList[i][3];
        }
    }

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    public String getNutrition() {
        return Nutrition;
    }

    public void setNutrition(String nutrition) {
        Nutrition = nutrition;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    @Override
    public String toString() {
        return "Dispenser{" +
                "itemList=" + Arrays.toString(itemList) +
                '}';
    }
}

