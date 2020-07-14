package org.wikimedia.heating;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Calendar;

/**
 * The system obtains temperature data from a remote source, compares it with a
 * given threshold and controls a remote heating unit by switching it on and
 * off. It does so only within a time period configured on a remote service (or
 * other source)
 * 
 * This is purpose-built crap
 *
 */
public class ScheduleManager {

	/**
	 * This method is the entry point into the code. You can assume that it is
	 * called at regular interval with the appropriate parameters.
	 */
	public static void manage(HeatingManagerImpl hM, String threshold) throws Exception {
		String t = stringFromURL("http://probe.home:9990/temp", 4);

		boolean active = Calendar.getInstance().get(Calendar.HOUR_OF_DAY) > startHour()
				&& Calendar.getInstance().get(Calendar.HOUR_OF_DAY) < endHour();
		hM.manageHeating(t, threshold, active);

	}

	private static int endHour() throws NumberFormatException, MalformedURLException, IOException {
		return Integer.parseInt(stringFromURL("http://timer.home:9990/end", 2));
	}

	private static String stringFromURL(String urlString, int s) throws MalformedURLException, IOException {
		URL url = new URL(urlString);
		InputStream is = url.openStream();
		byte[] tempBuffer = new byte[s];
		is.read(tempBuffer);
		is.close();
		return new String(tempBuffer);
	}

	private static int startHour() throws NumberFormatException, MalformedURLException, IOException {
		return Integer.parseInt(stringFromURL("http://timer.home:9990/start", 2));
	}

}
