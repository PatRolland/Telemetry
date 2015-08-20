/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import javafx.beans.property.SimpleListProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.chart.AreaChart;


/**
 *
 * @author Standard
 */
public class ChartSerie extends SimpleListProperty
{
    
    AreaChart.Series<Double, Double> serie = new AreaChart.Series();
    ObservableList<AreaChart.Data<Double, Double>> serieData = FXCollections.observableArrayList();
    
    public ChartSerie()
    {
        super(FXCollections.observableArrayList());
    }
    
}
