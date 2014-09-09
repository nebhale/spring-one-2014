/*
 * Copyright 2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.nebhale.springone2014.model;

import java.util.Map;

public enum DoorStatus {

    CLOSED,
    OPENED,
    SELECTED;

    private static final String STATUS_KEY = "status";

    public static DoorStatus parse(Map<String, String> payload) {
        String value = payload.get(STATUS_KEY);

        if (value == null) {
            throw new IllegalArgumentException("Payload is missing key status'");
        }

        try {
            return DoorStatus.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(
                    String.format("'%s' is an illegal value for key '%s'", value, STATUS_KEY), e);
        }

    }

}
