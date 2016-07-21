/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinook;

import gnu.io.CommPortIdentifier;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author club
 */
public class FXMLDocumentController implements Initializable {
    
    @FXML SplitMenuButton BaudRateButton;
    @FXML SplitMenuButton StopBitsButton;
    @FXML SplitMenuButton XGraphButton;
    @FXML SplitMenuButton YGraphButton;
    @FXML TextField GraphNameTextField;
    @FXML Button AddGraphButton;
    @FXML PieChart gearPieChart;
    @FXML PieChart pitchChart;
    @FXML PieChart rpmRoueChart;
    @FXML PieChart rpmEolienneChart;
    @FXML PieChart vitVentChart;
    @FXML PieChart poutChart;
    @FXML PieChart torqueChart;
    @FXML PieChart dirVentChart;
    @FXML PieChart thrustChart;
    @FXML PieChart dirRelativeMatChart;
    @FXML Label lbGear;
    @FXML Label lbPitch;
    @FXML Label lbRPMRoue;
    @FXML Label lbRPMEolienne;
    @FXML Label lbVitVent;
    @FXML Label lbPout;
    @FXML Label lbTorque;
    @FXML Label lbDirVent;
    @FXML Label lbThrust;
    @FXML Label lbDirRelativeMat;
    @FXML TextField textFieldVoltage;
    @FXML TextField textFieldAmp;
    @FXML TextField textFieldCommand;
    
    @FXML ToggleButton serialButton;
    @FXML ToggleButton logButton;
    
    
    @FXML public void handleBaudRate(ActionEvent event){ BaudRateButton.setText( ((MenuItem)event.getSource()).getText() ); }
    @FXML public void handleStopBits(ActionEvent event){ StopBitsButton.setText( ((MenuItem)event.getSource()).getText() ); }
    @FXML public void handleXGraphItem(ActionEvent event){ XGraphButton.setText( ((MenuItem)event.getSource()).getText() ); }
    @FXML public void handleYGraphItem(ActionEvent event){ YGraphButton.setText( ((MenuItem)event.getSource()).getText() ); }
    
    SerialCommunication serialComm;
    static CommPortIdentifier portId;
    static Enumeration portList;
    int startlogindex = 0;
    
    String PORT_NAME = "COM8";
    
    DataLogger dataLogger;
    
    ArrayList<FXMLGraphController> Graphs;
    
    //Gauge datas
    ObservableList<PieChart.Data> gearPieChartData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> pitchPieChartData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> rpmRouePieChartData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> rpmEoliennePieChartData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> vitVentPieChartData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> poutPieChartData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> torquePieChartData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> dirVentPieChartData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> thrustPieChartData = FXCollections.observableArrayList();
    ObservableList<PieChart.Data> dirRelativeMatPieChartData = FXCollections.observableArrayList();
    
    
    @FXML public void openCSVFile(ActionEvent event)
    {
        System.out.println("testopen");
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Resource File");
        File filechosen = fileChooser.showOpenDialog(null);
        
        System.out.println(filechosen.getAbsolutePath());
        if( filechosen != null)
            dothething(filechosen.getAbsolutePath());
    }
    
