package de.zabuza.wslotter.controller.logging;

import de.zabuza.wslotter.view.MainFrameView;

/**
 * Logger of the main frame.
 * 
 * @author Zabuza
 * 
 */
public final class Logger {

	/**
	 * View of the main frame.
	 */
	private final MainFrameView mView;

	/**
	 * Creates a new Logger using the view of the main frame.
	 * 
	 * @param view
	 *            view of the main frame
	 */
	public Logger(final MainFrameView view) {
		mView = view;
	}

	/**
	 * Logs an unknown error.
	 * 
	 * @param e
	 *            The error to log
	 */
	public void logUnknownError(final Exception e) {
		if (mView != null) {
			mView.logError("Ein unbekannter Fehler trat auf.");
			if (e.getMessage() != null) {
				mView.logError(e.getMessage());
			}
		}
	}
}