package de.zabuza.wslotter.controller;

import javax.swing.JFrame;

import de.zabuza.wslotter.controller.listener.StartActionListener;
import de.zabuza.wslotter.controller.listener.StopActionListener;
import de.zabuza.wslotter.controller.listener.StopAtWindowCloseListener;
import de.zabuza.wslotter.controller.logging.Logger;
import de.zabuza.wslotter.controller.settings.SettingsController;
import de.zabuza.wslotter.model.tasks.RoutineTask;
import de.zabuza.wslotter.view.MainFrameView;

/**
 * The controller of the main frame.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
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
	 * The controller for the settings.
	 */
	private final SettingsController mSettingsController;
	/**
	 * The view of the main frame.
	 */
	private final MainFrameView mView;

	/**
	 * Creates a new controller of the main frame by connecting it to the view.
	 * 
	 * @param owner
	 *            The owning frame of this controller
	 * @param view
	 *            view of the main frame
	 * @param logger
	 *            logger of the main frame
	 */
	public MainFrameController(final JFrame owner, final MainFrameView view, final Logger logger) {
		this.mView = view;
		this.mLogger = logger;
		this.mSettingsController = new SettingsController(owner, view, logger);
		this.mCurrentRoutine = null;
	}

	/**
	 * Initializes the controller.
	 */
	public void initialize() {
		linkListener();
		this.mSettingsController.initialize();
	}

	/**
	 * Call this method when the routine has finished.
	 */
	public void routineFinished() {
		this.mCurrentRoutine = null;
		this.mLogger.logInfo("Routine finished.", Logger.TOP_LEVEL);
		this.mView.setAllInputEnabled(true);
		this.mView.setStartButtonEnabled(true);
		this.mView.setStopButtonEnabled(false);
		this.mView.setSettingsButtonEnabled(true);
	}

	/**
	 * Starts the controller.
	 */
	public void start() {
		// Nothing to do yet
	}

	/**
	 * Starts the routine.
	 */
	public void startRoutine() {
		this.mLogger.logInfo("Routine started.", Logger.TOP_LEVEL);
		this.mView.setAllInputEnabled(false);
		this.mView.setStartButtonEnabled(false);
		this.mView.setStopButtonEnabled(true);
		this.mView.setSettingsButtonEnabled(false);

		this.mCurrentRoutine = new RoutineTask(this.mView.getThreadUrl(), this.mView.getTextToPost(),
				this.mView.getUsername(), this.mView.getPassword(), this.mView.getBrowser(), this.mLogger, this,
				this.mSettingsController);
		this.mCurrentRoutine.start();
	}

	/**
	 * Stops the routine.
	 */
	public void stopRoutine() {
		this.mLogger.logInfo("Routine stopped.", Logger.TOP_LEVEL);
		if (this.mCurrentRoutine != null) {
			this.mCurrentRoutine.interrupt();
			try {
				this.mCurrentRoutine.join(INTERRUPT_WAIT);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Links the listener to the view.
	 */
	private void linkListener() {
		this.mView.addListenerToStartAction(new StartActionListener(this));
		this.mView.addListenerToStopAction(new StopActionListener(this));
		this.mView.addWindowListener(new StopAtWindowCloseListener(this));
	}
}