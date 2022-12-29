package views;

import javafx.collections.FXCollections;
import javafx.scene.control.*;
import javafx.scene.layout.Pane;
import javafx.geometry.Pos;
import models.ElectronicStore;
import models.Product;
import java.util.HashMap;

public class ElectronicStoreView extends Pane {
    private ElectronicStore model;  // model to which this view is attached

    // The user interface components needed by the controller
    private Label summaryLabel, stockLabel, cartLabel, numOfSalesLabel, revenueLabel, saleLabel, popularItemsLabel;
    private Button addButton, removeButton, resetButton, completeButton;
    private TextField numOfSalesField, revenueField, saleField;
    private ListView<Product> stockList, popularList;
    private ListView<String> cartList;

    // public methods to allow access to window components
    public ListView<Product> getStockList() { return stockList; }
    public ListView<String> getCartList() { return cartList; }
    public Button getAddButton() { return addButton; }
    public Button getRemoveButton() { return removeButton; }
    public Button getResetButton() { return resetButton; }
    public Button getCompleteButton() { return completeButton; }

    public ElectronicStoreView(ElectronicStore aStore) {
        model = aStore; // Store the model for access later

        // Create the labels
        summaryLabel = new Label("Store Summary:");
        summaryLabel.relocate(10, 5);
        summaryLabel.setPrefSize(170, 30);
        summaryLabel.setAlignment(Pos.CENTER);
        stockLabel = new Label("Store Stock:");
        stockLabel.relocate(190, 5);
        stockLabel.setPrefSize(290, 30);
        stockLabel.setAlignment(Pos.CENTER);
        cartLabel = new Label("Current Cart:");
        cartLabel.relocate(490, 5);
        cartLabel.setPrefSize(290, 30);
        cartLabel.setAlignment(Pos.CENTER);
        numOfSalesLabel = new Label("# Sales: ");
        numOfSalesLabel.relocate(10, 35);
        numOfSalesLabel.setPrefSize(75, 30);
        numOfSalesLabel.setAlignment(Pos.CENTER_RIGHT);
        revenueLabel = new Label("Revenue: ");
        revenueLabel.relocate(10, 70);
        revenueLabel.setPrefSize(75, 30);
        revenueLabel.setAlignment(Pos.CENTER_RIGHT);
        saleLabel = new Label("$ / Sale: ");
        saleLabel.relocate(10, 105);
        saleLabel.setPrefSize(75, 30);
        saleLabel.setAlignment(Pos.CENTER_RIGHT);
        popularItemsLabel = new Label("Most Popular Items:");
        popularItemsLabel.relocate(10, 135);
        popularItemsLabel.setPrefSize(170, 30);
        popularItemsLabel.setAlignment(Pos.CENTER);

        // Create the textFields
        numOfSalesField = new TextField();
        numOfSalesField.relocate(85, 35);
        numOfSalesField.setPrefSize(95, 30);
        numOfSalesField.setEditable(false);
        revenueField = new TextField();
        revenueField.relocate(85, 70);
        revenueField.setPrefSize(95, 30);
        revenueField.setEditable(false);
        saleField = new TextField();
        saleField.relocate(85, 105);
        saleField.setPrefSize(95, 30);
        saleField.setEditable(false);

        // Create the buttons
        resetButton = new Button("Reset Store");
        resetButton.relocate(22.5, 340);
        resetButton.setPrefSize(145, 45);
        addButton = new Button("Add to Cart");
        addButton.relocate(262.5, 340);
        addButton.setPrefSize(145, 45);
        removeButton = new Button("Remove from Cart");
        removeButton.relocate(490, 340);
        removeButton.setPrefSize(145, 45);
        completeButton = new Button("Complete Sale");
        completeButton.relocate(635, 340);
        completeButton.setPrefSize(145, 45);

        // Create the ListViews
        stockList = new ListView<>();
        stockList.relocate(190, 35);
        stockList.setPrefSize(290, 300);
        cartList = new ListView<>();
        cartList.relocate(490, 35);
        cartList.setPrefSize(290, 300);
        popularList = new ListView<>();
        popularList.relocate(10, 165);
        popularList.setPrefSize(170, 170);

        // Add all the components to the window
        getChildren().addAll(summaryLabel, stockLabel, cartLabel, numOfSalesLabel, revenueLabel,
                saleLabel, popularItemsLabel, numOfSalesField, revenueField, saleField, resetButton,
                addButton, removeButton, completeButton, stockList, cartList, popularList);

        // Call update() to make sure model contents are shown
        update(model.getAvailableStock(), model.popularProduct(), model.getCurrentCart(), model.getCurrentCartRevenue(),
                model.getNumOfSales(), model.getSale(), model.getRevenue());
    }
    // This method is called whenever the model changes, to make
    // sure that the view shows the model's current state
    public void update(Product[] stock, Product[] popularItems, HashMap<Product,Integer> currentCart,
                       double currentCartRevenue, int numOfSales, double sale, double revenue) {
        stockList.setItems(FXCollections.observableArrayList(stock));
        popularList.setItems(FXCollections.observableArrayList(popularItems));
        String[] cartString = new String[currentCart.size()];
        int index = 0;
        for (Product p: currentCart.keySet()) {
            cartString[index] = currentCart.get(p) + " x " + p;
            index++;
        }
        int selectedIndex = cartList.getSelectionModel().getSelectedIndex();
        cartList.setItems(FXCollections.observableArrayList(cartString));
        cartList.getSelectionModel().select(selectedIndex);

        cartLabel.setText(String.format("Current Cart ($%.2f):", currentCartRevenue));
        numOfSalesField.setText("" + numOfSales);
        if (sale > 0)
            saleField.setText(String.format("%.2f", sale));
         else
            saleField.setText("N/A");
        revenueField.setText(String.format("%.2f", revenue));
        //Set addButtons to disable when no item is selected in the stockList,
        //Set removeButtons to disable when no item is selected in the cartList,
        //Set completeButton to disable when there are no items in the cart.
        addButton.setDisable(stockList.getSelectionModel().getSelectedItem() == null);
        removeButton.setDisable(cartList.getSelectionModel().getSelectedIndex() < 0);
        completeButton.setDisable(currentCart.size() <= 0);
    }
}


