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

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

// TODO 2: CONFLICT status code
@ResponseStatus(HttpStatus.CONFLICT)
public final class IllegalTransitionException extends Exception {

    IllegalTransitionException(Long gameId, Long doorId, DoorStatus from, DoorStatus to) {
        super(String.format("It is illegal to transition door '%d' in game '%d' from '%s' to '%s'",
                doorId, gameId, from, to));
    }

    IllegalTransitionException(Long gameId, Long doorId, DoorStatus to) {
        super(String.format("It is illegal to transition door '%d' in game '%d' to '%s'", doorId, gameId, to));
    }

    IllegalTransitionException(Long gameId, GameStatus from, GameStatus to) {
        super(String.format("It is illegal to transition game '%d' from '%s' to '%s'", gameId, from, to));
    }

}