    public void dothething(String filepath)
    {
        //Input file which needs to be parsed
        String fileToParse = filepath;
        BufferedReader fileReader = null;
              
        try
        {
            String line = "";
            //Create the file reader
            fileReader = new BufferedReader(new FileReader(fileToParse));
             
            //Read the file line by line
            while ((line = fileReader.readLine()) != null)
            {
                //Get all tokens available in line
                String[] tokens = line.split(",");
                
                    //Print all tokens
                    DataCSV dat = new DataCSV();
                    dataLogger.logData(new Data("Id",               Float.parseFloat(tokens[0])));
                    dataLogger.logData(new Data("Gear",             Float.parseFloat(tokens[1])));
                    dataLogger.logData(new Data("VitVent",          Float.parseFloat(tokens[2])));
                    dataLogger.logData(new Data("DirVent",          Float.parseFloat(tokens[3])));
                    dataLogger.logData(new Data("DirRelativeMat",   Float.parseFloat(tokens[4])));
                    dataLogger.logData(new Data("RPMEolienne",      Float.parseFloat(tokens[5])));
                    dataLogger.logData(new Data("RPMRoue",          Float.parseFloat(tokens[6])));
                    dataLogger.logData(new Data("Pitch",            Float.parseFloat(tokens[7])));
                    dataLogger.logData(new Data("Thrust",           Float.parseFloat(tokens[8])));
                    dataLogger.logData(new Data("Torque",           Float.parseFloat(tokens[9])));
                    dataLogger.logData(new Data("Power",            Float.parseFloat(tokens[10])));
                    dataLogger.logData(new Data("Temps",            Float.parseFloat(tokens[11])));                    
                    dataLogger.load();
                    for(int i = 0; i < 11 ; ++i)
                        System.out.print(tokens[i] + '\t');
                    System.out.println();
            }        
            
            
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        finally
        {
            try {
                fileReader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    
    @FXML public void handleStartHorn(MouseEvent event)
    {
        serialComm.sendSerialMessage("k");
    }
    
    @FXML public void handleStopHorn(MouseEvent event)
    {
        serialComm.sendSerialMessage("l");
    }
    
    @FXML public void handleSendCommand(ActionEvent event)
    {
        serialComm.sendSerialMessage(textFieldCommand.getText());
    }
    
    @FXML public void handleAddGraphButton(ActionEvent event)throws IOException {
        addGraph(GraphNameTextField.getText(), XGraphButton.getText(), YGraphButton.getText());
    }
    
    @FXML public void startSerialComm(ActionEvent event)
    {
        //<editor-fold desc="Serial Comm Init">
        portList = CommPortIdentifier.getPortIdentifiers();
        while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                System.out.println("Searching for connection..");
                if (portId.getName().equals(PORT_NAME)) {
                    
                    System.out.println("Connection to " + PORT_NAME + " established.");
                    serialComm = new SerialCommunication(portId, dataLogger);
                }
            }
        }
        //</editor-fold>
    }
    
    @FXML public void handleSerialButton(ActionEvent event)
    {
        if(serialButton.isSelected())
        {
            portList = CommPortIdentifier.getPortIdentifiers();
            while (portList.hasMoreElements()) {
            portId = (CommPortIdentifier) portList.nextElement();
            if (portId.getPortType() == CommPortIdentifier.PORT_SERIAL) {
                 if (portId.getName().equals("COM12")) {
			//                if (portId.getName().equals("/dev/term/a")) {
                        serialComm = new SerialCommunication(portId, dataLogger);
                 }
                 else
                 {
                     serialButton.setSelected(false);
                 }
            }
            else
            {
                serialButton.setSelected(false);
            }
        }
        }
        else
        {
            serialComm.close();
        }
    }
    
    @FXML public void stopSerialComm(ActionEvent event)
    {   
        if(serialComm != null) {
            serialComm.close();
            logData();
        }
    }
    
    public void logData()
    {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        Date date = new Date();
        PrintWriter writer = null;
        try{
            writer = new PrintWriter(dateFormat.format(date)+".txt", "UTF-8");
            for(int i = startlogindex; i < dataLogger.DataDirRelativeMat.size(); ++i)
            {
                writer.print( MathFct.round(dataLogger.DataId.get(i).getValue(),2) + "," +
                                MathFct.round(dataLogger.DataGear.get(i).getValue(),2) + "," +
                                MathFct.round(dataLogger.DataVitVent.get(i).getValue(),2) + "," +
                                MathFct.round(dataLogger.DataDirVent.get(i).getValue(),2) + "," +
                                MathFct.round(dataLogger.DataDirRelativeMat.get(i).getValue(),2) + "," +
                                MathFct.round(dataLogger.DataRPMEolienne.get(i).getValue(),2) + "," +
                                MathFct.round(dataLogger.DataRPMRoue.get(i).getValue(),2) + "," +
                                MathFct.round(dataLogger.DataPitch.get(i).getValue(),2) + "," +
                                MathFct.round(dataLogger.DataThrust.get(i).getValue(),2) + "," +
                                MathFct.round(dataLogger.DataTorque.get(i).getValue(),2) + "," +
                                MathFct.round(dataLogger.DataPower.get(i).getValue(),2) + ",");
                                if( i < dataLogger.getData("Temps").size() )
                                    writer.print(MathFct.round(dataLogger.DataTemps.get(i).getValue(),2));
                                writer.println();
                
            }
            
        }  catch (FileNotFoundException | UnsupportedEncodingException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            writer.close();
        }
    }
    
    public FXMLGraphController addGraph(String title, String xAxisTitle, String yAxisTitle) throws IOException
    {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("FXMLGraph.fxml"));
        AnchorPane newWindow = (AnchorPane)loader.load();
        FXMLGraphController controller = loader.getController();
        controller.setMainWindow(this);
        controller.setTitle(title);
        controller.setXaxisTitle(xAxisTitle);
        controller.setYaxisTitle(yAxisTitle);
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);        
        Scene scene = new Scene(newWindow);
        stage.setScene(scene);
        stage.show();
        
        Graphs.add(controller);
        for(int i = 0; i < dataLogger.getData(xAxisTitle).size();++i)
        {
            controller.setTempX(dataLogger.getData(xAxisTitle).get(i));
            controller.setTempY(dataLogger.getData(yAxisTitle).get(i));
        }
        
        return controller;
    }
    
