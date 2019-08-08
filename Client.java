package _UDP_01;

import java.util.*;
import java.io.*;
import java.net.*;

public class Client {
	public static void main(String args[]) {
		Message mess = new Message();
		SendMessage sendMess = new SendMessage(mess);
		ReceiveMessage receiveMess = new ReceiveMessage(mess);
		Thread sendThread = new Thread(sendMess);
		Thread receiveThread = new Thread(receiveMess);
		sendThread.start();
		receiveThread.start();
	}
}
class Message{
	private String serverHost = "192.168.137.1";
	private int sendPort = 20000;
	private int receivePort = 20001;
	//发送消息
	public void sendMessage() {
		try {
			DatagramSocket socket = new DatagramSocket();
			BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
			String data = null;
			while((data = bufferedReader.readLine()) != null) {
				DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName(this.serverHost), this.sendPort);
				socket.send(packet);
			}
			socket.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	//接收消息
	public void receiveMessage() {
		try {
			byte[] byt = new byte[1024];
			DatagramSocket socket = new DatagramSocket(this.receivePort);
			DatagramPacket packet = new DatagramPacket(byt, byt.length);
			while(true) {
				socket.receive(packet);
				System.out.println("收到" + new String(byt, 0, packet.getLength()));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}
class SendMessage implements Runnable{
	Message mess;
	public SendMessage(Message mess) {
		this.mess = mess;
	}
	public void run() {
		mess.sendMessage();
	}
}
class ReceiveMessage implements Runnable{
	Message mess;
	public ReceiveMessage(Message mess) {
		this.mess = mess;
	}
	public void run() {
		mess.receiveMessage();
	}
}