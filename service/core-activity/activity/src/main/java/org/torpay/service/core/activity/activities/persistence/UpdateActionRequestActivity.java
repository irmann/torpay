package org.torpay.service.core.activity.activities.persistence;

import org.torpay.service.core.activity.controller.CoreActivity;
import org.torpay.service.core.activity.controller.CoreActivityNames;

public class UpdateActionRequestActivity extends StoreActionRequestActivity
		implements CoreActivity {
	@Override
	public String getName() {
		return CoreActivityNames.UPDATE_ACTION_REQUEST;
	}
}
