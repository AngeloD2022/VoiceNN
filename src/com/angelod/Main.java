package com.angelod;

import libwave.computation.Complex;
import libwave.computation.ComplexMatrixUtil;
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

//        double[] result = FastFourierTransform.extractEO(new double[] {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15});

//        for(double x : result){
//            System.out.println(x);
//        }
//        System.out.println(result);
        double[] samples = new double[]{9.53997, 10.7569, 3.39465, 5.94873, 4.26445, 7.77647, 4.52789,
                5.36739, 2.24089, 2.74865, 3.73656, 9.06144, 13.8068, 2.00845,
                7.22171, 6.0695, 13.74, 10.2391, 13.5163, 9.86941, 12.9057, 4.39044,
                15.6936, 8.95317, 4.46984, 12.1601, 4.96757, 10.3981, 9.90728, 13.4516, 7.69608, 12.7877};

        FastFourierTransform.FFT(samples);
//        ComplexMatrixUtil.quadMerge()

        Complex[][] matrix = ComplexMatrixUtil.constructDFTMtx(32);
        ComplexMatrixUtil.printMtx(matrix);
    }
}
