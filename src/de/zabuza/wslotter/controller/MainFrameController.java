package de.zabuza.wslotter.controller;

import de.zabuza.wslotter.controller.logging.Logger;
import de.zabuza.wslotter.view.MainFrameView;

/**
 * The controller of the main frame.
 * 
 * @author Zabuza
 * 
 */
public final class MainFrameController {
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
	}

	/**
	 * Initializes the controller.
	 */
	public void initialize() {
		linkListener();
	}

	/**
	 * Starts the controller.
	 */
	public void start() {

	}

	/**
	 * Links the listener to the view.
	 */
	private void linkListener() {
		// mView.addListenerToChooseButton(new ChooseListener(mView, this,
		// mLogger));
		// mView.addListenerToValueButtons(new ValueListener(mView));
	}
}