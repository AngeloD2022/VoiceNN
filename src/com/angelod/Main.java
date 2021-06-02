package com.angelod;

import libwave.computation.FastFourierTransform;
import libwave.io.CaptureListener;
import libwave.io.MicrophoneCapture;

import javax.sound.sampled.*;
import javax.sound.sampled.spi.MixerProvider;

public class Main {
    private static MicrophoneCapture capture;

    public static void main(String[] args) throws LineUnavailableException {
//        Mixer.Info[] minf = AudioSystem.getMixerInfo();
//
//        Mixer mix = AudioSystem.getMixer(minf[0]);
////        Line.Info[] linf = mix.g;
//
//        DataLine.Info lineInfo = (DataLine.Info);
//
//
//        capture = new MicrophoneCapture(lineInfo);
//
//        capture.addCaptureListener(new CaptureListener() {
//            @Override
//            public void onSamplesReceived(byte[] bytes) {
//                for (byte b : bytes) {
//                    System.out.println(b);
//                }
//            }
//        });

        double[] result = FastFourierTransform.extractEO(new double[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});

        for(double x : result){
            System.out.println(x);
        }
//        System.out.println(result);
    }
}
