// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Swerve;
import frc.robot.util.LimelightHelpers;

public class FollowAprilTag extends CommandBase {
  final Swerve swerve;

  final double desiredZDistance = 1.0;

  PIDController forwardController = new PIDController(1.25, 0, 0);
  PIDController strafeController = new PIDController(1.25, 0, 0);
  PIDController rotationController = new PIDController(0.75, 0, 0);

  /** Creates a new FollowAprilTag. */
  public FollowAprilTag(Swerve swerve) {
    this.swerve = swerve;
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(swerve);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    if (LimelightHelpers.getTV("limelight")) {
      Pose3d targetPose = LimelightHelpers.getTargetPose3d_CameraSpace("limelight");

      double forwardSpeed = forwardController.calculate(targetPose.getZ(), desiredZDistance);
      double strafeSpeed = -strafeController.calculate(targetPose.getX(), 0.0);
      double rotationSpeed = -rotationController.calculate(targetPose.getRotation().getY(), 0.0);

      swerve.driveRobotOriented(forwardSpeed, strafeSpeed, rotationSpeed, true, true);
    } else {
      swerve.driveRobotOriented(0.0, 0.0, 0.0, true, true);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