    public void updateData(Data data)//not used?
    {
        for(FXMLGraphController c : Graphs)
        {
            if(c.getTypeX().compareTo(data.getType()) == 0)
                c.setTempX(data);
            if(c.getTypeY().compareTo(data.getType()) == 0)
                c.setTempY(data);
        }
        
        if(data.getType().compareTo("Gear") == 0)
        {
            gearPieChart.getData().get(1).setPieValue(data.getValue()-1);
            gearPieChart.getData().get(2).setPieValue(14-data.getValue());
            lbGear.setText(data.getValueString());
        }
        else if(data.getType().compareTo("Pitch") == 0)
        {
            pitchChart.getData().get(1).setPieValue(data.getValue());
            pitchChart.getData().get(2).setPieValue(14-data.getValue());
            lbPitch.setText(data.getValueString());
        }
        else if(data.getType().compareTo("RPMRoue") == 0)
        {
            rpmRoueChart.getData().get(1).setPieValue(data.getValue());
            rpmRoueChart.getData().get(2).setPieValue(4000-data.getValue());
            lbRPMRoue.setText(data.getValueString());
        }
        else if(data.getType().compareTo("RPMEolienne") == 0)
        {
            rpmEolienneChart.getData().get(1).setPieValue(data.getValue());
            rpmEolienneChart.getData().get(2).setPieValue(1600-data.getValue());
            lbRPMEolienne.setText(data.getValueString());
        }
        else if(data.getType().compareTo("VitVent") == 0)
        {
            vitVentChart.getData().get(1).setPieValue(data.getValue());
            vitVentChart.getData().get(2).setPieValue(14-data.getValue());
            lbVitVent.setText(data.getValueString());
        }
        else if(data.getType().compareTo("Power") == 0)
        {
            poutChart.getData().get(1).setPieValue(data.getValue());
            poutChart.getData().get(2).setPieValue(4000-Math.abs(data.getValue()));
            lbPout.setText(data.getValueString());
        }
        else if(data.getType().compareTo("Torque") == 0)
        {
            torqueChart.getData().get(1).setPieValue(data.getValue());
            torqueChart.getData().get(2).setPieValue(50 - data.getValue());
            lbTorque.setText(data.getValueString());            
        }
        else if(data.getType().compareTo("DirVent") == 0)
        {
            dirVentChart.getData().get(1).setPieValue(180 + data.getValue());
            dirVentChart.getData().get(2).setPieValue(180 - data.getValue());
            lbDirVent.setText(data.getValueString());
        }
        else if(data.getType().compareTo("Thrust") == 0)
        {
            thrustChart.getData().get(1).setPieValue(data.getValue());
            thrustChart.getData().get(2).setPieValue(360 - data.getValue());
            lbThrust.setText(data.getValueString());
        }
        else if(data.getType().compareTo("DirRelativeMat") == 0)
        {
            dirRelativeMatChart.getData().get(1).setPieValue(data.getValue());
            dirRelativeMatChart.getData().get(2).setPieValue(14 - data.getValue());
            lbDirRelativeMat.setText(data.getValueString());
        }
        else if(data.getType().compareTo("BatteryVoltage") == 0)
        {
            textFieldVoltage.setText(data.getValueString());
            if(data.getValue() < 18 || data.getValue() > 26)
                textFieldVoltage.setStyle("-fx-text-inner-color: red;");
            else
                textFieldVoltage.setStyle("-fx-text-inner-color: green;");
                
        }
        else if( data.getType().compareTo("BatteryAmp") == 0)
        {
            textFieldAmp.setText(data.getValueString());
             if(data.getValue() > 5)
                textFieldAmp.setStyle("-fx-text-inner-color: red;");
            else
                textFieldAmp.setStyle("-fx-text-inner-color: green;");
            
        }
    }    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Graphs = new ArrayList<>();
        
