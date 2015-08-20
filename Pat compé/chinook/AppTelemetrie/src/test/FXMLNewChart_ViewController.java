/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author club
 */
public class FXMLNewChart_ViewController extends AnchorPane implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML TextField XaxisLabel, YaxisLabel;
    @FXML AnchorPane pane;
    
    @FXML
    private void ChartButton_handle(ActionEvent event)
    {
        XaxisLabel.getText();        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        
        
    }    
    
    public FXMLNewChart_ViewController()
    {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("FXMLNewChart_View.fxml"));
        fxmlLoader.setRoot(this);
        //fxmlLoader.setController(this);
        try
        {
            fxmlLoader.load();            
        }
        catch (IOException exception)
        {
            throw new RuntimeException(exception);
        }
    }
    
}
