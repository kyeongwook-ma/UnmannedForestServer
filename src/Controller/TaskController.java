package Controller;

import java.util.ArrayList;

public class TaskController {

	private ArrayList<Runnable> taskPool;
	private static TaskController mInstance;

	static {
		mInstance = new TaskController();
	}

	public static TaskController getInstance() {
		if( mInstance == null ) {
			mInstance = new TaskController();
		} return mInstance;
	}

	private TaskController() { taskPool = new ArrayList<Runnable>(); }

	
	public void enqueueTaskNoLock(Runnable t) {
		t.run();
		taskPool.add(t);
	}
	
	public synchronized void enqueueTask(Runnable t) {
	
		t.run();
		taskPool.add(t);

	}

	public synchronized Object dequeTask() {

		while( isAllTaskDone() ) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}			
		}

		return taskPool.remove(0);
	}

	public boolean isAllTaskDone() {
		return taskPool.size() == 0 ? true : false;
	}

}
