import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class ChatClient extends Frame {

	PrintWriter out;
	Socket s = null;
	TextField tfTxt = new TextField();
	TextArea taContent = new TextArea();

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

			@Override
			public void windowClosing(WindowEvent arg0) {
				disconnect();
				System.exit(0);
			}

		});

		tfTxt.addActionListener(new TFListener());
		this.setVisible(true);
		connect();
	}

	public void connect() {
		try {
			s = new Socket("127.0.0.1", 8888);
			out = new PrintWriter(s.getOutputStream());
			System.out.println("succeed");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	public void disconnect(){
		try {
			s.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	private class TFListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String str = tfTxt.getText();
			taContent.setText(str);
			tfTxt.setText("");
				out.println(str);
				out.flush();

		}

	}

}
