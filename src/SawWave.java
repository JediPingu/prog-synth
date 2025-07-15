public class SawWave extends Voice {

    public SawWave(
        int offsetOctaves,
        double sampleRate,
        double peakVolume,
        double attack,
        double decay,
        double sustain
    ) {
        super(offsetOctaves, sampleRate, peakVolume, attack, decay, sustain);
    }

    @Override
    protected byte[] waveform(double frequency, double length) {
        double real_frequency = frequency * Math.pow(2, getOffset());
        byte[] wave = new byte[(int) (length * getSampleRate())];

        int attackSamples = (int) (getAttack() * getSampleRate());
        int decaySamples = (int) (getDecay() * getSampleRate());

        double decaySlope = (getPeakAmplitude() - getSustain())/decaySamples;
        double attackSlope = getPeakAmplitude()/(getAttack() * getSampleRate());

        wave[0] = 0;
        wave[1] = 1;


        for (int i = 2; i < wave.length; i++){
            double time = i/getSampleRate();
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

            double value =  2.0 * ((time * real_frequency) - Math.floor((i/getSampleRate()) * real_frequency + 0.5));
            wave[i] = (byte) (volume * value * 127);
        }
        return wave;
    }
}
