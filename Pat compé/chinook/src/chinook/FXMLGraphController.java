/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinook;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.chart.AreaChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.control.Slider;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Path;

/**
 * FXML Controller class
 *
 * @author club
 */
public class FXMLGraphController implements Initializable {


    
    private FXMLDocumentController mainWindow;
    ObservableList<AreaChart.Series<Double, Double>> areaChartData = FXCollections.observableArrayList();    
    @FXML AreaChart GraphChart;
    @FXML AnchorPane anchorPane;
  
    Data DataXTemp = null;
    Data DataYTemp = null;
    
    boolean autoRanging = true;
    
    SimpleDoubleProperty rectinitX = new SimpleDoubleProperty();
    SimpleDoubleProperty rectX = new SimpleDoubleProperty();
    SimpleDoubleProperty rectY = new SimpleDoubleProperty();
    
    double originalXUpperBound = -1;
    double originalXLowerBound = -1;
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        GraphChart.setData(areaChartData);
        GraphChart.setAnimated(false);
        
        areaChartData.add(new AreaChart.Series<Double, Double>());
        areaChartData.get(0).setName("Serial Values");
    }
    
    public void addItem(Data data1, Data data2)
    {
        for(AreaChart.Series<Double, Double> s : areaChartData)
        {
            if(data1.getType().compareTo(getTypeX()) == 0)
                if(data2.getType().compareTo(getTypeY()) == 0)
                    s.getData().add(new AreaChart.Data<>(data1.getValue(), data2.getValue()));
            if(data2.getType().compareTo(getTypeX()) == 0)
                if(data1.getType().compareTo(getTypeY()) == 0)
                    s.getData().add(new AreaChart.Data<>(data2.getValue(), data1.getValue()));
        }      
        
        
    }
    
    public void addItem(double i1, double i2)
    {
        areaChartData.get(0).getData().add(new AreaChart.Data<>(i1, i2));
    }
    
    private void addItem()
    {
        areaChartData.get(0).getData().add(new AreaChart.Data<>(DataXTemp.getValue(), DataYTemp.getValue()));
        DataXTemp = null;
        DataYTemp = null;
    }
    
    public void setPosition(int x, int y)
    {
        
    }
    
    public void setTempX(Data data)
    {
        DataXTemp = new Data(data.getType(), data.getValue());
        originalXUpperBound = DataXTemp.getValue();
        if(DataYTemp != null)
            addItem();        
    }
    public void setTempY(Data data)
    {
        DataYTemp = new Data(data.getType(), data.getValue());
        if(DataXTemp != null)
            addItem();        
    }
    
    public void setMainWindow(FXMLDocumentController mainWindow){
        this.mainWindow = mainWindow;
    }
    
    public void setTitle(String title)
    {
        GraphChart.setTitle(title);
    }
    public void setXaxisTitle(String title)
    {
        GraphChart.getXAxis().setLabel(title);
    }
    public void setYaxisTitle(String title)
    {
        GraphChart.getYAxis().setLabel(title);
    }
    public String getTypeX()
    {
        return GraphChart.getXAxis().getLabel();
    }
    public String getTypeY()
    {
        return GraphChart.getYAxis().getLabel();
    }  
    
    @FXML public void clearData(ActionEvent event)
    {
        areaChartData.remove(0, areaChartData.size());
        areaChartData.add(new AreaChart.Series<Double, Double>());
        areaChartData.get(0).setName("Serial Values");
    }
    
    @FXML public void handleAnimatedCheckBox(ActionEvent event)
    {
        autoRanging = !autoRanging;
        GraphChart.getXAxis().setAutoRanging(autoRanging);
    }
    
    @FXML public void handleMouseEvent(MouseEvent mouseEvent)
    {
        if (mouseEvent.getEventType() == MouseEvent.MOUSE_PRESSED) {
            rectinitX.set(mouseEvent.getX());
        } else if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED )//|| mouseEvent.getEventType() == MouseEvent.MOUSE_MOVED) 
        {
           // LineChart<Number, Number> lineChart = (LineChart<Number, Number>) anchorPane.get;
            NumberAxis xAxis = (NumberAxis) GraphChart.getXAxis();

            double newXlower = 0, newXupper = 0;
            double Delta =( ((NumberAxis)GraphChart.getYAxis()).getUpperBound() + 
                    Math.abs(((NumberAxis)GraphChart.getYAxis()).getLowerBound()) /1000);
            

            if (mouseEvent.getEventType() == MouseEvent.MOUSE_DRAGGED) {
                if (rectinitX.get() < mouseEvent.getX()) {
                    Delta *= -1;                    
                } 
                newXlower = xAxis.getLowerBound() + Delta;
                newXupper = xAxis.getUpperBound() + Delta;

                xAxis.setLowerBound(newXlower);
                xAxis.setUpperBound(newXupper);

                DoubleProperty p1 = xAxis.scaleXProperty();
                DoubleProperty p2 = xAxis.translateXProperty();

                double horizontalValueRange = xAxis.getUpperBound() - xAxis.getLowerBound();
                double horizontalWidthPixels = xAxis.getWidth();
                //pixels per unit
                double xScale = horizontalWidthPixels / horizontalValueRange;

                Set<Node> nodes = GraphChart.lookupAll(".chart-vertical-grid-lines");
                for (Node n: nodes) {
                    Path p = (Path) n;
                    double currLayoutX = p.getLayoutX();
                    p.setLayoutX(currLayoutX + (Delta*-1) * xScale);
                }                    
                double lox = xAxis.getLayoutX();                                      
            }
            rectinitX.set(mouseEvent.getX());
        }
    }
    
    @FXML public void handleScrollEvent(ScrollEvent scrollEvent)
    {
        scrollEvent.consume();

        if (scrollEvent.getDeltaY() == 0) {
            return;
        }
        
        
        NumberAxis xAxis = (NumberAxis) GraphChart.getXAxis();
        NumberAxis yAxis = (NumberAxis) GraphChart.getYAxis();
        
        if((xAxis.getLowerBound() + scrollEvent.getDeltaY()/100) < (xAxis.getUpperBound() - scrollEvent.getDeltaY()/100))
        {
            xAxis.setLowerBound(xAxis.getLowerBound() + scrollEvent.getDeltaY()/100);
            xAxis.setUpperBound(xAxis.getUpperBound() - scrollEvent.getDeltaY()/100);
        }
    }
    
    
    
    
        
        
    
}
