/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package chinook;

import gnu.io.CommPortIdentifier;
import gnu.io.PortInUseException;
import gnu.io.SerialPort;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;
import gnu.io.UnsupportedCommOperationException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.TooManyListenersException;

/**
 *
 * @author club
 */
public class SerialCommunication implements SerialPortEventListener {
    static Enumeration portList;
    
    InputStream inputStream;
    OutputStream outputStream;
    SerialPort serialPort;
    
    DataLogger Logger;
    int readBufferSize = 280;
    byte[] readBuffer = new byte[readBufferSize];
    int bytesRead = 0;
    int readValues = 0;
    int lines = 0;
    
    LinkedList<String> valuesString = new LinkedList<>();
    LinkedList<Float> values = new LinkedList<>();

    
    public SerialCommunication(CommPortIdentifier portid, DataLogger logger) {
        Logger = logger;
        Logger.logData(new Data("Temps", 0));
        
       try {
            serialPort = (SerialPort) portid.open("SimpleReadApp", 2000);
            inputStream = serialPort.getInputStream();
            outputStream = serialPort.getOutputStream();
            serialPort.addEventListener(this);
            serialPort.notifyOnDataAvailable(true);
            
            serialPort.setSerialPortParams(9600,
                SerialPort.DATABITS_8,
                SerialPort.STOPBITS_1,
                SerialPort.PARITY_NONE);
        } catch (PortInUseException | 
                UnsupportedCommOperationException | 
                IOException |
                TooManyListenersException e) 
        {
            System.out.println(e);
        }
        
        sendStartCommand();
    }
    
    public Boolean sendStartCommand()
    {
        Integer message = 0xDEADBEEF;
        return sendSerialMessage(message);        
    }
    
    public Boolean sendSerialMessage(Object message)
    {
        Boolean succes = true;
        try {
            if(message instanceof String) {
                outputStream.write(((String)message).getBytes());
            }
            else if (message instanceof Integer) {
                outputStream.write((Integer)message);
            }
        }
        catch(IOException e) {
            succes = false;
        }
        return succes;
    }
    
    public void close()
    {
        try {
            inputStream.close();
            outputStream.close();
            serialPort.close();
        } catch (IOException ex) {
            
        }        
    }

 

    @Override
    public void serialEvent(SerialPortEvent event) {
        switch(event.getEventType()) {
            case SerialPortEvent.BI:
            case SerialPortEvent.OE:
            case SerialPortEvent.FE:
            case SerialPortEvent.PE:
            case SerialPortEvent.CD:

            case SerialPortEvent.CTS:
            case SerialPortEvent.DSR:
            case SerialPortEvent.RI:
            case SerialPortEvent.OUTPUT_BUFFER_EMPTY:
                break;
            case SerialPortEvent.DATA_AVAILABLE:
                int data;
                int len = 0;
                try {
                    while((data = inputStream.read()) > -1)
                    {
                        if(len == readBufferSize -1) {
                            len = 0;
                            StringBuilder sb = new StringBuilder();
            
                            for(int i = 0; i < readBuffer.length; ++i) {
                                sb.append(String.format("%02X ", readBuffer[i]));
                            }
                            System.out.println(sb.toString());
                           Runnable thread = new ByteBufferRunnable(readBuffer);
                           thread.run();
                        }
                        readBuffer[len++] = (byte) data;                        
                    }             
                } catch (IOException e) {
                    System.out.println(e);
                }
                break;
        }
    }
    
    private static final Float HEADER = -898989.0f;
    private static final Float FOOTER = -909090.0f;

    private float id = 0;
    
    public class ByteBufferRunnable implements Runnable {

        private byte[] var;
        boolean startRead = false;

        public ByteBufferRunnable(byte[] var) {
            this.var = var;
        }

        public void run() {
            ByteBuffer byteBuffer = ByteBuffer.wrap(var).order(ByteOrder.LITTLE_ENDIAN);
            for(int readValues = 0; byteBuffer.hasRemaining();) {
                Float f = null;
                try {
                    f = byteBuffer.getFloat();
                }
                catch(BufferUnderflowException e) {
                    break;
                }
                if(HEADER.equals(f)) {
                    System.out.println("HEADER");
                    startRead = true;
                }
                else if(FOOTER.equals(f)) {
                    System.out.println("FOOTER");
                    readValues = 0;
                    startRead = false;
                }
                else if(startRead){    
                    values.add(f);
                   
                    System.out.println("Value #" + ++readValues + ": " + f);
                     if(readValues == 12) {
                        
                        writeDemValues();
                        values = new LinkedList<>();
                    }
                    
                }                 
                else {
                    int position = byteBuffer.position();
                    if(position < byteBuffer.capacity()) {
                        byteBuffer.position(position - 3); //-4 bytes per float + 1 byte
                    }
                }                    
            }
            if(byteBuffer.remaining() > 0){
                System.out.println("Remaining " + byteBuffer.remaining());
            }
        }
    }

    
    private void writeDemValues()
    {
        try{
        float   Id              = values.pop();
        float   Gear            = values.pop();
        float VitVent           = values.pop();
        float DirVent           = values.pop();
        float DirRelativeMat    = values.pop();
        float RPMEolienne       = values.pop();
        float RPMRoue           = values.pop();
        float Pitch             = values.pop();
        float Thrust            = values.pop();
        float Torque            = values.pop();
        float Power             = -(RPMEolienne*Torque)+(VitVent*Thrust);
        float BatteryVoltage    = values.pop();
        float BatteryAmp        = values.pop();
        
        
        Logger.startTimer();
        Logger.logData(new Data("Id",               Id));
        Logger.logData(new Data("Gear",             Gear));
        Logger.logData(new Data("VitVent",          VitVent));
        Logger.logData(new Data("DirVent",          DirVent));
        Logger.logData(new Data("DirRelativeMat",   DirRelativeMat));
        Logger.logData(new Data("RPMEolienne",      RPMEolienne));
        Logger.logData(new Data("RPMRoue",          RPMRoue));
        Logger.logData(new Data("Pitch",            Pitch));
        Logger.logData(new Data("Thrust",           Thrust));
        Logger.logData(new Data("Torque",           Torque));
        Logger.logData(new Data("Power",            Power));
        Logger.logData(new Data("BatteryVoltage",   BatteryVoltage));
        Logger.logData(new Data("BatteryAmp",       BatteryAmp));

        Logger.done();
        System.out.println(lines++);
        }
        catch(Exception e)
        {
            System.out.println(valuesString);
            System.out.println(e);
        }
                        
    }
    
}
