import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {

	DataOutputStream dos = null;
	DataInputStream dis = null;
	Socket s = null;
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();

	// Thread tRecv=new Thread(new RecvThread());

	private boolean bConnected = false;

	public static void main(String args[]) {
		new ChatClient().launchFrame();
	}

	public void launchFrame() {
		setLocation(400, 300);
		this.setSize(300, 300);
		add(taContent, BorderLayout.NORTH);
		add(tfTxt, BorderLayout.SOUTH);
		pack();
		addWindowListener(new WindowAdapter() {

			public void windowClosing(WindowEvent arg0) {
				disconnect();
				System.exit(0);
			}

		});

		tfTxt.addActionListener(new TFListener());
		this.setVisible(true);
		connect();

		new Thread(new RecvThread()).start();
	}

	public void connect() {
		try {
			s = new Socket("127.0.0.1", 8888);
			dos = new DataOutputStream(s.getOutputStream());
			dis = new DataInputStream(s.getInputStream());
			System.out.println("succeed");
			bConnected = true;
		} catch (UnknownHostException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void disconnect() {
		try {
			bConnected = false;
			dis.close();
			dos.close();
			s.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				s.close();
				dos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String str = tfTxt.getText();
			// taContent.setText(str);
			tfTxt.setText("");
			try {
				dos.writeUTF(str);
				dos.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	private class RecvThread implements Runnable {

		public void run() {

			try {
				while (bConnected) {
					String str = dis.readUTF();
					// System.out.println(str);
					taContent.setText(taContent.getText() + str + '\n');
				}
			} catch (SocketException e) {
				System.out.println("ÏµÍ³¹Ø±Õ");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

	}

}
