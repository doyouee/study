package com.socket.ecoserver.listener;

import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class EchoServerEx {
	ServerSocket server; //서버 소켓
	Socket child; //클라이언트와 통신하기 위한 소켓
	InputStream is; //클라이언트와 연결된 입력 스트림 저장
	ObjectInputStream ois; //클라이언트로부터 데이터를 전송받기 위한 스트림
	OutputStream os; //클라이언트와 연결된 출력 스트림 저장
	ObjectOutputStream oos;//클라이언트에게 데이터를 전송하기 위한 스트림

	String receiveData; //클라이언트로부터 전송받은 데이터를 저장하기 위한 변수
	
	public EchoServerEx(int port) { //생성자는 오픈할 포트 번호를 전달 받음
		try {
			//1. 에코 서버 프로그램은 포트를 지정해서 서버 소켓 생성부터 한다.
			server = new ServerSocket(port);
			System.out.println("**** 에코 서버*****");
			System.out.println("서버는 클라이언트 소켓의 접속 요청을 기다리고 있음");
			
			//2. 클라이언트의 접속을 항상 받아들일 수 있음
			//클라이언트의 요청이 없으면 대기 상태에 들어감
			//클라이언트가 접속하는 순간 클라이언트와 통신할 수 있는 소켓을 반환함
			child = server.accept();
			
			//3. 접속이 되면 클라이언트로부터 아이피 주소를 얻어 출력함
			System.out.println(child.getInetAddress()+"로부터 연결요청 받음");
			
			//4. 클라이언트로 부터 보내진 데이터를 읽기 위해서 클라이언트로부터 입력 스트림을 얻어옴
			is = child.getInputStream();
			
			//4-1. 입력 스트림을 ObjectInputStream으로 변환한다.
			ois = new ObjectInputStream(is);
			
			//5. 에코 서버이므로 클라이언트로부터 받은 메시지를 다시 보내기 위해서 출력 스트림 생성
			os = child.getOutputStream();
			
			//5_1. 출력 스트림을 ObjectOutputStream으로 변환한다.
			oos = new ObjectOutputStream(os);
			
			//4-2. 스트림을 통해 데이터를 읽어옴
			while((receiveData = (String)ois.readObject()) != null) {
	            if(receiveData.equals("quit"))
	            	break;
			
				//5-2. 클라이언트로 부터 받은 데이터를 클라이언트에게 다시 전송함-> 에코:메아리
				oos.writeObject(receiveData);
				oos.flush();
			}
			is.close();
			ois.close();
			oos.close();
		} catch(Exception e) { //예외가 발생하면
			e.printStackTrace(); //에러 메시지를 출력하고
			System.exit(0); //프로그램을 종료한다.
		}
	}

	public static void main(String[] args) {
		new EchoServerEx(5000);//포트 번호 5000을 오픈한다.
	}
}
