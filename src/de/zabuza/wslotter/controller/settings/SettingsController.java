package de.zabuza.wslotter.controller.settings;

import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JFrame;
import javax.swing.JTextField;

import de.zabuza.wslotter.controller.listener.CloseAtCancelActionListener;
import de.zabuza.wslotter.controller.listener.ClosingCallbackWindowListener;
import de.zabuza.wslotter.controller.listener.FileChooseSetActionListener;
import de.zabuza.wslotter.controller.listener.SaveActionListener;
import de.zabuza.wslotter.controller.listener.SettingsActionListener;
import de.zabuza.wslotter.controller.logging.Logger;
import de.zabuza.wslotter.model.EBrowser;
import de.zabuza.wslotter.view.MainFrameView;
import de.zabuza.wslotter.view.SettingsDialog;

/**
 * The controller of the settings.
 * 
 * @author Zabuza
 *
 */
public final class SettingsController implements ISettingsProvider {
	/**
	 * Text to save for a value if a key is unknown.
	 */
	public static final String UNKNOWN_KEY_VALUE = "";
	/**
	 * Key identifier for driver settings.
	 */
	private static final String KEY_IDENTIFIER_DRIVER = "driver";
	/**
	 * Separator which separates several information in a key.
	 */
	private static final String KEY_INFO_SEPARATOR = "@";
	/**
	 * The logger used by this object.
	 */
	private final Logger mLogger;
	/**
	 * The owning frame of this controller.
	 */
	private final JFrame mOwner;
	/**
	 * The object for the settings.
	 */
	private final Settings mSettings;
	/**
	 * The settings dialog or <tt>null</tt> if currently not opened.
	 */
	private SettingsDialog mSettingsDialog;
	/**
	 * Structure which saves all currently loaded settings.
	 */
	private final Map<String, String> mSettingsStore;
	/**
	 * The view of the main frame.
	 */
	private final MainFrameView mView;

	/**
	 * Creates a new controller of the settings.
	 * 
	 * @param owner
	 *            The owning frame of this controller
	 * @param view
	 *            The view to control
	 * @param logger
	 *            The logger to use
	 */
	public SettingsController(final JFrame owner, final MainFrameView view, final Logger logger) {
		mView = view;
		mLogger = logger;
		mOwner = owner;

		mSettingsStore = new HashMap<>();
		mSettings = new Settings(mLogger);
		mSettingsDialog = null;
	}

	/**
	 * Call whenever the settings dialog is closing. This is used as callback to
	 * free the parent window of the dialog.
	 */
	public void closingSettingsDialog() {
		mView.setAllInputEnabled(true);
		mView.setStartButtonEnabled(true);
		mView.setStopButtonEnabled(false);
		mView.setSettingsButtonEnabled(true);
	}

	/**
	 * Call whenever the save action is to be executed. This will save all
	 * settings and close the settings dialog.
	 */
	public void executeSaveAction() {
		// Driver settings
		for (EBrowser browser : EBrowser.values()) {
			JTextField field = mSettingsDialog.getBrowserDriverField(browser);
			String value = field.getText();
			if (!value.equals(UNKNOWN_KEY_VALUE)) {
				String key = KEY_IDENTIFIER_DRIVER + KEY_INFO_SEPARATOR + browser;
				setSetting(key, value);
			}
		}
		
		// Save settings
		mSettings.saveSettings(this);

		// Close the settings dialog
		mSettingsDialog.dispatchEvent(new WindowEvent(mSettingsDialog, WindowEvent.WINDOW_CLOSING));
	}

	/**
	 * Call whenever the settings action is to be executed. This will open a
	 * settings dialog.
	 */
	public void executeSettingsAction() {
		// Deactivate all actions until the settings dialog has closed
		mView.setAllInputEnabled(false);
		mView.setStartButtonEnabled(false);
		mView.setStopButtonEnabled(false);
		mView.setSettingsButtonEnabled(false);

		// Open the dialog
		mSettingsDialog = new SettingsDialog(mOwner);
		linkDialogListener();

		// Load settings to the store
		passSettingsToView();
		mSettingsDialog.setVisible(true);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.wslotter.controller.settings.ISettingsProvider#getAllSettings()
	 */
	@Override
	public Map<String, String> getAllSettings() {
		return mSettingsStore;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.wslotter.controller.settings.ISettingsProvider#getSetting(java.
	 * lang.String)
	 */
	@Override
	public String getSetting(final String key) {
		String value = mSettingsStore.get(key);
		if (value == null) {
			value = UNKNOWN_KEY_VALUE;
		}
		return value;
	}

	/**
	 * Initializes the controller.
	 */
	public void initialize() {
		linkListener();
		mSettings.loadSettings(this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.zabuza.wslotter.controller.settings.ISettingsProvider#setSetting(java.
	 * lang.String, java.lang.String)
	 */
	@Override
	public void setSetting(final String key, final String value) {
		mSettingsStore.put(key, value);
	}

	/**
	 * Links the listener of the dialog to it.
	 */
	private void linkDialogListener() {
		mSettingsDialog.addWindowListener(new ClosingCallbackWindowListener(this));
		for (EBrowser browser : EBrowser.values()) {
			ActionListener listener = new FileChooseSetActionListener(mSettingsDialog,
					mSettingsDialog.getBrowserDriverField(browser));
			mSettingsDialog.addListenerToBrowserDriverSelectionAction(browser, listener);
		}
		mSettingsDialog.addListenerToSaveAction(new SaveActionListener(this));
		mSettingsDialog.addListenerToCancelAction(new CloseAtCancelActionListener(mSettingsDialog));
	}

	/**
	 * Links the listener to the view.
	 */
	private void linkListener() {
		mView.addListenerToSettingsAction(new SettingsActionListener(this));
	}

	/**
	 * Passes the settings of the store to the view for display.
	 */
	private void passSettingsToView() {
		for (Entry<String, String> entry : mSettingsStore.entrySet()) {
			String[] keySplit = entry.getKey().split(KEY_INFO_SEPARATOR);
			String keyIdentifier = keySplit[0];

			// Driver settings
			if (keyIdentifier.equals(KEY_IDENTIFIER_DRIVER)) {
				EBrowser browser = EBrowser.valueOf(keySplit[1]);
				JTextField field = mSettingsDialog.getBrowserDriverField(browser);
				field.setText(entry.getValue());
			} else {
				throw new IllegalStateException(
						"The given setting key is not supported by this method: " + entry.getKey());
			}
		}
	}

}
