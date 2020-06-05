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

package LibTMOA.models.structures;

import java.util.Arrays;

/**
 * An ADT for DcMotor Velocities
 */
public class DcMotorVelocities {
    private final double[] velocities;

    /**
     * Creates DcMotorVelocities Object from double[]
     * @param velocities Velocities [double[]]
     */
    public DcMotorVelocities(double[] velocities) {
        this.velocities = velocities;
    }

    /**
     * Returns a DcMotorVelocities as a double[]
     * @return Velocities [double[]]
     */
    public double[] getVelocities() {
        return velocities;
    }

    /**
     * Returns DcMotorVelocity as a double
     * @param id DcMotor ID
     * @return Velocity [double]
     */
    public double getVelocity(byte id){
        return velocities[id];
    }

    /**
     * Returns DcMotorVelocities as a string
     * @return Velocities [array -> string]
     */
    @Override
    public String toString() {
        return "Velocity{" +
                "velocities=" + Arrays.toString(velocities) +
                '}';
    }
}