/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataInfo;

/**
 *
 * @author Standard
 */
public class DataCSV {
    
    public float Pitch;
    public int RPM;
    public int VitesseVent;
    public float POUT;
    
    public DataCSV()
    {
        Pitch = 0;
        RPM = 0;
        VitesseVent = 0;
        POUT = 0;
    }
    
    public DataCSV(float pitch, int rpm, int vitesseVent, float pout)
    {
        Pitch = pitch;
        RPM = rpm;
        VitesseVent = vitesseVent;
        POUT = pout;
    }
    
}
