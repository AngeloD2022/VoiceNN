package libwave.parsers;

import java.io.File;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.file.Files;

/*
 *Notes
 * - PCM, or pulse-code modulation is a method used to digitally represent sampled analog signals (wikipedia)
 *  >> "In a PCM stream, the amplitude of the analog signal is sampled regularly at uniform intervals."
 *  >> "Each sample is quantized to the nearest value within a range of digital steps."
 *
 */


public class WAVFile {

    /**
     * "RIFF" in ASCII
     */
    private final int RIFF_VALUE = 0x52494646;

    private final long WAVE_MAGIC = 0x57415645666d74L;

    private byte[] data;
    private ByteBuffer buffer;


    public WAVFile(String filePath) throws Exception {
        File file = new File(filePath);

        this.data = Files.readAllBytes(file.toPath());
        this.buffer = ByteBuffer.wrap(this.data);

        if (!isValidWave())
            throw new Exception("Not a valid WAVE file.");
    }

    public WAVFile(byte[] data) throws Exception {
        this.data = data;
        this.buffer = ByteBuffer.wrap(this.data);

        if (!isValidWave())
            throw new Exception("Not a valid WAVE file.");
    }

    public ByteBuffer getByteBuffer() {
        return this.buffer;
    }

    public int getChunkSize() {
        return getByteBuffer().slice(4, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public boolean isPCM() {
        return getByteBuffer().slice(20, 2).order(ByteOrder.LITTLE_ENDIAN).getShort() == 1;
    }

    public short getChannelCount() {
        return getByteBuffer().slice(22, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public int getSampleRate() {
        return getByteBuffer().slice(24, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public int getByteRate() {
        return getByteBuffer().slice(28, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public short getBlockAlign() {
        return getByteBuffer().slice(32, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public short getBitsPerSample() {
        return getByteBuffer().slice(34, 2).order(ByteOrder.LITTLE_ENDIAN).getShort();
    }

    public int getSubchunk1Size() {
        return getByteBuffer().slice(16, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public int getDataSize() {
        return getByteBuffer().slice(getSubchunk2Pos() + 4, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
    }

    public boolean containsListMetadata() {
        return getByteBuffer().slice(36, 4).getInt() == 0x4c495354;
    }

    public float getAudioDurationSeconds() {
        return (float) (getDataSize() / (getBitsPerSample() / 8.0) / getSampleRate());
    }

    public int[] getSamples() {
        int[] buffer = new int[getDataSize()];
        getByteBuffer().asIntBuffer().get(buffer, getSubchunk2Pos() + 8, buffer.length);
        return buffer;
    }

    private boolean isValidWave() {
        ByteBuffer buffer = getByteBuffer();

        // TODO: implement a more rigorous check.
        if (buffer.slice(0, 4).getInt() != RIFF_VALUE
                || buffer.slice(8, 4).getInt() != 0x57415645) {
            return false;
        }
        return true;
    }

    private int getSubchunk2Pos() {
        if (containsListMetadata()) {
            int offset = getByteBuffer().slice(40, 4).order(ByteOrder.LITTLE_ENDIAN).getInt();
            return offset + 44;
        }
        return 36;
    }

}





