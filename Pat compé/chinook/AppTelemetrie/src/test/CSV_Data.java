package test;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author club
 */
public class CSV_Data {
    
    public String docNumber, note, index = "";
    
    public CSV_Data(String docNumber, String note, String index)
    {
        this.docNumber = docNumber;
        this.note = note;
        this.index = index;
    }
    
}
