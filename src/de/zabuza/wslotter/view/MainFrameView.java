package de.zabuza.wslotter.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;

import de.zabuza.wslotter.model.EBrowser;

/**
 * View of the main frame.
 * 
 * @author Zabuza
 *
 */
public final class MainFrameView {
	/**
	 * Height of the view.
	 */
	public static final int HEIGHT = 370;
	/**
	 * Width of the view.
	 */
	public static final int WIDTH = 450;
	/**
	 * The default amount of columns for fields of the view.
	 */
	private static final int DEFAULT_FIELD_COLUMNS = 10;
	/**
	 * The default font of the view.
	 */
	private static final String DEFAULT_FONT = "Tahoma";
	/**
	 * The default font size of the view.
	 */
	private static final int DEFAULT_FONT_SIZE = 11;
	/**
	 * The browser choice of the view.
	 */
	private JComboBox<EBrowser> mBrowserChoiceBox;
	/**
	 * Container of the view.
	 */
	private Container mContainer;
	/**
	 * List of all input elements.
	 */
	private final List<JComponent> mInputElements;
	/**
	 * Log area of the view.
	 */
	private JTextArea mLogArea;
	/**
	 * Log pane of the view.
	 */
	private JScrollPane mLogPane;
	/**
	 * The main panel of the view.
	 */
	private JPanel mMainPanel;
	/**
	 * Password field of the view.
	 */
	private JTextField mPasswordField;
	/**
	 * Start button of the view.
	 */
	private JButton mStartBtn;
	/**
	 * Stop button of the view.
	 */
	private JButton mStopBtn;
	/**
	 * Text to post area of the view.
	 */
	private JTextArea mTextToPostArea;
	/**
	 * Text to post pane of the view.
	 */
	private JScrollPane mTextToPostPane;
	/**
	 * Thread URL field of the view.
	 */
	private JTextField mThreadUrlField;
	/**
	 * Username field of the view.
	 */
	private JTextField mUsernameField;

	/**
	 * Create the view.
	 * 
	 * @param thatContainer
	 *            container of the view
	 */
	public MainFrameView(final Container thatContainer) {
		mContainer = thatContainer;
		mInputElements = new LinkedList<>();
		initialize();
	}

	/**
	 * Adds an action listener to the start action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToStartAction(final ActionListener listener) {
		mStartBtn.addActionListener(listener);
	}

	/**
	 * Adds an action listener to the stop action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToStopAction(final ActionListener listener) {
		mStopBtn.addActionListener(listener);
	}

	/**
	 * Appends a line to the log area.
	 * 
	 * @param line
	 *            line to append
	 */
	public void log(final String line) {
		mLogArea.setForeground(Color.BLACK);
		mLogArea.append(line + "\n");
	}

	/**
	 * Appends a line to the log area using a red font.
	 * 
	 * @param line
	 *            line to append
	 */
	public void logError(final String line) {
		mLogArea.setForeground(Color.RED);
		mLogArea.append(line + "\n");
	}

	public void setAllInputEnabled(final boolean enabled) {
		for (JComponent element : mInputElements) {
			element.setEnabled(enabled);
		}
	}

	public void setStartButtonEnabled(final boolean enabled) {
		mStartBtn.setEnabled(enabled);
	}

	public void setStopButtonEnabled(final boolean enabled) {
		mStopBtn.setEnabled(enabled);
	}

	/**
	 * Initialize the contents of the view.
	 */
	private void initialize() {
		initializePanels();
		initializeLabels();
		initializeButtons();
		initializeInputFields();
		initializeTextAreas();

		setStopButtonEnabled(false);
	}

	/**
	 * Initialize the buttons.
	 */
	private void initializeButtons() {
		mStartBtn = new JButton("Start");
		mStartBtn.setBounds(180, 170, 109, 23);
		mMainPanel.add(mStartBtn);

		mStopBtn = new JButton("Stop");
		mStopBtn.setBounds(300, 170, 109, 23);
		mMainPanel.add(mStopBtn);
	}

