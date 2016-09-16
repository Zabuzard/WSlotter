package de.zabuza.wslotter.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.zabuza.wslotter.controller.MainFrameController;
import de.zabuza.wslotter.view.MainFrameView;

/**
 * Listener of the stop action.
 * 
 * @author Zabuza
 *
 */
public class StopActionListener implements ActionListener {
	/**
	 * The controller of the main frame.
	 */
	private final MainFrameController mController;
	/**
	 * The view of the main frame.
	 */
	private final MainFrameView mView;

	/**
	 * Creates a new listener of the stop action.
	 * 
	 * @param view
	 *            View of the main frame
	 * @param controller
	 *            Controller of the main frame
	 */
	public StopActionListener(final MainFrameView view, final MainFrameController controller) {
		mView = view;
		mController = controller;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	@Override
	public void actionPerformed(final ActionEvent e) {
		mController.stopRoutine();
	}
}
