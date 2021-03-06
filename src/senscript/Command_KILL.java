package senscript;

import device.SensorNode;

public class Command_KILL extends Command {

	private String arg = "";
	
	public Command_KILL(SensorNode sensor, String arg) {
		this.sensor = sensor ;
		this.arg = arg ;
	}

	@Override
	public double execute() {
		double v = Double.valueOf(sensor.getScript().getVariableValue(arg));
		if(Math.random()<v)
			sensor.getBattery().setLevel(0);
		return 0 ;
	}
	
	@Override
	public String toString() {
		return "KILL";
	}
}
