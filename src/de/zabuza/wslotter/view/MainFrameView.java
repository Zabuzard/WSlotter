package de.zabuza.wslotter.view;

import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.KeyboardFocusManager;
import java.awt.event.ActionListener;
import java.awt.event.WindowListener;
import java.util.LinkedList;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyleContext;

import de.zabuza.wslotter.model.EBrowser;

/**
 * View of the main frame.
 * 
 * @author Zabuza {@literal <zabuza.dev@gmail.com>}
 *
 */
public final class MainFrameView {
	/**
	 * Height of the view.
	 */
	public static final int HEIGHT = 385;
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
	private final Container mContainer;
	/**
	 * The frame of the view.
	 */
	private final JFrame mFrame;
	/**
	 * List of all input elements.
	 */
	private final List<JComponent> mInputElements;
	/**
	 * Log area of the view.
	 */
	private JTextPane mLogArea;
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
	 * Settings button of the view.
	 */
	private JButton mSettingsBtn;
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
	 * The trailer panel of the view.
	 */
	private JPanel mTrailerPanel;
	/**
	 * Username field of the view.
	 */
	private JTextField mUsernameField;

	/**
	 * Creates the view.
	 * 
	 * @param frame
	 *            Frame of the view
	 */
	public MainFrameView(final JFrame frame) {
		this.mFrame = frame;
		this.mContainer = frame.getContentPane();
		this.mInputElements = new LinkedList<>();
		initialize();
	}

	/**
	 * Adds an action listener to the settings action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToSettingsAction(final ActionListener listener) {
		this.mSettingsBtn.addActionListener(listener);
	}

	/**
	 * Adds an action listener to the start action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToStartAction(final ActionListener listener) {
		this.mStartBtn.addActionListener(listener);
	}

	/**
	 * Adds an action listener to the stop action.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addListenerToStopAction(final ActionListener listener) {
		this.mStopBtn.addActionListener(listener);
	}

	/**
	 * Adds a window listener to the view window.
	 * 
	 * @param listener
	 *            Listener to add
	 */
	public void addWindowListener(final WindowListener listener) {
		this.mFrame.addWindowListener(listener);
	}

	/**
	 * Gets the selected input browser.
	 * 
	 * @return The selected input browser
	 */
	public EBrowser getBrowser() {
		return (EBrowser) this.mBrowserChoiceBox.getSelectedItem();
	}

	/**
	 * Gets the input password.
	 * 
	 * @return The input password
	 */
	public String getPassword() {
		return this.mPasswordField.getText();
	}

	/**
	 * Gets the input text to post.
	 * 
	 * @return The input text to post
	 */
	public String getTextToPost() {
		return this.mTextToPostArea.getText();
	}

	/**
	 * Gets the input thread url.
	 * 
	 * @return The input thread url
	 */
	public String getThreadUrl() {
		return this.mThreadUrlField.getText();
	}

	/**
	 * Gets the input username.
	 * 
	 * @return The input username
	 */
	public String getUsername() {
		return this.mUsernameField.getText();
	}

	/**
	 * Appends a line to the log area.
	 * 
	 * @param line
	 *            line to append
	 */
	public void log(final String line) {
		appendToLog(line + "\n", Color.BLACK);
	}

	/**
	 * Appends a line to the log area using a red font.
	 * 
	 * @param line
	 *            line to append
	 */
	public void logError(final String line) {
		appendToLog(line + "\n", Color.RED);
	}

	/**
	 * Enables or disables all input fields.
	 * 
	 * @param enabled
	 *            Whether the fields should be enabled or disabled
	 */
	public void setAllInputEnabled(final boolean enabled) {
		for (JComponent element : this.mInputElements) {
			element.setEnabled(enabled);
		}
	}

	/**
	 * Enables or disables the settings button.
	 * 
	 * @param enabled
	 *            Whether the button should be enabled or disabled
	 */
	public void setSettingsButtonEnabled(final boolean enabled) {
		this.mSettingsBtn.setEnabled(enabled);
	}

	/**
	 * Enables or disables the start button.
	 * 
	 * @param enabled
	 *            Whether the button should be enabled or disabled
	 */
	public void setStartButtonEnabled(final boolean enabled) {
		this.mStartBtn.setEnabled(enabled);
	}

	/**
	 * Enables or disables the stop button.
	 * 
	 * @param enabled
	 *            Whether the button should be enabled or disabled
	 */
	public void setStopButtonEnabled(final boolean enabled) {
		this.mStopBtn.setEnabled(enabled);
	}

	/**
	 * Appends a message to the logging area.
	 * 
	 * @param message
	 *            Message to add
	 * @param color
	 *            Color of the message
	 */
	private void appendToLog(final String message, final Color color) {
		StyleContext sc = StyleContext.getDefaultStyleContext();
		AttributeSet aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, color);

		aset = sc.addAttribute(aset, StyleConstants.FontFamily, DEFAULT_FONT);
		aset = sc.addAttribute(aset, StyleConstants.Alignment, Integer.valueOf(StyleConstants.ALIGN_JUSTIFIED));

		int len = this.mLogArea.getDocument().getLength();
		this.mLogArea.setCaretPosition(len);
		this.mLogArea.setCharacterAttributes(aset, false);
		this.mLogArea.setEditable(true);
		this.mLogArea.replaceSelection(message);
		this.mLogArea.setEditable(false);
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
		this.mStartBtn = new JButton("Start");
		this.mStartBtn.setBounds(180, 170, 100, 23);
		this.mMainPanel.add(this.mStartBtn);

