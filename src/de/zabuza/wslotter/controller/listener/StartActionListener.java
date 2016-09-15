package de.zabuza.wslotter.controller.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import de.zabuza.wslotter.controller.MainFrameController;
import de.zabuza.wslotter.view.MainFrameView;

/**
 * Listener of the start action.
 * 
 * @author Zabuza
 *
 */
public class StartActionListener implements ActionListener {
	/**
	 * The controller of the main frame.
	 */
	private final MainFrameController mController;
	/**
	 * The view of the main frame.
	 */
	private final MainFrameView mView;

	/**
	 * Creates a new listener of the start action.
	 * 
	 * @param view
	 *            View of the main frame
	 * @param controller
	 *            Controller of the main frame
	 */
	public StartActionListener(final MainFrameView view, final MainFrameController controller) {
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
		mView.setAllInputEnabled(false);
		mView.setStartButtonEnabled(false);
		mView.setStopButtonEnabled(true);

		mController.executeStartTask();
	}
}
