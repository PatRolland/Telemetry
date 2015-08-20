/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinook;

import java.time.LocalDateTime;
import java.util.ArrayList;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

/**
 *
 * @author club
 */



public class DataLogger {
    public static enum TYPES {PITCH, THRUST, GEAR, RPM_ROUE, RPM_EOLIENNE, DIR_VENT, VIT_VENT, DIR_RELATIVE_MAT, NB_TYPES};
    
    ArrayList<Data> DataPitch = new ArrayList<>();
    ArrayList<Data> DataThrust = new ArrayList<>();
    ArrayList<Data> DataRPMRoue = new ArrayList<>();
    ArrayList<Data> DataRPMEolienne = new ArrayList<>();
    ArrayList<Data> DataDirVent = new ArrayList<>();
    ArrayList<Data> DataVitVent = new ArrayList<>();
    ArrayList<Data> DataDirRelativeMat = new ArrayList<>();
    ArrayList<Data> DataGear = new ArrayList<>();
    ArrayList<Data> DataTemps = new ArrayList<>();
    ArrayList<Data> DataTorque = new ArrayList<>();
    ArrayList<Data> DataPower = new ArrayList<>();
    ArrayList<Data> DataId =  new ArrayList <>();
    ArrayList<Data> DataBatteryVoltage = new ArrayList<>();
    ArrayList<Data> DataBatteryAmp = new ArrayList<>();
    
    FXMLDocumentController Controller;
    Timeline timeline;
    boolean timerStarted;
    int elapsedTime = 0;
    long startTime = 0;
    long currentTime = 0;
    
    
    
    public DataLogger(FXMLDocumentController controller) {    
        //test
        Controller = controller;
        timerStarted = false;        
    }
    
    public void startTimer()
    {
        if(!timerStarted){
            startTime = System.currentTimeMillis();
            timerStarted = true;
        }
    }
    
    public void addTime() //used with timeline
    {
        elapsedTime++;
        done();
    }
    
    public long getElapsedTime()
    {
        currentTime = System.currentTimeMillis();
        long value = currentTime - startTime;
        return value;
    }
            
    
    public ArrayList<Data> getData(String type)
    {
       ArrayList<Data> data = null;
       switch(type)
       {
            case("Thrust"):
                data =  DataThrust;  
                   break;
            case("Pitch"):
                data =  DataPitch;  
                   break;
            case("RPMRoue"):
                data =  DataRPMRoue;  
                   break;
            case("RPMEolienne"):
                data =  DataRPMEolienne;  
                   break;
            case("DirVent"):
                data =  DataDirVent;  
                   break;
            case("VitVent"):
                data =  DataVitVent;  
                   break;
            case("DirRelativeMat"):
                data =  DataDirRelativeMat;  
                   break;
            case("Gear"):
                data =  DataGear;  
                   break;
            case("Temps"):
                data =  DataTemps;  
                   break;
            case("Torque"):
                data =  DataTorque;  
                   break;
            case("Power"):
                data =  DataPower;  
                   break;
            case("Id"):
                    data = DataId;
                break;
            case("BatteryVoltage"):
                data = DataBatteryVoltage;
                break;
            case("BatteryAmp"):
                data = DataBatteryAmp;
                break;
       }
       
       return data;
    }
    
    public void logData(Data data)
    {
        getData(data.getType()).add(data);        
    }
    
    public void done()
    {
        javafx.application.Platform.runLater(new Runnable() {
            public void run() {
                 
        DataTemps.add(new Data("Temps", (double)getElapsedTime()/1000));
        
        
        
        Controller.updateData(DataPitch.get(DataPitch.size()-1));
        Controller.updateData(DataThrust.get(DataThrust.size()-1));
        Controller.updateData(DataRPMRoue.get(DataRPMRoue.size()-1));
        Controller.updateData(DataRPMEolienne.get(DataRPMEolienne.size()-1));
        Controller.updateData(DataDirVent.get(DataDirVent.size()-1));
        Controller.updateData(DataVitVent.get(DataVitVent.size()-1));
        Controller.updateData(DataDirRelativeMat.get(DataDirRelativeMat.size()-1));
        Controller.updateData(DataGear.get(DataGear.size()-1));
        Controller.updateData(DataTemps.get(DataTemps.size()-1));
        Controller.updateData(DataTorque.get(DataTorque.size()-1));
        Controller.updateData(DataPower.get(DataPower.size()-1));
        Controller.updateData(DataBatteryVoltage.get(DataBatteryVoltage.size()-1));
        Controller.updateData(DataBatteryAmp.get(DataBatteryAmp.size()-1));
            }
        });
    }
    public void load()
    {
        Controller.updateData(DataPitch.get(DataPitch.size()-1));
        Controller.updateData(DataThrust.get(DataThrust.size()-1));
        Controller.updateData(DataRPMRoue.get(DataRPMRoue.size()-1));
        Controller.updateData(DataRPMEolienne.get(DataRPMEolienne.size()-1));
        Controller.updateData(DataDirVent.get(DataDirVent.size()-1));
        Controller.updateData(DataVitVent.get(DataVitVent.size()-1));
        Controller.updateData(DataDirRelativeMat.get(DataDirRelativeMat.size()-1));
        Controller.updateData(DataGear.get(DataGear.size()-1));
        Controller.updateData(DataTemps.get(DataTemps.size()-1));
        Controller.updateData(DataTorque.get(DataTorque.size()-1));
        Controller.updateData(DataPower.get(DataPower.size()-1));
        
    }
}
