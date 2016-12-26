package org.statusblaster.example;

import java.net.MalformedURLException;

import org.statusblaster.HttpStatusBlaster;
import org.statusblaster.Status;
import org.statusblaster.StatusBlasterConstants;
import org.statusblaster.StatusMessage;

public class SimpleApp {

	public static void main(String[] args) throws MalformedURLException {
		HttpStatusBlaster blaster = new HttpStatusBlaster("http://localhost:" + StatusBlasterConstants.DEFAULT_HTTP_PING_PORT + "/ping");
		blaster.blast(new StatusMessage("SimpleApp", Status.NOT_DEAD_YET));
	}

}
