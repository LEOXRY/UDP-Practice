package _UDP_01;

import java.util.*;
import java.io.*;
import java.net.*;

public class Server {
	public static void main(String args[]) {
		Message2 mess = new Message2();
		mess.receiveMessage();
	}
}
class Message2{
	public final int sendPort = 20001;
	public final int receivePort = 20000;
	public List<String> list = new LinkedList<String>();
	//群发消息
	public void sendMessage(String data) {
		try {
			DatagramSocket socket = new DatagramSocket();
			for(String st: list) {
				DatagramPacket packet = new DatagramPacket(data.getBytes(), data.length(), InetAddress.getByName(st), this.sendPort);
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
				String host = packet.getAddress().getHostAddress();
				if(list.size() > 0) {
					for(String st: list) {
						if(!st.equals(host)) {
							list.add(host);
						}
					}
				}
				else {
					list.add(host);
				}
				sendMessage(new String(byt, 0, packet.getLength()));
			}
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
}