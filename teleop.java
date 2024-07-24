package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import java.util.Arrays;

@TeleOp(name = "Teleop")
public class Teleop extends LinearOpMode {
    Robot robot = new Robot();
    int robotCycle = 0;

    //Servo claw1;
    // double setPosition = 0.0;

    @Override
    public void runOpMode() throws InterruptedException {


        //initialization variables, notifying robot is initialized and shows how long robot ran for
        telemetry.addData("Status", "Initialized");
        telemetry.addData("Status", "Runtime " + robot.runtime.toString());
        telemetry.update();


        robot.init(hardwareMap);

        robot.lift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();

        while (opModeIsActive()) {
            telemetry.addData("Robot Cycle", robotCycle);
            //  telemetry.addData("Arm Encoder Value", robot.lift.getCurrentPosition());
            telemetry.addData("Match Time (s)", getRuntime());
            telemetry.addData("FL Count", robot.frontLeft.getCurrentPosition());
            telemetry.addData("FR Count", robot.frontRight.getCurrentPosition());
            telemetry.addData("BL Count", robot.backLeft.getCurrentPosition());
            telemetry.addData("BR Count", robot.backRight.getCurrentPosition());
            telemetry.addData("Status", "Resetting Values");
            telemetry.update();
            //controller 1 functions
            double FrontLeftVal = gamepad1.left_stick_y - gamepad1.left_stick_x - gamepad1.right_stick_x;
            double FrontRightVal = gamepad1.left_stick_y + (gamepad1.left_stick_x) + gamepad1.right_stick_x;
            double BackLeftVal = gamepad1.left_stick_y + (gamepad1.left_stick_x) - gamepad1.right_stick_x;
            double BackRightVal = gamepad1.left_stick_y - (gamepad1.left_stick_x) + gamepad1.right_stick_x;


            // change orientation bc going forward is backwards
            //Move range to between 0 and +1, if not already
            double[] wheelPowers = {FrontRightVal, FrontLeftVal, BackLeftVal, BackRightVal};
            Arrays.sort(wheelPowers);
            if (wheelPowers[3] > 1) {
                FrontLeftVal /= wheelPowers[3];
                FrontRightVal /= wheelPowers[3];
                BackRightVal /= wheelPowers[3];
                BackLeftVal /= wheelPowers[3];

            }

            telemetry.addData("PTFrLe", FrontLeftVal);
            telemetry.addData("PTFrRi", FrontRightVal);
            telemetry.addData("PTBaLe", BackLeftVal);
            telemetry.addData("PTBaRr", BackRightVal);
            telemetry.update();
            // set power to wheel motors
            robot.frontLeft.setPower(FrontLeftVal);
            robot.frontRight.setPower(FrontRightVal);
            robot.backLeft.setPower(BackLeftVal);
            robot.backRight.setPower(BackRightVal);

            // arm servo controls


            if (gamepad2.x) {
                robot.claw1.setPosition(1);
            } else if (gamepad2.y) {
                robot.claw1.setPosition(0);     //pos 0 is closed and 1 is open
            }

            if (gamepad2.x) {
                robot.claw1.setPosition(1);
            } else if (gamepad2.y) {
                robot.claw1.setPosition(0);     //pos 0 is closed and 1 is open
            }

            //robot.claw1.setPosition(gamepad2.right_trigger);
            if (gamepad2.left_trigger > 0) {
                robot.claw1.setPosition(gamepad2.left_trigger);
            }
            // Lift
            if (gamepad2.left_trigger == 1) {

                robot.lift.setPower(1);
            } else if (gamepad2.right_trigger == 1) {
                robot.lift.setPower(-1);
            }
            else {
                robot.lift.setPower(0);
            }

            // only touch this
            robot.lift.setPower(-gamepad2.left_stick_y * 0.6);
            // update lift power if arm flies back (noted at field ins arm flew back and dropped pixel

            //Drone Launch Code

            if (gamepad1.b) {
                robot.DroneLaunch.setPower(0.5);
            }
            else if (gamepad1.y) {
                robot.DroneLaunch.setPower(-0.5);
            }
            else {
                robot.DroneLaunch.setPower(0);
            }

            //Rigging

            if (gamepad1.a) {
                robot.Rigging.setPower(0.75);
                robot.Rigging2.setPower(0.75);

            }
            else if (gamepad1.x) {
                robot.Rigging.setPower(-0.75);
                robot.Rigging2.setPower(-0.75);
            }
            else {
                robot.Rigging.setPower(0);
                robot.Rigging2.setPower(0);
            }

            idle();
        }
    }
}
































