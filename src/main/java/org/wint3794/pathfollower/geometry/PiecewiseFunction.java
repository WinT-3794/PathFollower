/*
 * Copyright 2020 WinT 3794 (Manuel Diaz Rojo and Alexis Obed Garcia Hernandez)
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

package org.wint3794.pathfollower.geometry;

import java.util.ArrayList;

public class PiecewiseFunction {
    public ArrayList<Pose2d> m_points = new ArrayList<>();

    public PiecewiseFunction(String visualString) {
        //go through the string from bottom left to top right (kinda weird) assuming it's 20 by 9 chars

        int iter = visualString.length() - 20;//this will be the bottom left
        while (true) {

            if (visualString.charAt(iter) == '1') {
                int row = iter / 20;//since lines are in groups of 20 chars, devide i by 20 and truncate decimals (int)
                int col = iter - (row * 20);

                Pose2d c = new Pose2d((double) col / 19.0, 1.0 - ((double) row / 8.0));
                m_points.add(c);
            }

            if (iter % 20 == 19) {
                iter -= 40;
            }
            iter++;

            if (iter < 0) {
                break;
            }
        }
    }

    //if you want to manually set the points
    public PiecewiseFunction(ArrayList<Pose2d> points) {
        m_points = points;
    }

    //to use this function
    public double getVal(double x) {
        double x1 = 0;
        double y1 = 0;
        double x2 = 0;
        double y2 = 0;

        for (int i = 0; i < m_points.size() - 1; i++) {
            if (x >= m_points.get(i).getX() && x < m_points.get(i + 1).getX()) {
                x1 = m_points.get(i).getX();
                y1 = m_points.get(i).getY();
                x2 = m_points.get(i + 1).getX();
                y2 = m_points.get(i + 1).getY();
            }
        }
        //slope is change in y over change in x
        double slope = x2 - x1 != 0 ? (y2 - y1) / (x2 - x1) : 100000;//avoiding a divide by 0
        /*
        To find y intercept use point-slope:
        y - y1 = m(x - x1)
        y - y1 = slope(0-x1)
        y = slope(-x1) + y1
        */
        double yintercept = (slope * -x1) + y1;
        return (x * slope) + yintercept;//plug the x value into our equation
    }
}