/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;


import DataInfo.DataCSV;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.application.Platform;



import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;

import javafx.scene.control.ListView;
import javafx.stage.FileChooser;

import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


/**
 * FXML Controller class
 *
 * @author club
 */
public class FXMLMainViewController implements Initializable {
    DataBase d = new DataBase();
    ObservableList<AreaChart.Series> areaChartData = FXCollections.observableArrayList();
    
    List<AreaChart.Series<Double, Double>> listChart = new ArrayList<>();
    List<ObservableList<AreaChart.Data<Double, Double>>> listChartData = new ArrayList<>();
    
    AreaChart.Series<Double, Double> serie6 = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serie6Data = FXCollections.observableArrayList();
    AreaChart.Series<Double, Double> serie8 = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serie8Data = FXCollections.observableArrayList();
    AreaChart.Series<Double, Double> serie10 = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serie10Data = FXCollections.observableArrayList();
    AreaChart.Series<Double, Double> serie12 = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serie12Data = FXCollections.observableArrayList();
    AreaChart.Series<Double, Double> serie14 = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serie14Data = FXCollections.observableArrayList();
    AreaChart.Series<Double, Double> serie16 = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serie16Data = FXCollections.observableArrayList();
    AreaChart.Series<Double, Double> serie18 = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serie18Data = FXCollections.observableArrayList();
    AreaChart.Series<Double, Double> serie20 = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serie20Data = FXCollections.observableArrayList();
    AreaChart.Series<Double, Double> serie22 = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serie22Data = FXCollections.observableArrayList();
    AreaChart.Series<Double, Double> serie24 = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serie24Data = FXCollections.observableArrayList();
    
    
    double i = 0;
    double j = 0;
    Thread t;
    
   
    @FXML
    ListView DocumentList;
    
    @FXML
    AreaChart RPMROUE_Chart;
     
    
    /**
     * Initializes the controller class.
     */
    @FXML
    private void handleOpenMenu(ActionEvent event)
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
                    dat.Pitch = Float.parseFloat(tokens[0]);
                    dat.RPM = Integer.parseInt(tokens[1]);
                    dat.VitesseVent = Integer.parseInt(tokens[2]);
                    dat.POUT = Float.parseFloat(tokens[3]);
                    
                    boolean seriePresente = false;
                    for(AreaChart.Series<Double, Double> s : listChart)
                    {
                        if(s.getName().compareTo(tokens[2]) == 0)
                        {
                            seriePresente = true;
                        }
                    }
                    
                    if(!seriePresente)
                    {
                        AreaChart.Series<Double, Double> serieTemp = new AreaChart.Series<Double, Double>();
                        serieTemp.setName(tokens[1]);
                        
                        AreaChart.Data<Double, Double> list = new AreaChart.Data<>();
                        ObservableList<AreaChart.Data<Double, Double>> serieTempData = FXCollections.observableArrayList(list);
                        listChartData.add(serieTempData);
                        serieTemp.setData(serieTempData);
                        
                        //areaChartData.add(listChart.get(listChart.indexOf(serieTemp)));
                    }
                    
                    addItem(tokens[2], dat.RPM, dat.POUT);
                
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
    
    @FXML
    private void handleCheckBox_Theorique(ActionEvent event)
    {  
        for(int i = 0; i < 10; ++i) {
            
                Platform.runLater(t);
            
                
        }
        
    }
    @FXML
    private void handleCheckBox_Pratique(ActionEvent event)
    {  
        CSV_Data dat = new CSV_Data("1", "2", "3");
        
        //addItem(1, Double.parseDouble(dat.docNumber), Double.parseDouble(dat.index));
    }
    
    public void addItem(String serieName, double x, double y)
    {
        AreaChart.Data<Double, Double> data;
        data = new AreaChart.Data<>(x,y);        
        
        for( AreaChart.Series<Double, Double> s : areaChartData)
        {
            if(s.getName().compareTo(serieName) == 0)
            {
                s.getData().add(data);
            }
        }   
    }
    
    @FXML
    private void handleMenuItem_New(ActionEvent event) throws IOException
    {
        //FXMLNewChart_ViewController a = new FXMLNewChart_ViewController();
        
        Parent root = FXMLLoader.load(getClass().getResource("FXMLNewChart_View.fxml"));

        Stage stage = new Stage();

        stage.setScene(new Scene(root, 450, 450));

        stage.show();

        
    }
    
    //@FXML
    //TextField XaxisLabel, YaxisLabel;
    
    
    @FXML
    private void ChartButton_handle(ActionEvent event)
    {
        //XaxisLabel.getText();
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        //AreaChart1.getXAxis().setAutoRanging(true);

        //serieTheoriqueData.add( new AreaChart.Data(0, 0));
        setupNames();
        setupData();        
        addSeriesToChart();
        //seriePratiqueData.add( new AreaChart.Data(0, 0));
        
        
        /*serieTheorique.setData(serieTheoriqueData);
        seriePratique.setData(seriePratiqueData);
        
        areaChartData.add(serieTheorique);
        areaChartData.add(seriePratique);*/
        
        
        
        //listChart.add(seriePratique);
        //listChart.add(serieTheorique);
        
        NumberAxis xAxis = new NumberAxis("X Values", 1.0d, 9.0d, 2.0d);
        NumberAxis yAxis = new NumberAxis("Y Values", 0.0d, 10.0d, 2.0d);
        
        
        
        RPMROUE_Chart.getData().addAll(0, areaChartData);
        RPMROUE_Chart.getXAxis().setLabel("RPM éolienne");
        RPMROUE_Chart.getYAxis().setLabel("Power out");
        RPMROUE_Chart.getXAxis().animatedProperty().set(false);
//        for(int i = 0; i < 600; i++)
//        {
//           addItem(1, i,i * i/1.68);
//           addItem(2, i, i*i/2);
//        }
        //t = new addItemThread(serieTheorique, 1, 1);
        
        
        
    }
    
    private void setupNames()
    {
        serie6.setName("6");
        serie8.setName("8");
        serie10.setName("10");
        serie12.setName("12");
        serie14.setName("14");
        serie16.setName("16");
        serie18.setName("18");
        serie20.setName("20");
        serie22.setName("22");
        serie24.setName("24");        
    }
    
    private void setupData()
    {
        serie6.setData(serie6Data);
        serie8.setData(serie8Data);
        serie10.setData(serie10Data);
        serie12.setData(serie12Data);
        serie14.setData(serie14Data);
        serie16.setData(serie16Data);
        serie18.setData(serie18Data);
        serie20.setData(serie20Data);
        serie22.setData(serie22Data);
        serie24.setData(serie24Data);
    }
    
   
    
    
    private void addSeriesToChart()
    {
        areaChartData.add(serie6);
        areaChartData.add(serie8);
        areaChartData.add(serie10);
        areaChartData.add(serie12);
        areaChartData.add(serie14);
        areaChartData.add(serie16);
        areaChartData.add(serie18);
        areaChartData.add(serie20);
        areaChartData.add(serie22);
        areaChartData.add(serie24);        
    }
}
