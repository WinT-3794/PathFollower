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

package org.wint3794.pathfollower.movement.road;

import org.wint3794.pathfollower.controllers.Robot;
import org.wint3794.pathfollower.debug.ComputerDebugging;
import org.wint3794.pathfollower.models.structures.Pose2D;
import org.wint3794.pathfollower.utils.CurvePoint;
import org.wint3794.pathfollower.utils.Point;
import org.wint3794.pathfollower.utils.Range;

import java.util.ArrayList;
import java.util.List;

import static org.wint3794.pathfollower.robot.RuntimeRobotVars.*;
import static org.wint3794.pathfollower.utils.MathUtils.AngleWrap;
import static org.wint3794.pathfollower.utils.MathUtils.lineCircleintersection;

public class RobotMovement {
    public static void followCurve(List<CurvePoint> allPoints, double followAngle) {
        for (int i = 0; i < allPoints.size() - 1; i++) {
            ComputerDebugging.sendLine(new Pose2D(allPoints.get(i).x, allPoints.get(i).y), new Pose2D(allPoints.get(i + 1).x, allPoints.get(i + 1).y));
        }

        CurvePoint followMe = getFollowPointPath(allPoints, new Point(Robot.getXPos(), Robot.getYPos()), allPoints.get(0).followDistance);

        ComputerDebugging.sendKeyPoint(new Pose2D(followMe.x, followMe.y));

        moveToPosition(followMe.x, followMe.y, followMe.moveSpeed, followAngle, followMe.turnSpeed);
    }

    public static CurvePoint getFollowPointPath(List<CurvePoint> pathPoints, Point robotLocation, double followRadius) {
        CurvePoint followMe = new CurvePoint(pathPoints.get(0));


        for (int i = 0; i < pathPoints.size() - 1; i++) {
            CurvePoint startLine = pathPoints.get(i);
            CurvePoint endline = pathPoints.get(i + 1);

            ArrayList<Point> intersections = lineCircleintersection(robotLocation, followRadius, startLine.toPoint(), endline.toPoint());

            double closestAngle = 100000000;

            for (Point thisIntersection : intersections) {
                double angle = Math.atan2(thisIntersection.y - Robot.getYPos(), thisIntersection.x - Robot.getYPos());
                double deltaAngle = Math.abs(AngleWrap(angle - Robot.getWorldAngle()));

                if (deltaAngle < closestAngle) {
                    closestAngle = deltaAngle;
                    followMe.setPoint(thisIntersection);
                }
            }
        }
        return followMe;
    }


    public static void moveToPosition(double x, double y, double Speed, double preferredTurnAngle, double turnSpeed) {
        double distanceToTarget = Math.hypot(x - Robot.getXPos(), y - Robot.getYPos());
        double absoluteAngleToTarget = Math.atan2(y - Robot.getYPos(), x - Robot.getXPos());
        double relativeAngleToTarget = AngleWrap(absoluteAngleToTarget - (Robot.getWorldAngle() - Math.toRadians(90)));


        double relativeX = Math.cos(relativeAngleToTarget) * distanceToTarget;
        double relativeY = Math.sin(relativeAngleToTarget) * distanceToTarget;

        double v = Math.abs(relativeX) + Math.abs(relativeY);
        double movementXPower = relativeX / v;
        double movementYPower = relativeY / v;

        movementX = movementXPower * Speed;
        movementY = movementYPower * Speed;

        double relativeTurnAngle = relativeAngleToTarget - Math.toRadians(180) + preferredTurnAngle;
        movementTurn = Range.clip(relativeTurnAngle / Math.toRadians(30), -1, 1) * turnSpeed;

        if (distanceToTarget < 10) {
            movementTurn = 0;
        }
    }
}