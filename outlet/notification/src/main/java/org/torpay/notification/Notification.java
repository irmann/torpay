package org.torpay.notification;

public interface Notification {

	void notify(String id, String message);
	void notify(String id,String[] parameters);

}
