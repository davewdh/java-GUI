package controllers;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.ElectronicStore;
import models.Product;
import views.ElectronicStoreView;

public class ElectronicStoreApp extends Application {
    private ElectronicStore model;
    private ElectronicStoreView view;

    public void start(Stage primaryStage) {
        model = ElectronicStore.createStore();
        view = new ElectronicStoreView(model);

        // Plug in the event handlers
        view.getAddButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) { handleAddToCart(); }
        });

        view.getRemoveButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) { handleRemoveFromCart(); }
        });

        view.getCompleteButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) { handleCompleteSale(); }
        });

        view.getResetButton().setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent actionEvent) { handleResetStore(); }
        });

        view.getStockList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                handleStockSelection();
            }
        });

        view.getCartList().setOnMousePressed(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent mouseEvent) {
                handleCartSelection();
            }
        });

        primaryStage.setTitle("Electronic Store Application - " + model.getName());
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(view, 800,400));
        primaryStage.show();
    }

    //This method will add the selected product to the cart if it is not null,
    //and update the view.
    private void handleAddToCart() {
        Product selection = view.getStockList().getSelectionModel().getSelectedItem();
        if (selection != null)
            model.addToCart(selection);
        view.update(model.getAvailableStock(), model.popularProduct(), model.getCurrentCart(), model.getCurrentCartRevenue(),
                model.getNumOfSales(), model.getSale(), model.getRevenue());
    }

    //This method will remove the selected index of the cartList,
    //and update the view.
    private void handleRemoveFromCart() {
        int selection = view.getCartList().getSelectionModel().getSelectedIndex();
        if (selection < 0)
            return;
        model.removeFromCart(selection);
        view.update(model.getAvailableStock(), model.popularProduct(), model.getCurrentCart(), model.getCurrentCartRevenue(),
                model.getNumOfSales(), model.getSale(), model.getRevenue());
    }

    //When complete button is clicked, this method will call the sellCart() method in the model class,
    //and update the view.
    private void handleCompleteSale() {
        model.sellCart();
        view.update(model.getAvailableStock(), model.popularProduct(), model.getCurrentCart(), model.getCurrentCartRevenue(),
                model.getNumOfSales(), model.getSale(), model.getRevenue());
    }

    //When reset button is clicked, the model will reset to its initial state,
    //cart will be emptied, and update the view.
    private void handleResetStore() {
        model.getCurrentCart().clear();
        model = ElectronicStore.createStore();
        view.update(model.getAvailableStock(), model.popularProduct(), model.getCurrentCart(), model.getCurrentCartRevenue(),
                model.getNumOfSales(), model.getSale(), model.getRevenue());
    }

    //When item selected in the stockList, update the view to enable the add button.
    private void handleStockSelection() {
        view.update(model.getAvailableStock(), model.popularProduct(), model.getCurrentCart(), model.getCurrentCartRevenue(),
                model.getNumOfSales(), model.getSale(), model.getRevenue());
    }

    //When item selected in the cartList, update the view to enable the remove button.
    private void handleCartSelection() {
        view.update(model.getAvailableStock(), model.popularProduct(), model.getCurrentCart(), model.getCurrentCartRevenue(),
                model.getNumOfSales(), model.getSale(), model.getRevenue());
    }

    public static void main(String[] args) {
        launch(args);
    }
}