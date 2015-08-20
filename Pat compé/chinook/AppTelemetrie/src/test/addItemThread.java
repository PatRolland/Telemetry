/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package test;

import java.util.LinkedList;
import java.util.Queue;
import javafx.scene.chart.AreaChart;

/**
 *
 * @author club
 */
public class addItemThread extends Thread {
    
    AreaChart.Data<Double, Double> data;
    AreaChart.Series<Double, Double> serie;
    double index = 0;
    
    public addItemThread(AreaChart.Series<Double, Double> serie, double x, double y)
    {
        data = new AreaChart.Data<Double, Double>(x,y);
        this.serie = serie;
    }
    
    public void run()
    {
        //reaChart.Data<Double, Double> data2 = new AreaChart.Data<Double, Double>(1.2,2.2);
        Queue< AreaChart.Data<Double, Double> > serieTemp = new LinkedList<>();
        
        //serie2.add(data2);
        
        //serie.getData().addAll(serie2);
        for(double i = 0; i < 25; i++, index++)
            serieTemp.add(new AreaChart.Data<>(index,index));
        
        serie.getData().addAll(serieTemp);
    }
    
}
