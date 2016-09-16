package de.zabuza.wslotter;

import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.Toolkit;

import javax.swing.JFrame;

import de.zabuza.wslotter.controller.MainFrameController;
import de.zabuza.wslotter.controller.logging.Logger;
import de.zabuza.wslotter.view.MainFrameView;

/**
 * Starts the tool in a frame.
 * 
 * @author Zabuza
 * 
 */
public final class FrameLauncher {

	/**
	 * Launch the view.
	 * 
	 * @param args
	 *            Not supported
	 */
	public static void main(final String[] args) {
		EventQueue.invokeLater(new Runnable() {
			@Override
			public void run() {
				JFrame frame = null;
				MainFrameView window = null;
				Logger logger = null;
				try {
					frame = new JFrame();
					frame.setResizable(false);
					frame.setTitle("WSlotter");
					frame.setBounds(0, 0, MainFrameView.WIDTH, MainFrameView.HEIGHT);
					frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
					frame.getContentPane().setLayout(null);
					Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
					frame.setLocation((screenSize.width - frame.getWidth()) / 2,
							(screenSize.height - frame.getHeight()) / 2);

					window = new MainFrameView(frame);
					logger = new Logger(window);
					MainFrameController controller = new MainFrameController(window, logger);
					controller.initialize();
					controller.start();
				} catch (Exception e) {
					if (logger != null) {
						logger.logUnknownError(e);
					}
				} finally {
					if (frame != null) {
						frame.setVisible(true);
					}
				}
			}
		});
	}

	/**
	 * Utility class. No implementation.
	 */
	private FrameLauncher() {

	}
}
