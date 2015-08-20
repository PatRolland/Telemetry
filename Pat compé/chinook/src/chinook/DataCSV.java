/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinook;

/**
 *
 * @author Standard
 */
public class DataCSV {
    
    int   Gear;
    float VitVent;
    float DirVent;
    float DirRelativeMat;
    float RPMEolienne;
    float RPMRoue;
    float Pitch;
    float Thrust;
    float Torque;
    float Power;
    float Temps;
    
    public DataCSV()
    {
        Gear = 0;
        VitVent = 0;
        DirVent = 0;
        DirRelativeMat = 0;
        RPMEolienne = 0;
        RPMRoue = 0;
        Pitch = 0;
        Thrust = 0;
        Torque = 0;
        Power = 0;
        Temps = 0;
    }
    
    public DataCSV(int Gear, float VitVent, float DirVent, float DirRelativeMat, float RPMEolienne, float RPMRoue, float Pitch, float Thrust, float Torque, float Power, float Temps)
    {
        this.Gear = Gear;
        this.VitVent = VitVent;
        this.DirVent = DirVent;
        this.DirRelativeMat = DirRelativeMat;
        this.RPMEolienne = RPMEolienne;
        this.RPMRoue = RPMRoue;
        this.Pitch = Pitch;
        this.Thrust = Thrust;
        this.Torque = Torque;
        this.Power = Power;
        this.Temps = Temps;
    }
    
}
