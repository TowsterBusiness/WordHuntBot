package org.example;
//https://code.google.com/archive/p/java-simple-serial-connector/wikis/jSSC_examples.wiki - Code based on
import jssc.*;

public class SerialPortTest {

    static SerialPort serialPort;
    public static void main(String[] args) {
        serialPort = new SerialPort("/dev/cu.usbmodem14301");
        try {
            serialPort.openPort();

            serialPort.setParams(SerialPort.BAUDRATE_9600,
                    SerialPort.DATABITS_8,
                    SerialPort.STOPBITS_1,
                    SerialPort.PARITY_NONE);
            int mask = SerialPort.MASK_RXCHAR + SerialPort.MASK_CTS + SerialPort.MASK_DSR;
            serialPort.setEventsMask(mask);
            serialPort.addEventListener(new SerialPortReader());
            System.out.println("Started");
            Thread.sleep(3000);
            System.out.println("Resumed");
            int[] intList = {2, 2, 4, 5, 6};
            byte[] byteList = new byte[intList.length];
            for (int i = 0; i < intList.length; i++) {
                byteList[i] = (byte) intList[i];
            }
            serialPort.writeBytes(byteList);



        }
        catch (SerialPortException e) {
            System.out.println(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    static class SerialPortReader implements SerialPortEventListener {

        public void serialEvent(SerialPortEvent event) {
            if(event.isRXCHAR() && event.getEventValue() > 0){
                try {
                    System.out.print(serialPort.readString());
                } catch (SerialPortException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}


