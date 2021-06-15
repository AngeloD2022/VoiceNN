package libwave.parsers;

import org.junit.jupiter.api.Test;

class WAVFileTest {

    @Test
    public void testParser() throws Exception {
        WAVFile wavFile = new WAVFile("C:\\Users\\asola\\OneDrive\\Desktop\\Ultimate Custom Night\\sounds\\fan.wav");
//        WAVFile wavFile = new WAVFile("C:\\Users\\asola\\OneDrive\\Desktop\\Ultimate Custom Night\\sounds\\fish1.wav");
        dumpWavFile(wavFile);

    }

    public void dumpWavFile(WAVFile wavFile){
        System.out.println("Begin file attribute dump...");

        System.out.println("WAVE file chunk size: " + wavFile.getChunkSize());

        if(wavFile.isPCM())
            System.out.println("WAVE data is PCM formatted.");
        else
            System.out.println("WAVE data is not PCM formatted.");

        System.out.println("Channel count: " + wavFile.getChannelCount());
        System.out.println("Bits per sample: " + wavFile.getBitsPerSample());

        System.out.println("Subchunk1 size: " + wavFile.getSubchunk1Size());
        System.out.println("Subchunk2 size: " + wavFile.getDataSize());
        System.out.println("Duration: " + wavFile.getAudioDurationSeconds()+" seconds");
    }

    @Test
    public void sanityCheck(){

    }
}