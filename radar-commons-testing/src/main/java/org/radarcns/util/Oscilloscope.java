/*
 * Copyright 2017 The Hyve and King's College London
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.radarcns.util;

/**
 * Oscilloscope gives out a regular beat, at a given frequency per second. The intended way to use
 * this is with a do-while loop, with the {@link #beat()} retrieved at the start of the loop, and
 * {@link #willRestart()} in the condition of the loop.
 */
public class Oscilloscope {
    private final int frequency;
    private final long timeStep;

    private int iteration;
    private long baseTime;

    /**
     * Frequency which will give beat at given frequency per second.
     * @param frequency number of beats per second.
     */
    public Oscilloscope(int frequency) {
        this.frequency = frequency;
        this.baseTime = System.nanoTime();
        this.timeStep = 1_000_000_000L / this.frequency;
        this.iteration = 0;
    }

    /** Restart the oscilloscope at zero. */
    public void reset() {
        this.baseTime = System.nanoTime();
        this.iteration = 0;
    }

    /** Whether the next beat will restart at one. */
    public boolean willRestart() {
        return iteration % frequency == 0;
    }

    /**
     * One oscilloscope beat, sleeping if necessary to not exceed the frequency per second. The beat
     * number starts at one, increases to the frequency, and then goes to one again.
     * @return one up to the given frequency
     * @throws InterruptedException when the sleep was interrupted.
     */
    public int beat() throws InterruptedException {
        long currentTime = System.nanoTime();
        long nextSend = baseTime + iteration * timeStep;
        if (currentTime < nextSend) {
            long timeToSleep = nextSend - currentTime;
            Thread.sleep(timeToSleep / 1_000_000L, ((int) timeToSleep) % 1_000_000);
        }
        int beatNumber = iteration % frequency + 1;
        iteration++;
        return beatNumber;
    }
}
