import java.io.*;
import java.net.*;

public class ChatServer {
	boolean started = false;
	ServerSocket ss = null;

	public static void main(String args[]) {
		new ChatServer().start();
	}

	public void start() {
		try {
			ss = new ServerSocket(8888);
			started = true;//�˿����ӳɹ���
		} catch (BindException e) {
			System.out.println("�˿�ʹ����");
			System.out.println("��ر���س���");
			System.exit(0);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			//�˿����ӳɹ�����ͻ��˵�����
			while (started) {
				Socket s = ss.accept();
				//�ͻ������Ӻ�newһ���̴߳���
				Client c = new Client(s);
				System.out.println("a client connected");
				new Thread(c).start();
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				ss.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	class Client implements Runnable {

		private DataInputStream dis = null;
		private Socket s = null;
		private boolean bConnect = false;

		public Client(Socket s) {
			this.s = s;
			try {
				dis = new DataInputStream(s.getInputStream());
				bConnect = true;
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				while (bConnect) {
					String str = dis.readUTF();
					System.out.println(str);
				}
			} catch (EOFException e) {
				System.out.println("Client closed");
			} catch (IOException e) {
				e.printStackTrace();

			} finally {
				try {
					if (dis != null)
						dis.close();
					if (s != null)
						s.close();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}
		}
	}

}
