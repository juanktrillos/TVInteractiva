/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jkt.tv.main;

import com.panamahitek.PanamaHitek_Arduino;
import gnu.io.SerialPortEvent;
import gnu.io.SerialPortEventListener;

/**
 *
 * @author juan.trillos
 */
public class Arduino implements SerialPortEventListener {

    PanamaHitek_Arduino arduino;

    Arduino(String serialPort) throws Exception {

        this.arduino = new PanamaHitek_Arduino();
        arduino.arduinoRX(serialPort, 9600, this);
    }

    @Override
    public void serialEvent(SerialPortEvent ev) {
        if (arduino.isMessageAvailable()) {
            String cad = arduino.printMessage();
            System.out.print(cad + " ");
        } 
    }

}
