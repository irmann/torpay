package org.torpay.notification;

import org.springframework.stereotype.Component;

@Component
public class NotificationImpl implements Notification{

	@Override
	public void notify(String id, String message) {
		// TODO implement me
		// lookup id from table
		// check it is sms or email
		// send message to the address by sms or email
		// save notification in database
		
	}

	@Override
	public void notify(String id, String[] parameters) {
		
		
	}

}
