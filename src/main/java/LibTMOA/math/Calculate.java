/*
 * Copyright 2020 WinT 3794 (Manuel Díaz Rojo and Alexis Obed García Hernández)
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
 *
 */

package LibTMOA.math;

import LibTMOA.utils.Utilities;
import LibTMOA.utils.VelocityChecker;

/**
 * A class with all the mathematics methods to get mecanum directions.
 */
public class Calculate {
    /**
     * Returns power value (for FR,BL).
     * @param Vd The multiplicative speed [0 - 1]
     * @param Td The directional angle [0 - 2 * Math.PI]
     * @param Vt The change speed [-1 - 1]
     * @return Power Value
     */
    public static double calc1(double Vd, double Td, double Vt) {
        return Math.abs(Vd) * Math.sin(Math.abs(Td) + (Math.PI / 4)) + Vt;
    }

    /**
     * Returns power value (for FL,BR).
     * @param Vd The multiplicative speed [0 - 1]
     * @param Td The directional angle [0 - 2 * Math.PI]
     * @param Vt The change speed [-1 - 1]
     * @return Power Value
     */
    public static double calc2(double Vd, double Td, double Vt) {
        return Math.abs(Vd) * Math.cos(Math.abs(Td) + (Math.PI / 4)) + Vt;
    }

    /**
     * Returns angle from coordinates.
     * @param y Ordinates Position [-1 - 1]
     * @param x Abscissa Position [-1 - 1]
     * @return Angle [0 - 2 * Math.PI]
     */
    public static double getAngle(double y, double x) {
        double a = Math.atan2(y, x);
        return Math.abs(a);
    }

    /**
     * Returns multiplicative speed from coordinates.
     * @param y Ordinates Position [-1 - 1]
     * @param x Abscissa Position [-1 - 1]
     * @return Multiplicative Speed [0 - 1] || '0' if not pass integrity check
     */
    public static double getSpeed(double y, double x) {
        y = Math.abs(y);
        x = Math.abs(x);

        return VelocityChecker.checkCoordinates(y, x) ? Math.max(y, x) : 0;
    }

    /**
     * Returns rounded value to established number of places (Utilities.roundPower).
     * @param value Raw value
     * @return Rounded value
     */
    public static double roundPower(double value) {
        long factor = (long) Math.pow(10, Utilities.roundPower);
        value = value * factor;
        return (double) Math.round(value) / factor;
    }
}
