package io;

import javax.sound.sampled.TargetDataLine;

public class MicrophoneCapture {

    private TargetDataLine dataLine;
    private CaptureListener listener;
    private int sampleRate = 16000;


    public MicrophoneCapture(TargetDataLine dataLine){
        this.dataLine = dataLine;
    }

    public void addCaptureListener(CaptureListener listener){
        this.listener = listener;
    }

    
}
