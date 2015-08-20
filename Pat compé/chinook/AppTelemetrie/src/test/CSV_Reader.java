package test;


import test.CSV_Data;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.StringTokenizer;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author club
 */


public class CSV_Reader {
    
    public static ArrayList importCSV(String filepath)
    {
     
        ArrayList<CSV_Data> datalist;
        datalist = new ArrayList<>();

        try
        {
            FileReader fr = new FileReader(filepath);
            BufferedReader br = new BufferedReader(fr);
            String stringRead = br.readLine();

            while( stringRead != null )
            {
                StringTokenizer st = new StringTokenizer(stringRead, ",");
                String docNumber = st.nextToken( );
                String note = st.nextToken( );  /** PROBLEM */
                String index = st.nextToken( ); /** PROBLEM */

                //ImportedXls temp = new ImportedXls(docNumber, note, index);
                //datalist.add(temp);

                // read the next line
                stringRead = br.readLine();
            }
            
            br.close( );
        }
        catch(IOException ioe){}
        
        return datalist;
    }
}
    

