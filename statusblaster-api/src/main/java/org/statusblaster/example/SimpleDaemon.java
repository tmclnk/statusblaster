package org.statusblaster.example;

import java.net.MalformedURLException;

import org.statusblaster.HttpStatusBlaster;
import org.statusblaster.Status;
import org.statusblaster.StatusBlasterConstants;
import org.statusblaster.StatusMessage;

public class SimpleDaemon {
	public static void main(String[] args) throws MalformedURLException {
		HttpStatusBlaster blaster = new HttpStatusBlaster(
				"http://localhost:" + StatusBlasterConstants.DEFAULT_HTTP_PING_PORT + "/ping");
		for (int i = 0; i < 5000; i++) {
			blaster.blast(new StatusMessage("BlastTron5000", Status.NOT_DEAD_YET));
		}
	}
}