        //<editor-fold desc="DataLogger Init">
        dataLogger = new DataLogger(this);
        //</editor-fold>
        
        //<editor-fold desc="Add default graphs">
        try {            
            addGraph("VitVent", "Temps", "Temps");  
            addGraph("Graph2", "Temps", "Power");
            addGraph("Gear vs Time", "Temps", "Gear");
            addGraph("Test graph", "Temps", "RPMEolienne");
            
        } catch (IOException ex) {
            Logger.getLogger(FXMLDocumentController.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>

        //<editor-fold desc="Gear "gauge" setup">
        gearPieChart.setData(gearPieChartData);
        gearPieChartData.add(new PieChart.Data(null, 5));
        gearPieChartData.add(new PieChart.Data(null, 7));
        gearPieChartData.add(new PieChart.Data(null, 7));
        gearPieChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFF;");
        gearPieChart.getData().get(1).getNode().setStyle("-fx-pie-color: #00FF00;");
        gearPieChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        gearPieChart.setRotate(43);
        //</editor-fold>
        
        //<editor-fold desc="Pitch "gauge" setup">
        pitchChart.setData(pitchPieChartData);
        pitchPieChartData.add(new PieChart.Data(null, 7));
        pitchPieChartData.add(new PieChart.Data(null, 9));
        pitchPieChartData.add(new PieChart.Data(null, 9));
        pitchChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFF;");
        pitchChart.getData().get(1).getNode().setStyle("-fx-pie-color: #00FF00;");
        pitchChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        pitchChart.setRotate(43);
        //</editor-fold>
        
        //<editor-fold desc="RPMRoue "gauge" setup">
        rpmRoueChart.setData(rpmRouePieChartData);
        rpmRouePieChartData.add(new PieChart.Data(null, 1300));
        rpmRouePieChartData.add(new PieChart.Data(null, 2000));
        rpmRouePieChartData.add(new PieChart.Data(null, 2000));
        rpmRoueChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFF;");
        rpmRoueChart.getData().get(1).getNode().setStyle("-fx-pie-color: #00FF00;");
        rpmRoueChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        rpmRoueChart.setRotate(47);
        //</editor-fold>
        
        //<editor-fold desc="RPMEolienne "gauge" setup">
        rpmEolienneChart.setData(rpmEoliennePieChartData);
        rpmEoliennePieChartData.add(new PieChart.Data(null, 550));
        rpmEoliennePieChartData.add(new PieChart.Data(null, 800));
        rpmEoliennePieChartData.add(new PieChart.Data(null, 800));
        rpmEolienneChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFF;");
        rpmEolienneChart.getData().get(1).getNode().setStyle("-fx-pie-color: #00FF00;");
        rpmEolienneChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        rpmEolienneChart.setRotate(43);
        //</editor-fold>
        
        //<editor-fold desc="VitVent "gauge" setup">
        vitVentChart.setData(vitVentPieChartData);
        vitVentPieChartData.add(new PieChart.Data(null, 5));
        vitVentPieChartData.add(new PieChart.Data(null, 7));
        vitVentPieChartData.add(new PieChart.Data(null, 7));
        vitVentChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFF;");
        vitVentChart.getData().get(1).getNode().setStyle("-fx-pie-color: #00FF00;");
        vitVentChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        vitVentChart.setRotate(43);
        //</editor-fold>
        
        //<editor-fold desc="Power "gauge" setup">
        poutChart.setData(poutPieChartData);
        poutPieChartData.add(new PieChart.Data(null, 1400));
        poutPieChartData.add(new PieChart.Data(null, 2000));
        poutPieChartData.add(new PieChart.Data(null, 2000));
        poutChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFF;");
        poutChart.getData().get(1).getNode().setStyle("-fx-pie-color: #00FF00;");
        poutChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        poutChart.setRotate(43);
        //</editor-fold>
        
        //<editor-fold desc="Torque "gauge" setup">
        torqueChart.setData(torquePieChartData);
        torquePieChartData.add(new PieChart.Data(null, 17));
        torquePieChartData.add(new PieChart.Data(null, 25));
        torquePieChartData.add(new PieChart.Data(null, 25));
        torqueChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFF;");
        torqueChart.getData().get(1).getNode().setStyle("-fx-pie-color: #00FF00;");
        torqueChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        torqueChart.setRotate(43);
        //</editor-fold>
        
        //<editor-fold desc="DirVent "gauge" setup">
        dirVentChart.setData(dirVentPieChartData);
        dirVentPieChartData.add(new PieChart.Data(null, 120));
        dirVentPieChartData.add(new PieChart.Data(null, 180));
        dirVentPieChartData.add(new PieChart.Data(null, 180));
        dirVentChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFF;");
        dirVentChart.getData().get(1).getNode().setStyle("-fx-pie-color: #00FF00;");
        dirVentChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        dirVentChart.setRotate(43);
        //</editor-fold>
        
        //<editor-fold desc="Thrust "gauge" setup">
        thrustChart.setData(thrustPieChartData);
        thrustPieChartData.add(new PieChart.Data(null, 120));
        thrustPieChartData.add(new PieChart.Data(null, 180));
        thrustPieChartData.add(new PieChart.Data(null, 180));
        thrustChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFF;");
        thrustChart.getData().get(1).getNode().setStyle("-fx-pie-color: #00FF00;");
        thrustChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        thrustChart.setRotate(43);
        //</editor-fold>
        
        //<editor-fold desc="DirRelativeMat "gauge" setup">
        dirRelativeMatChart.setData(dirRelativeMatPieChartData);
        dirRelativeMatPieChartData.add(new PieChart.Data(null, 120));
        dirRelativeMatPieChartData.add(new PieChart.Data(null, 180));
        dirRelativeMatPieChartData.add(new PieChart.Data(null, 180));
        dirRelativeMatChart.getData().get(0).getNode().setStyle("-fx-pie-color: #FFFFFF;");
        dirRelativeMatChart.getData().get(1).getNode().setStyle("-fx-pie-color: #00FF00;");
        dirRelativeMatChart.getData().get(2).getNode().setStyle("-fx-pie-color: #FF0000;");
        dirRelativeMatChart.setRotate(43);
        //</editor-fold>
        
    }    
    
}
