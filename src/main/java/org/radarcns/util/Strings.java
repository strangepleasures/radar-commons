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

import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

/**
 * String utilities.
 */
public final class Strings {

    private Strings() {
        // utility class
    }

    /**
     * For each string, compiles a pattern that checks if it is contained in another string in a
     * case-insensitive way.
     */
    public static Pattern[] containsPatterns(Collection<String> contains) {
        Pattern[] patterns = new Pattern[contains.size()];
        Iterator<String> containsIterator = contains.iterator();
        for (int i = 0; i < patterns.length; i++) {
            patterns[i] = containsIgnoreCasePattern(containsIterator.next());
        }
        return patterns;
    }

    /**
     * Compiles a pattern that checks if it is contained in another string in a case-insensitive
     * 7way.
     */
    public static Pattern containsIgnoreCasePattern(String containsString) {
        int flags = Pattern.CASE_INSENSITIVE // case insensitive
                | Pattern.LITERAL // do not compile special characters
                | Pattern.UNICODE_CASE; // case insensitive even for Unicode (special) characters.
        return Pattern.compile(containsString, flags);
    }

    /**
     * Whether any of the patterns matches given value.
     */
    public static boolean findAny(Pattern[] patterns, CharSequence value) {
        for (Pattern pattern : patterns) {
            if (pattern.matcher(value).find()) {
                return true;
            }
        }
        return false;
    }

    /** Whether given value is null or empty. */
    public static boolean isNullOrEmpty(String value) {
        return value == null || value.isEmpty();
    }
}
