/*
 * Copyright (C) 2017 dr4ur
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package common;

/**
 *
 * @author dr4ur
 */
public class ActionException extends Exception {
    
    private final ActionExceptionType type;
    /**
     * Creates an ActionException with the specified type and message.
     * @param type the type of ActionException.
     * @param message the error message to be displayed in the console.
     */
    public ActionException(ActionExceptionType type, String message) {
        super(message);
        this.type = type;
    }
    /**
     * Returns the type of action that caused an ActionException.
     * @return this ActionException type.
     */
    public ActionExceptionType getType() {
        return this.type;
    }
}
