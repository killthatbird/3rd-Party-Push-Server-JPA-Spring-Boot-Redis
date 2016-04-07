package org.wlfek.push.component;

import org.junit.Before;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.wlfek.push.domain.GcmSend;

@RunWith(MockitoJUnitRunner.class)
public class ScheduledTasksTest {
	@Mock
	private GcmSend sender;
	private ScheduledTasks scheduledTasks;

	@Before
	public void createScheduledTasks() throws Exception {
		scheduledTasks = new ScheduledTasks();
		scheduledTasks.setSender(sender);
	}

}
