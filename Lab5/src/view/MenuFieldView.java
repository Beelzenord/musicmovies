/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import controller.Controller;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;

public class MenuFieldView extends MenuBar{
    private Controller controller;
    private MenuItem save;
    private MenuItem saveAs;
    private MenuItem load;
    private MenuItem exit;
    
    public MenuFieldView(Controller controller) {
        super();
        this.controller = controller;
        initView();
    }

    private void initView() {
        Menu menu = new Menu("File");
        save = new MenuItem("Save");
        saveAs = new MenuItem("Save As");
        load = new MenuItem("Load");
        exit = new MenuItem("Exit");
        menu.getItems().add(save);
        menu.getItems().add(saveAs);
        menu.getItems().add(load);
        menu.getItems().add(exit);
        this.getMenus().addAll(menu);
        
        addActionHandlers();
    }
    
    private void addActionHandlers() {
        save.setOnAction((ActionEvent event) -> {
            try {
                controller.saveToFile();
            } catch (IOException ex) {
            }
        });
        
        saveAs.setOnAction((ActionEvent event) -> {
            try {
                controller.saveAsToFile();
            } catch (IOException ex) {
            }
        });
        
        load.setOnAction((ActionEvent event) -> {
            try {
                controller.loadFromFile();
            } catch (IOException ex) {
            }
        });
        
        exit.setOnAction((ActionEvent event) -> {
            controller.exitProgram();
        });
    }
}
