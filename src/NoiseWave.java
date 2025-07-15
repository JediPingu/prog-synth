public class NoiseWave extends Voice{
    public NoiseWave(int offsetOctaves, double sampleRate, double peakVolume) {
        super(offsetOctaves, sampleRate, peakVolume);
    }

    @Override
    protected byte[] waveform(double frequency, double length) {

        int totalLength = (int) (length * getSampleRate());

        int attackSamples = (int) (getAttack() * getSampleRate());
        int decaySamples = (int) (getDecay() * getSampleRate());

        double attackSlope = getPeakAmplitude()/(getAttack() * getSampleRate());
        double decaySlope = (getPeakAmplitude() - getSustain())/decaySamples;

        byte[] sample = new byte[totalLength];
        sample[0] = 0;
        sample[1] = 1;
        for (int i = 2; i < sample.length; i++) {
            double volume;
            if (i < attackSamples){
                volume = i * attackSlope;
            }
            else if (i < attackSamples + decaySamples){
                volume = getPeakAmplitude() - ((i - attackSamples)*decaySlope);
            }
            else {
                volume = getSustain();
            }

            double randomNum = Math.random();
            if (randomNum > 0.5) {
                sample[i] = (byte) (volume * randomNum * 127);
            }
            else{
                sample[i] = (byte) (volume * randomNum * -128);
            }
        }
        return sample;
    }
}

