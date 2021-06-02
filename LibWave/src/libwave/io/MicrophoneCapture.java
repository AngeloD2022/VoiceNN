package libwave.io;

import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;

public class MicrophoneCapture {

    private TargetDataLine dataLine;
    private CaptureListener listener;
    private int sampleRate = 16000;
    CaptureThread ct;

    public MicrophoneCapture(DataLine.Info lineInfo) throws LineUnavailableException {
        this.dataLine = (TargetDataLine) AudioSystem.getLine(lineInfo);
        ct = new CaptureThread();
    }

    public void enableMicrophone() throws LineUnavailableException {
        System.out.println("LIBWAVE >> Starting capture thread...");

        dataLine.open(new AudioFormat(44100.0F,16,1,true,false));

        ct.start();
    }

    public void addCaptureListener(CaptureListener listener){
        this.listener = listener;
    }


    private class CaptureThread implements Runnable {
        private Thread thread;
        private String threadName = "LW_AUDCAP";
        private LineListener lineListener;
        private ByteArrayOutputStream buffer;

        @Override
        public void run() {
            buffer = new ByteArrayOutputStream();

            lineListener = new LineListener() {

                @Override
                public void update(LineEvent event) {

                    // Check if the line closed...
                    if(!event.getLine().isOpen()){
                        System.out.println("LIBWAVE >> Microphone data line closed, ending thread.");
                        thread.interrupt();
                    }
                }
            };

            while(true){

                // read samples and send buffer to event listener...
                byte[] buf = new byte[8000];
                dataLine.read(buf, 0,8000);
                listener.onSamplesReceived(buf);
            }
        }

        public void start(){
            if(thread == null){
                thread = new Thread(this, threadName);
                thread.start();
            }
        }
    }

}
