package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.awt.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Random;
import java.sql.Time;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    public Button adminButton, purchaseBut, balanceButton;
    public TableColumn<Dispenser, String> displayPrice, displayName, displayNutrition, displayType;
    public TableColumn<Dispenser, Integer> displaySelection;
    public Label quantityBar;
    public TextField inputBalance, finalDisplay, displayedTime, StatusDisplay;
    public AnchorPane mainPane;
    public PasswordField passwordField;
    public Text vendMachine;
    public TableView table;
    public int[] inven;
    public String itemList[][];
    public ChoiceBox<Integer> selectionBox;
    public double balance;
    public int machine;
    public Date today = Calendar.getInstance().getTime();
    public SimpleDateFormat cTime = new SimpleDateFormat("yyyy-MM-dd-HH.mm");
    public String currentTime = cTime.format(today);

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        System.out.println(currentTime);
        fillData();
        setShop();
        getSelection();
    }

    public void adminMode() {
        if (passwordField.getText().equals("password")) {
            table.setEditable(true);
            finalDisplay.setText("Admin mode is ON.\n Edit the text file to change inventory.");
            displayNutrition.setEditable(true);
            displayName.setEditable(true);
            displaySelection.setEditable(true);
            displayPrice.setEditable(true);
            displayType.setEditable(true);

        }
    }

    public void writeArray() throws IOException {
        if (machine == 1) {
            int[] invArray = new int[50];
            FileInputStream fstream = new FileInputStream("AStock.txt");
            BufferedReader read = new BufferedReader(new InputStreamReader(fstream));
            String line = read.readLine();
            int x = 0;
            while (line != null) {

                int temp = Integer.parseInt(line);
                invArray[x] = temp;
                line = read.readLine();
                x++;
            }
            inven = invArray;
            read.close();
        }
        if (machine == 0) {
            int[] invArray = new int[50];
            FileInputStream fstream = new FileInputStream("BStock.txt");
            BufferedReader read = new BufferedReader(new InputStreamReader(fstream));
            String line = read.readLine();
            int x = 0;
            while (line != null) {

                int temp = Integer.parseInt(line);
                invArray[x] = temp;
                line = read.readLine();
                x++;
            }
            inven = invArray;
            read.close();
        }
    }

    public void changeDisplayInv() throws IOException {
        writeArray();
        try {
            int choice = selectionBox.getValue();
            String string = Integer.toString(inven[choice - 1]);
            quantityBar.setText(string);
        } catch (NullPointerException e) {
            System.out.println("NPE");
        }
    }

    public void computePurchase() throws IOException {
        int choice = selectionBox.getValue();
        double choicePrice = Double.parseDouble(itemList[choice - 1][2]);
        if (choicePrice <= balance) {
            NumberFormat formatter = NumberFormat.getCurrencyInstance();

            finalDisplay.setText("You've purchased a " + itemList[choice - 1][0] + " for " + itemList[choice - 1][2]
                    + ".\n Your remaining balance is " + (formatter.format(this.balance - choicePrice)) + ".");
            this.balance = this.balance - choicePrice;
            changeInv(choice);
        } else {
            finalDisplay.setText("Insufficent funds!");
        }
    }

    public void changeInv(int choice) throws IOException {
        choice--;
        int[] invArray = new int[50];
        if (machine == 1) {
            FileInputStream fstream = new FileInputStream("AStock.txt");
            BufferedReader read = new BufferedReader(new InputStreamReader(fstream));
            String name = ("A_" + currentTime + ".txt");
            File file = new File(name);
            file.createNewFile();
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(file));

            String line = read.readLine();
            int x = 0;
            while (line != null) {
                int temp = Integer.parseInt(line);
                invArray[x] = temp;
                line = read.readLine();
                x++;
            }
            invArray[choice] = invArray[choice] - 1;
            for (int i = 0; i < 10; i++) {
                outputWriter.write(Integer.toString(invArray[i]));
                outputWriter.newLine();
            }
            outputWriter.flush();
            outputWriter.close();
            fstream.close();

            String astock = ("AStock.txt");
            File fileStock = new File(astock);
            fileStock.createNewFile();
            BufferedWriter stockWriter = new BufferedWriter(new FileWriter(fileStock));
            for (int i = 0; i < 10; i++) {
                stockWriter.write(Integer.toString(invArray[i]));
                stockWriter.newLine();
            }
            stockWriter.flush();
            stockWriter.close();
        }
        if (machine == 0) {
            FileInputStream fstream = new FileInputStream("BStock.txt");
            BufferedReader read = new BufferedReader(new InputStreamReader(fstream));
            String name = ("B_" + currentTime + ".txt");
            File file = new File(name);
            BufferedWriter outputWriter = new BufferedWriter(new FileWriter(file));
            String line = read.readLine();
            int x = 0;
            while (line != null) {

                int temp = Integer.parseInt(line);
                invArray[x] = temp;
                line = read.readLine();
                x++;
            }
            invArray[choice] = invArray[choice];
            for (int i = 0; i < 10; i++) {
                outputWriter.write(Integer.toString(invArray[i]));
                outputWriter.newLine();
            }
            outputWriter.flush();
            outputWriter.close();
            fstream.close();
            String Bstock = ("BStock.txt");
            File BStock = new File(Bstock);
            BStock.createNewFile();
            BufferedWriter bStockWriter = new BufferedWriter(new FileWriter(BStock));
            invArray[choice] = invArray[choice];
            for (int i = 0; i < 10; i++) {
                bStockWriter.write(Integer.toString(invArray[i]));
                bStockWriter.newLine();
            }
            bStockWriter.flush();
            bStockWriter.close();
        }
    }

    public void updateBalance() {
        String strBalance = inputBalance.getText();
        double newBalance = Double.parseDouble(strBalance);
        this.balance = newBalance;
        finalDisplay.setText("New Balance is : " + this.balance);
    }

    public void fillData() {
        displaySelection.setCellValueFactory(new PropertyValueFactory<>("selection"));
        displayName.setCellValueFactory(new PropertyValueFactory<>("Name"));
        displayType.setCellValueFactory(new PropertyValueFactory<>("Type"));
        displayPrice.setCellValueFactory(new PropertyValueFactory<>("Price"));
        displayNutrition.setCellValueFactory(new PropertyValueFactory<>("Nutrition"));
        table.setItems(getDispenser());
    }

    public void getSelection() {
        selectionBox.setItems(FXCollections.observableArrayList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10));
    }

    public void setShop() {
        final Random random = new Random();
        final int millisInDay = 24 * 60 * 60 * 1000;
        Time time = new Time((long) random.nextInt(millisInDay));
        String sTime = time.toString();
        displayedTime.setText("Current Time: " + sTime);

        String hourTime = sTime.substring(0, 2);
        int crntTime = Integer.parseInt(hourTime);

        if (crntTime < 8 || crntTime > 22) {
            StatusDisplay.setText("CLOSED");
            purchaseBut.setOpacity(0);
        } else {
            StatusDisplay.setText("OPEN");
        }
    }

    //gets all of dispenser
    public ObservableList<Dispenser> getDispenser() {
        ObservableList<Dispenser> dispensers = FXCollections.observableArrayList();

        Random rand = new Random();
        int randomMachine = rand.nextInt((1) + 1);
        machine = randomMachine;
        FoodInfo test = new FoodInfo(randomMachine);
        if (machine == 0) {
            vendMachine.setText("Machine B");
        }
        itemList = test.fileInfo;
        int x = 1;
        dispensers.add(new Dispenser(x, itemList[0][0], itemList[0][1], itemList[0][2], itemList[0][3]));
        dispensers.add(new Dispenser((++x), itemList[1][0], itemList[1][1], itemList[1][2], itemList[1][3]));
        dispensers.add(new Dispenser((++x), itemList[2][0], itemList[2][1], itemList[2][2], itemList[2][3]));
        dispensers.add(new Dispenser((++x), itemList[3][0], itemList[3][1], itemList[3][2], itemList[3][3]));
        dispensers.add(new Dispenser((++x), itemList[4][0], itemList[4][1], itemList[4][2], itemList[4][3]));
        dispensers.add(new Dispenser((++x), itemList[5][0], itemList[5][1], itemList[5][2], itemList[5][3]));
        dispensers.add(new Dispenser((++x), itemList[6][0], itemList[6][1], itemList[6][2], itemList[6][3]));
        dispensers.add(new Dispenser((++x), itemList[7][0], itemList[7][1], itemList[7][2], itemList[7][3]));
        dispensers.add(new Dispenser((++x), itemList[8][0], itemList[8][1], itemList[8][2], itemList[8][3]));
        dispensers.add(new Dispenser((++x), itemList[9][0], itemList[9][1], itemList[9][2], itemList[9][3]));
        return dispensers;
    }

}
