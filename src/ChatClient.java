import java.awt.*;

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
		this.setVisible(true);
	}
}
