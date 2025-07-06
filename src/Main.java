import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.SourceDataLine;

public class Main {

    public static void main(String[] args) throws LineUnavailableException {
        float sampleRate = 44100f;
        int durationSeconds = 3;
        int numSamples = (int) (durationSeconds * sampleRate);
        int numChannels = 1;

        // Define sine wave frequencies to mix
        double[] freqs = { 440.0, 550.0, 660.0 }; // A4, C#5, E5 (major chord)
        byte[] audioBuffer = new byte[numSamples * 2]; // 16-bit audio = 2 bytes per sample

        for (int i = 0; i < numSamples; i++) {
            double sample = 0.0;

            // Sum sine waves
            for (double freq : freqs) {
                sample += Math.sin((2 * Math.PI * freq * i) / sampleRate);
            }

            // Average to avoid clipping (basic normalization)
            sample /= freqs.length;

            // Convert to 16-bit signed PCM
            short val = (short) (sample * Short.MAX_VALUE);
            audioBuffer[2 * i] = (byte) (val >> 8);
            audioBuffer[2 * i + 1] = (byte) (val & 0xFF);
        }

        // Playback setup
        AudioFormat format = new AudioFormat(
            sampleRate,
            16,
            numChannels,
            true,
            true
        );
        SourceDataLine line = AudioSystem.getSourceDataLine(format);
        line.open(format);
        line.start();

        // Play audio
        line.write(audioBuffer, 0, audioBuffer.length);
        line.drain();
        line.close();
    }
}
