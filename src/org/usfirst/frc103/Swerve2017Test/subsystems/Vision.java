package org.usfirst.frc103.Swerve2017Test.subsystems;

import java.util.List;
import java.util.stream.Collectors;

import org.usfirst.frc103.Swerve2017Test.RobotMap;
import org.usfirst.frc103.Swerve2017Test.Pixy2.Block;

public class Vision {
    
    public static GearTarget getGearTarget() {
    	List<Block> filteredBlocks = RobotMap.pixy.getDetectedBlocks()
    			.stream()
    			.filter((Block b) -> (double) b.height / (double) b.width > 2.0)
    			.sorted((Block o1, Block o2) -> o1.centerX - o2.centerX)
    			.collect(Collectors.toList());
    	GearTarget target = null;
    	if (filteredBlocks.size() >= 2) {
    		int targetIndex = 0;
    		int minPairDistance = Integer.MAX_VALUE;
    		for (int i = 0; i < filteredBlocks.size() - 1; i++) {
    			Block leftBlock = filteredBlocks.get(i);
    			for (int j = i; j < filteredBlocks.size(); j++) {
        			Block rightBlock = filteredBlocks.get(j);
        			double heightDifference =
        					(double) Math.abs(leftBlock.height - rightBlock.height) /
        					(double) Math.min(leftBlock.height, rightBlock.height);
        			if (heightDifference > 0.5) {
        				continue;
        			}
        			int pairDistance = rightBlock.centerX - leftBlock.centerX;
        			if (pairDistance < minPairDistance) {
        				targetIndex = i;
        				minPairDistance = pairDistance;
        			}
    			}
    		}
    		Block leftBlock = filteredBlocks.get(targetIndex);
    		Block rightBlock = filteredBlocks.get(targetIndex + 1);
    		double centerX = (double) (leftBlock.centerX + rightBlock.centerX) / 2.0;
    		double height = (double) (leftBlock.height + rightBlock.height) / 2.0;
    		target = new GearTarget(centerX, height);
    	}
    	return target;
    }
    
    public static class GearTarget {
    	public final double centerX;
    	public final double height;
    	public final double distanceEstimate;
    	
		public GearTarget(double centerX, double height) {
			this.centerX = centerX;
			this.height = height;
			this.distanceEstimate = 0.0584974 * Math.pow(height, 2.0) - 4.96322 * (double) height + 143.824;
		}

		@Override
		public String toString() {
			return "GearTarget [centerX=" + centerX + ", height=" + height + ", distanceEstimate=" + distanceEstimate + "]";
		}
    	
    }

}
