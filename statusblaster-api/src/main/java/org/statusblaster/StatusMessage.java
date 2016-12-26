package org.statusblaster;

import java.io.Serializable;

public class StatusMessage implements Serializable {
	private static final long serialVersionUID = 6998796807500503088L;

	public StatusMessage(String key, Status status) {
		super();
		this.key = key;
		this.status = status;
	}

	private String key;

	public String getKey() {
		return key.toLowerCase();
	}

	public void setKey(String key) {
		this.key = key;
	}

	public Status getStatus() {
		return status;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	private Status status;
}
