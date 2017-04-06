import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.net.*;

public class ChatClient extends Frame {

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
		addWindowListener(new WindowAdapter(){

			@Override
			public void windowClosing(WindowEvent arg0) {
				// TODO Auto-generated method stub
				System.exit(0);
			}
			
		});

		tfTxt.addActionListener(new TFListener());
		this.setVisible(true);
		connect();
	}
	
	public void connect(){
		try {
			Socket s=new Socket("127.0.0.1",8888);
System.out.println("succeed");
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	private class TFListener implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent arg0) {
			String s=tfTxt.getText();
			taContent.setText(s);
			tfTxt.setText("");
		}
		
	}

}
