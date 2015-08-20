/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinook;

/**
 *
 * @author club
 */
public class Data {
    
    private String Type;    
    private double Value;
    
    public Data(String type, double value)
    {
        Type = type;
        Value = value;
    }
    
    public double getValue()
    {
        return Value;
    }
    public String getType()
    {
        return Type;
    }
    
    public String getValueString()
    {
        return String.valueOf(MathFct.round(Value, 2));
    }
}
