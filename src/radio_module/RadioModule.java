package radio_module;

import java.awt.Color;
import java.awt.Graphics;
import java.io.PrintStream;

import device.DeviceList;
import device.SensorNode;
import utilities.UColor;

public abstract class RadioModule {

	protected String name ;
	protected int radioDataRate = 250000; // zigbee: default
	
	protected double radioRangeRadius = 0 ;
	protected double radioRangeRadiusOri = 0 ;
	
	protected Color radioRangeColor1 = UColor.PURPLE_TTRANSPARENT;
	protected Color radioRangeColor2 = UColor.PURPLE_TRANSPARENT;
	
	protected double eTx = 0.0000592; //sending energy consumption
	protected double eRx = 0.0000286; // receiving energy consumption
	protected double eSleep = 0.0000001;//Sleeping energy
	protected double eListen = 0.000001;//Listening energy	
	
	protected int my = 0;
	protected double pl = 100;
	protected int ch = 0x0;
	protected int nId = 0x3334;
	
	protected double timeToResend = 3.01;//0.001 ;
	protected int numberOfSends = 3 ;
	protected int attempts = 0;
	
	protected double frequency = 2.4e9; // GHz (zigbee: default)
	
//------ Simple Propagation
	protected double requiredQuality = -80.0; // dB
	protected double transmitPower = 0 ; // dBm
	
	protected SensorNode sensorNode;
	
	public RadioModule(SensorNode sensorNode, String name) {
		this.sensorNode = sensorNode;
		this.name = name;
	}
	
	public abstract int getStandard();
	
	public int getRadioDataRate() {
		return radioDataRate;
	}
	
	public void setRadioDataRate(int radioDataRate) {
		this.radioDataRate = radioDataRate;
	}
	
	public abstract String getStandardName();
	
	public static int getStandardByName(String stdName) {
		if(stdName.equals("NONE")) {
			return RadioStandard.NONE;			
		}
		if(stdName.equals("ZIGBEE")) {
			return RadioStandard.ZIGBEE_802_15_4;
		}
		if(stdName.equals("WIFI")) {
			return RadioStandard.WIFI_802_11;
		}
		if(stdName.equals("LORA")) {
			return RadioStandard.LORA;
		}
		return -1;
	}
	
	public static String getNameByStandard(int std) {
		if(std == RadioStandard.NONE) {
			return "NONE";			
		}
		if(std == RadioStandard.ZIGBEE_802_15_4) {
			return "ZIGBEE";
		}
		if(std == RadioStandard.WIFI_802_11) {
			return "WIFI";
		}
		if(std == RadioStandard.LORA) {
			return "LORA";
		}
		return "";
	}
	
	public String getName() {
		return name;
	}

	/**
	 * Consume
	 * 
	 * @param v
	 */
	public void consume(int v) {
		sensorNode.getBattery().consume(v);
	}
	
	/**
	 * consumeTx
	 * 
	 * @param v
	 */
	public void consumeTx(int v) {
		sensorNode.getBattery().consume(v*eTx*pl/100.);
//		double d = (radioRangeRadius*pl/100.); 
//		if(d<200) {
//			//System.out.println(v*5e-8+v*6e-12*d*d);
//			sensorNode.getBattery().consume(v*5e-8+v*6e-12*d*d);
//		}
//		else
//			sensorNode.getBattery().consume(v*5e-8+v*1.1e-15*d*d*d*d);
	}
	
	/**
	 * consumeRx
	 * 
	 * @param v
	 */
	public void consumeRx(int v) {
		sensorNode.getBattery().consume(v*eRx);
		//sensorNode.getBattery().consume(v*5e-8);
	}
	
	
	/**
	 * ConsumeTx v units
	 * 
	 * @param v
	 */
	public void consumeTx(double v) {
	}
	
	/**
	 * ConsumeRx v units
	 * 
	 * @param v
	 */
	public void consumeRx(double v) {
	}

	
	public void setMy(int my) {
		this.my = my;
	}
	
	public void setPl(double pl) {
		this.pl = pl;
		this.getSensorNode().calculateRadioSpace();
	}
	
	public double getPl() {
		return pl;
	}
		
	public double getETx() {
		return eTx;
	}

