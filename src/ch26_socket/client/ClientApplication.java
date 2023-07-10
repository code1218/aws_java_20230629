package ch26_socket.client;

import java.awt.EventQueue;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class ClientApplication extends JFrame {

	private Socket socket;
	
	private JPanel mainPanel;
	private JTextField ipTextField;
	private JTextField portTextField;
	private JTextField messageTextField;
	private JButton messageSendButton;

	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ClientApplication frame = new ClientApplication();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	public ClientApplication() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 624, 553);
		mainPanel = new JPanel();
		mainPanel.setBorder(new EmptyBorder(5, 5, 5, 5));

		setContentPane(mainPanel);
		mainPanel.setLayout(null);
		
		
		// <<< 채팅내용 >>>
		JScrollPane chatTextAreaScrollPane = new JScrollPane();
		chatTextAreaScrollPane.setBounds(12, 10, 393, 435);
		mainPanel.add(chatTextAreaScrollPane);
		
		JTextArea chatTextArea = new JTextArea();
		chatTextArea.setEditable(false);
		chatTextAreaScrollPane.setViewportView(chatTextArea);
		
		// <<< 채팅연결 >>>
		ipTextField = new JTextField();
		ipTextField.setBounds(417, 10, 179, 29);
		mainPanel.add(ipTextField);
		ipTextField.setColumns(10);
		
		portTextField = new JTextField();
		portTextField.setBounds(417, 49, 179, 29);
		mainPanel.add(portTextField);
		portTextField.setColumns(10);
		
		JButton connectionButton = new JButton("접속");
		connectionButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String serverIp = ipTextField.getText();
				String serverPort = portTextField.getText();
				
				if(serverIp.isBlank() || serverPort.isBlank()) {
					JOptionPane.showMessageDialog(
							mainPanel, 
							"IP와 PORT번호를 입력해주세요.", 
							"접속 오류",
							JOptionPane.ERROR_MESSAGE);
					return;
				}
				
				try {
					socket = new Socket(serverIp, Integer.parseInt(serverPort));
					JOptionPane.showMessageDialog(
							mainPanel,
							"서버와의 연결에 성공하였습니다.",
							"접속 완료",
							JOptionPane.PLAIN_MESSAGE);
					messageTextField.setEditable(true);
					messageSendButton.setEnabled(true);
					
				} catch (NumberFormatException e1) {
					e1.printStackTrace();
				} catch (UnknownHostException e1) {
					e1.printStackTrace();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
				
			}
		});
		connectionButton.setBounds(417, 88, 179, 29);
		mainPanel.add(connectionButton);
		
		// <<< 접속자 >>>
		JScrollPane connectedUserListScrollPane = new JScrollPane();
		connectedUserListScrollPane.setBounds(417, 127, 179, 318);
		mainPanel.add(connectedUserListScrollPane);
		
		JList connectedUserList = new JList();
		connectedUserListScrollPane.setViewportView(connectedUserList);
		
		// <<< 메세지입력 및 전송 >>>
		messageTextField = new JTextField();
		messageTextField.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				if(e.getKeyCode() == KeyEvent.VK_ENTER) {
					System.out.println("전송");
				}
			}
		});
		messageTextField.setBounds(12, 455, 507, 49);
		messageTextField.setEditable(false);
		mainPanel.add(messageTextField);
		messageTextField.setColumns(10);
		
		messageSendButton = new JButton("전송");
		messageSendButton.setBounds(531, 455, 65, 49);
		messageSendButton.setEnabled(false);
		mainPanel.add(messageSendButton);
	}
}










