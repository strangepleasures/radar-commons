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

package org.radarcns.stream.collector;

import java.util.Arrays;
import java.util.List;

/**
 * Java class to aggregate data using Kafka Streams. Double Array is the base type.
 */
public class DoubleArrayCollector {
    private DoubleValueCollector[] collectors;

    /**
     * Add a sample to the collection.
     * @param value new sample that has to be analysed
     */
    public DoubleArrayCollector add(double[] value) {
        if (collectors == null) {
            collectors = new DoubleValueCollector[value.length];
            for (int i = 0; i < value.length; i++) {
                collectors[i] = new DoubleValueCollector();
            }
        }
        if (collectors.length != value.length) {
            throw new IllegalArgumentException(
                    "The length of current input differs from the length of the value used to "
                            + "instantiate this collector");
        }
        for (int i = 0; i < collectors.length; i++) {
            collectors[i].add(value[i]);
        }

        return this;
    }

    @Override
    public String toString() {
        return Arrays.toString(collectors);
    }

    public List<DoubleValueCollector> getCollectors() {
        return Arrays.asList(collectors);
    }
}