		this.mStopBtn = new JButton("Stop");
		this.mStopBtn.setBounds(300, 170, 100, 23);
		this.mMainPanel.add(this.mStopBtn);

		this.mSettingsBtn = new LinkButton("Settings");
		this.mSettingsBtn.setBounds(350, 0, 90, 23);
		this.mTrailerPanel.add(this.mSettingsBtn);
	}

	/**
	 * Initialize the text fields.
	 */
	private void initializeInputFields() {
		this.mThreadUrlField = new JTextField();
		this.mThreadUrlField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mThreadUrlField.setBounds(0, 20, this.mMainPanel.getWidth(), 20);
		this.mMainPanel.add(this.mThreadUrlField);
		this.mInputElements.add(this.mThreadUrlField);
		this.mThreadUrlField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mUsernameField = new JTextField();
		this.mUsernameField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mUsernameField.setBounds((this.mMainPanel.getWidth() / 2) + 90, 70, 123, 20);
		this.mMainPanel.add(this.mUsernameField);
		this.mInputElements.add(this.mUsernameField);
		this.mUsernameField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mPasswordField = new JPasswordField();
		this.mPasswordField.setHorizontalAlignment(SwingConstants.LEFT);
		this.mPasswordField.setBounds((this.mMainPanel.getWidth() / 2) + 90, 100, 123, 20);
		this.mMainPanel.add(this.mPasswordField);
		this.mInputElements.add(this.mPasswordField);
		this.mPasswordField.setColumns(DEFAULT_FIELD_COLUMNS);

		this.mBrowserChoiceBox = new JComboBox<>();
		for (EBrowser browser : EBrowser.values()) {
			this.mBrowserChoiceBox.addItem(browser);
			if (browser == EBrowser.FIREFOX) {
				this.mBrowserChoiceBox.setSelectedItem(browser);
			}
		}
		this.mBrowserChoiceBox.setBounds((this.mMainPanel.getWidth() / 2) + 90, 130, 123, 20);
		this.mMainPanel.add(this.mBrowserChoiceBox);
		this.mInputElements.add(this.mBrowserChoiceBox);
	}

	/**
	 * Initialize the labels.
	 */
	private void initializeLabels() {
		JLabel mThreadUrlLbl = new JLabel("Thread URL:");
		mThreadUrlLbl.setHorizontalAlignment(SwingConstants.LEFT);
		mThreadUrlLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mThreadUrlLbl.setBounds(0, 0, 75, 14);
		this.mMainPanel.add(mThreadUrlLbl);

		JLabel mTextToPostLbl = new JLabel("Text to post:");
		mTextToPostLbl.setHorizontalAlignment(SwingConstants.LEFT);
		mTextToPostLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mTextToPostLbl.setBounds(0, 50, 90, 14);
		this.mMainPanel.add(mTextToPostLbl);

		JLabel mUsernameLbl = new JLabel("Username:");
		mUsernameLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mUsernameLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mUsernameLbl.setBounds((this.mMainPanel.getWidth() / 2) + 20, 70, 65, 14);
		this.mMainPanel.add(mUsernameLbl);

		JLabel mPasswordLbl = new JLabel("Password:");
		mPasswordLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mPasswordLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mPasswordLbl.setBounds((this.mMainPanel.getWidth() / 2) + 20, 100, 65, 14);
		this.mMainPanel.add(mPasswordLbl);

		JLabel mBrowserChoiceLbl = new JLabel("Browser:");
		mBrowserChoiceLbl.setHorizontalAlignment(SwingConstants.RIGHT);
		mBrowserChoiceLbl.setFont(new Font(DEFAULT_FONT, Font.BOLD, DEFAULT_FONT_SIZE + 1));
		mBrowserChoiceLbl.setBounds((this.mMainPanel.getWidth() / 2) + 20, 130, 65, 14);
		this.mMainPanel.add(mBrowserChoiceLbl);
	}

	/**
	 * Initialize the panels.
	 */
	private void initializePanels() {
		this.mMainPanel = new JPanel();
		this.mMainPanel.setBounds(10, 10, WIDTH - 25, 200);
		this.mContainer.add(this.mMainPanel);
		this.mMainPanel.setLayout(null);

		this.mTextToPostPane = new JScrollPane();
		this.mTextToPostPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.mTextToPostPane.setBounds(0, 70, (this.mMainPanel.getWidth() / 2) - 20, 80);
		this.mMainPanel.add(this.mTextToPostPane);

		this.mLogPane = new JScrollPane();
		this.mLogPane.setViewportBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
		this.mLogPane.setBounds(10, 230, WIDTH - 25, 100);
		this.mContainer.add(this.mLogPane);

		this.mTrailerPanel = new JPanel();
		this.mTrailerPanel.setBounds(10, 330, WIDTH - 25, 50);
		this.mContainer.add(this.mTrailerPanel);
		this.mTrailerPanel.setLayout(null);
	}

	/**
	 * Initialize the logging area.
	 */
	private void initializeTextAreas() {
		this.mTextToPostArea = new JTextArea();
		this.mTextToPostArea.setLineWrap(true);
		this.mTextToPostArea.setFocusTraversalKeys(KeyboardFocusManager.FORWARD_TRAVERSAL_KEYS, null);
		this.mTextToPostArea.setFocusTraversalKeys(KeyboardFocusManager.BACKWARD_TRAVERSAL_KEYS, null);
		this.mTextToPostPane.setViewportView(this.mTextToPostArea);
		this.mInputElements.add(this.mTextToPostArea);

		this.mLogArea = new JTextPane();
		this.mLogArea.setEditable(false);
		this.mLogPane.setViewportView(this.mLogArea);
	}
}