	/**
	 * Initialize the text fields.
	 */
	private void initializeInputFields() {
		mThreadUrlField = new JTextField();
		mThreadUrlField.setHorizontalAlignment(SwingConstants.LEFT);
		mThreadUrlField.setBounds(0, 20, mMainPanel.getWidth(), 20);
		mMainPanel.add(mThreadUrlField);
		mInputElements.add(mThreadUrlField);
		mThreadUrlField.setColumns(DEFAULT_FIELD_COLUMNS);

		mUsernameField = new JTextField();
		mUsernameField.setHorizontalAlignment(SwingConstants.LEFT);
		mUsernameField.setBounds((mMainPanel.getWidth() / 2) + 90, 70, 123, 20);
		mMainPanel.add(mUsernameField);
		mInputElements.add(mUsernameField);
		mUsernameField.setColumns(DEFAULT_FIELD_COLUMNS);

		mPasswordField = new JPasswordField();
		mPasswordField.setHorizontalAlignment(SwingConstants.LEFT);
		mPasswordField.setBounds((mMainPanel.getWidth() / 2) + 90, 100, 123, 20);
		mMainPanel.add(mPasswordField);
		mInputElements.add(mPasswordField);
		mPasswordField.setColumns(DEFAULT_FIELD_COLUMNS);

		mBrowserChoiceBox = new JComboBox<>();
		for (EBrowser browser : EBrowser.values()) {
			mBrowserChoiceBox.addItem(browser);
			if (browser == EBrowser.FIREFOX) {
				mBrowserChoiceBox.setSelectedItem(browser);
			}
		}
		mBrowserChoiceBox.setBounds((mMainPanel.getWidth() / 2) + 90, 130, 123, 20);
		mMainPanel.add(mBrowserChoiceBox);
		mInputElements.add(mBrowserChoiceBox);
	}

	/**
	 * Initialize the labels.
	 */
	private void initializeLabels() {
		JLabel mThreadUrlLbl = new JLabel("Thread URL:");
		mThreadUrlLbl.setHorizontalAlignment(SwingConstants.LEFT);
		mThreadUrlLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mThreadUrlLbl.setBounds(0, 0, 75, 14);
		mMainPanel.add(mThreadUrlLbl);

		JLabel mTextToPostLbl = new JLabel("Text to post:");
		mTextToPostLbl.setHorizontalAlignment(SwingConstants.LEFT);
		mTextToPostLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mTextToPostLbl.setBounds(0, 50, 90, 14);
		mMainPanel.add(mTextToPostLbl);

		JLabel mUsernameLbl = new JLabel("Username:");
		mUsernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mUsernameLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mUsernameLbl.setBounds((mMainPanel.getWidth() / 2) + 20, 70, 65, 14);
		mMainPanel.add(mUsernameLbl);

		JLabel mPasswordLbl = new JLabel("Password:");
		mPasswordLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mPasswordLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mPasswordLbl.setBounds((mMainPanel.getWidth() / 2) + 20, 100, 65, 14);
		mMainPanel.add(mPasswordLbl);

		JLabel mBrowserChoiceLbl = new JLabel("Browser:");
		mBrowserChoiceLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mBrowserChoiceLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mBrowserChoiceLbl.setBounds((mMainPanel.getWidth() / 2) + 20, 130, 65, 14);
		mMainPanel.add(mBrowserChoiceLbl);
	}

	/**
	 * Initialize the panels.
	 */
	private void initializePanels() {
		mMainPanel = new JPanel();
		mMainPanel.setBounds(10, 10, WIDTH - 25, 200);
		mContainer.add(mMainPanel);
		mMainPanel.setLayout(null);

		mTextToPostPane = new JScrollPane();
		mTextToPostPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mTextToPostPane.setBounds(0, 70, (mMainPanel.getWidth() / 2) - 20, 80);
		mMainPanel.add(mTextToPostPane);

		mLogPane = new JScrollPane();
		mLogPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		mLogPane.setBounds(10, 230, WIDTH - 25, 100);
		mContainer.add(mLogPane);
	}

	/**
	 * Initialize the logging area.
	 */
	private void initializeTextAreas() {
		mTextToPostArea = new JTextArea();
		mTextToPostArea.setLineWrap(true);
		mTextToPostArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		mTextToPostArea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
		mTextToPostPane.setViewportView(mTextToPostArea);
		mInputElements.add(mTextToPostArea);

		mLogArea = new JTextArea();
		mLogArea.setEditable(false);
		mLogArea.setLineWrap(true);
		mLogPane.setViewportView(mLogArea);
		mInputElements.add(mLogArea);
	}
}
