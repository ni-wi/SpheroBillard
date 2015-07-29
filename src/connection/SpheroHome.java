package connection;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionListener;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.ListSelectionModel;

public class SpheroHome extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4605601418605002879L;

	private JPanel mainPanel, buttonPanel, listPanel;
	private JButton searchBtn, connectBtn, disconnectBtn, manualControlBtn, gameStartBtn;
	private JList<String> deviceList;
	DefaultListModel<String> listContent;

	public SpheroHome() throws HeadlessException {
		this.setSize(400, 400);

		mainPanel = new JPanel();

		searchBtn = new JButton("Search");
		connectBtn = new JButton("Conncect");
		disconnectBtn = new JButton("Disconnect");
		gameStartBtn = new JButton("Game Start");
		manualControlBtn = new JButton("Manual Control");

		buttonPanel = new JPanel();
		GridLayout gridLayout = new GridLayout(4, 1);
		buttonPanel.setLayout(gridLayout);
		buttonPanel.add(searchBtn);
		buttonPanel.add(connectBtn);
		buttonPanel.add(gameStartBtn);
		buttonPanel.add(manualControlBtn);
		buttonPanel.add(disconnectBtn);

		listPanel = new JPanel();
		listContent = new DefaultListModel<>();
		listContent.addElement(" ");
		deviceList = new JList<>(listContent);
		deviceList.setFixedCellWidth(200);
		deviceList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		listPanel.add(deviceList);

		mainPanel.add(buttonPanel);
		mainPanel.add(listPanel);

		this.add(mainPanel);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public void addSearchListener(ActionListener listener) {
		this.searchBtn.addActionListener(listener);
	}

	public void addConnectListener(ActionListener listener) {
		this.connectBtn.addActionListener(listener);
	}

	public void addDisconnectListener(ActionListener listener) {
		this.disconnectBtn.addActionListener(listener);
	}

	public void setListContent(DefaultListModel<String> content) {
		this.deviceList.setModel(content);
	}

	public int getSelectedDevice() {
		return this.deviceList.getSelectedIndex();
	}

	public void addManualControlListener(ActionListener listener) {
		this.manualControlBtn.addActionListener(listener);
	}
	
	public void addGameStartListener(ActionListener listener){
		this.gameStartBtn.addActionListener(listener);
	}

}
