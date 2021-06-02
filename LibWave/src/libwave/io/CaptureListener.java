package libwave.io;

public interface CaptureListener {
    void onSamplesReceived(byte[] value);
}
