package de.zabuza.wslotter.controller;

import de.zabuza.wslotter.controller.listener.StartActionListener;
import de.zabuza.wslotter.controller.listener.StopActionListener;
import de.zabuza.wslotter.controller.listener.StopAtWindowCloseListener;
import de.zabuza.wslotter.controller.logging.Logger;
import de.zabuza.wslotter.model.tasks.RoutineTask;
import de.zabuza.wslotter.view.MainFrameView;

/**
 * The controller of the main frame.
 * 
 * @author Zabuza
 * 
 */
public final class MainFrameController {
	/**
	 * The time to wait for a thread to be finished after interrupting, in
	 * milliseconds.
	 */
	private static final long INTERRUPT_WAIT = 2000;
	/**
	 * The current executing routine.
	 */
	private RoutineTask mCurrentRoutine;
	/**
	 * Logger of the main frame.
	 */
	private final Logger mLogger;
	/**
	 * The view of the main frame.
	 */
	private final MainFrameView mView;

	/**
	 * Creates a new controller of the main frame by connecting it to the view.
	 * 
	 * @param view
	 *            view of the main frame
	 * @param logger
	 *            logger of the main frame
	 */
	public MainFrameController(final MainFrameView view, final Logger logger) {
		mView = view;
		mLogger = logger;
		mCurrentRoutine = null;
	}

	/**
	 * Initializes the controller.
	 */
	public void initialize() {
		linkListener();
	}

	/**
	 * Call this method when the routine has finished.
	 */
	public void routineFinished() {
		mCurrentRoutine = null;
		mLogger.logInfo("Routine finished.", Logger.TOP_LEVEL);
		mView.setAllInputEnabled(true);
		mView.setStartButtonEnabled(true);
		mView.setStopButtonEnabled(false);
	}

	/**
	 * Starts the controller.
	 */
	public void start() {

	}

	/**
	 * Starts the routine.
	 */
	public void startRoutine() {
		mLogger.logInfo("Routine started.", Logger.TOP_LEVEL);
		mView.setAllInputEnabled(false);
		mView.setStartButtonEnabled(false);
		mView.setStopButtonEnabled(true);

		mCurrentRoutine = new RoutineTask(mView.getThreadUrl(), mView.getTextToPost(), mView.getUsername(),
				mView.getPassword(), mView.getBrowser(), mLogger, this);
		mCurrentRoutine.start();
	}

	/**
	 * Stops the routine.
	 */
	public void stopRoutine() {
		mLogger.logInfo("Routine stopped.", Logger.TOP_LEVEL);
		if (mCurrentRoutine != null) {
			mCurrentRoutine.interrupt();
			try {
				mCurrentRoutine.join(INTERRUPT_WAIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Links the listener to the view.
	 */
	private void linkListener() {
		mView.addListenerToStartAction(new StartActionListener(mView, this));
		mView.addListenerToStopAction(new StopActionListener(mView, this));
		mView.addWindowListener(new StopAtWindowCloseListener(this));
	}
}