	public void setETx(double eTx) {
		this.eTx = eTx;
	}

	public double getERx() {
		return eRx;
	}

	public void setERx(double eRx) {
		this.eRx = eRx;
	}
	
	public double getESleep() {
		return eSleep;
	}

	public void setESleep(double eSleep) {
		this.eSleep = eSleep;
	}

	public double getEListen() {
		return eListen;
	}

	public void setEListen(double eListen) {
		this.eListen = eListen;
	}

	public int getCh() {
		return ch;
	}

	public void setCh(int ch) {
		this.ch = ch;
	}

	public int getNId() {
		return nId;
	}

	public void setNId(int nId) {
		this.nId = nId;
	}
		
	/**
	 * @param x
	 * @param y
	 * @param r
	 * @param g
	 */
	public void drawRadioRadius(int x, int y, int r, Graphics g) {
		if (r > 0 && sensorNode.getDisplaydRadius()) {
			g.setColor(UColor.WHITE_TRANSPARENT);
			int lr1 = (int) (r * Math.cos(Math.PI / 4.));
			g.drawLine(x, y, (int) (x + lr1), (int) (y - lr1));
			g.drawString("" +r+" m", x + (lr1 / 2), (int) (y - (lr1 / 4.)));
		}		
	}

	public double getRequiredQuality() {
		return requiredQuality;
	}

	public void setRequiredQuality(double requiredQuality) {
		this.requiredQuality = requiredQuality;
	}

	public int getMy() {
		return my;
	}

	public double getTimeToResend() {
		return timeToResend;
	}

	public void setTimeToResend(double timeToResend) {
		this.timeToResend = timeToResend;
	}

	public int getNumberOfSends() {
		return numberOfSends;
	}

	public void setNumberOfSends(int numberOfSends) {
		this.numberOfSends = numberOfSends;
	}
	
	public double getRadioRangeRadius() {
		return radioRangeRadius;
	}	
	
	public void setRadioRangeRadius(double radioRangeRadius) {
		this.radioRangeRadius = radioRangeRadius;
		if(DeviceList.propagationsCalculated)			
			DeviceList.calculatePropagations();
	}
	
	public double getRadioRangeRadiusOri() {
		return radioRangeRadiusOri;
	}

	public void setRadioRangeRadiusOri(double radioRangeRadiusOri) {
		this.radioRangeRadiusOri = radioRangeRadiusOri;
	}
	
	public Color getRadioRangeColor1() {	
		return radioRangeColor1;
	}
	
	public Color getRadioRangeColor2() {	
		return radioRangeColor2;
	}
	
	public void resizeRadioRangeRadius(int i) {
		radioRangeRadius += i;
	}
	
	public void resizeRadioRangeRadiusOri(int i) {
		radioRangeRadius += i;
	}
	
	public abstract void init();

	public abstract double getTransmitPower();
	
	public void setTransmitPower(double transmitPower) {
		this.transmitPower = transmitPower;
	}
	
	public double getFrequency() {
		return frequency;
	}
	
	public abstract RadioModule duplicate(SensorNode sensorNode);
	
	public SensorNode getSensorNode() {
		return sensorNode;
	}
	
	public int getAttempts() {
		return attempts;
	}
	
	public void setAttempts(int attempts) {
		this.attempts = attempts;
	}
	
	public void incAttempts() {
		attempts++;
	}
	
	public static RadioModule newRadioModule(SensorNode sensor, String name, String sStandard) {
		int standard = getStandardByName(sStandard);				
		return newRadioModule(sensor, name, standard);
	}
	
	public static RadioModule newRadioModule(SensorNode sensor, String name, int standard) {		
		if(standard == RadioStandard.ZIGBEE_802_15_4) {
			return new RadioModule_ZigBee(sensor, name);
		}
		
		if(standard == RadioStandard.LORA) {
			return new RadioModule_Lora(sensor, name);
		}
		
		if(standard == RadioStandard.WIFI_802_11) {
			return new RadioModule_Wifi(sensor, name);
		}
		return null;
	}	
	
	public abstract void save(PrintStream fos, RadioModule currentRadioModule);
	
	public int getSpreadingFactor() {
		return -1;
	}
	
	public void setSpreadingFactor(int v) {}
}
