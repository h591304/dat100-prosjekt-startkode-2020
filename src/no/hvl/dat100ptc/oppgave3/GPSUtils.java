package no.hvl.dat100ptc.oppgave3;

import static java.lang.Math.*;

import no.hvl.dat100ptc.TODO;
import no.hvl.dat100ptc.oppgave1.GPSPoint;

public class GPSUtils {

	public static double findMax(double[] da) {

		double max; 
		
		max = da[0];
		
		for (double d : da) {
			if (d > max) {
				max = d;
			}
		}
		
		return max;
	}

	public static double findMin(double[] da) {

		double min;

		min = da[0];
		
		for (double d : da) {
			if (d < min) {
				min = d;
			}
		}
		
		return min;

	}

	public static double[] getLatitudes(GPSPoint[] gpspoints) {

		double [] latitude = new double[gpspoints.length];
		int i;
		
		for(i = 0; i < gpspoints.length; i++) {
			latitude[i] = gpspoints[i].getLatitude();
		}
		
		return latitude;
	}

	public static double[] getLongitudes(GPSPoint[] gpspoints) {

		double [] longitude = new double[gpspoints.length];
		int i;
		
		for(i = 0; i < gpspoints.length; i++) {
			longitude[i] = gpspoints[i].getLongitude();
			
		}
		return longitude;
	}

	private static int R = 6371000; // jordens radius

	public static double distance(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		double d;
		double latitude1, longitude1, latitude2, longitude2;
		double a, c;
		double deltalat, deltalong;
		
		latitude1 = gpspoint1.getLatitude();
		latitude2 = gpspoint2.getLatitude();
		longitude1 = gpspoint1.getLongitude();
		longitude2 = gpspoint2.getLongitude();
		
		deltalat = Math.toRadians(latitude2 - latitude1);
		deltalong = Math.toRadians(longitude2 - longitude1);
		
		a = Math.pow(Math.sin(deltalat/2), 2) + Math.cos(toRadians(latitude1)) * 
		Math.cos(toRadians(latitude2)) * Math.pow(Math.sin(deltalong/2), 2);
		c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		d = R * c;
		
		return d;

	}

	public static double speed(GPSPoint gpspoint1, GPSPoint gpspoint2) {

		int secs;
		double speed;
		double di;
		
		di = distance(gpspoint1, gpspoint2); 
		secs = gpspoint2.getTime() - gpspoint1.getTime();
		speed = (di / secs * 60 * 60)/1000;
		
		return speed;
	}

	public static String formatTime(int secs) {

		String timestr;
		String TIMESEP = ":";

		int hr = secs / 60 / 60;
		int min = (secs - hr * 3600)/ 60;
		int sec = secs % 60;
		
		String secStr = String.format("%02d", sec);
		String minStr = String.format("%02d", min);
		String hrStr = String.format("%02d", hr);
		
		timestr = "  " + hrStr + TIMESEP + minStr +  TIMESEP + secStr;

		return timestr;

	}
	private static int TEXTWIDTH = 10;

	public static String formatDouble(double d) {

		String str;

		//str = String.format("%10.2f", d).replace(",", ".");  - alternativ metode
		str = String.format("%" + TEXTWIDTH + ".2f", d).replace(",", ".");

		return str;
		
	}
}
