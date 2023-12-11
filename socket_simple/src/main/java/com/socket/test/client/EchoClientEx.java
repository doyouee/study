package com.socket.test.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;

public class EchoClientEx {
	String ipAddress; //접속을 요청할 서버의 아이피 주소와
	int port; //포트 번호
	Socket client=null; //클라이언트 소켓
	BufferedReader read; //키보드로부터 메시지를 읽어올 입력 스트림
	InputStream is; //서버가 보낸 데이터를 읽기 위한 입력 스트림 저장
	ObjectInputStream ois; //서버로부터 데이터를 전송받기 위한 스트림
	OutputStream os; //서버로 메시지를 보내기 위한 출력 스트림 저장
	ObjectOutputStream oos; //서버에 데이터를 전송하기 위한 스트림
	String sendData; //서버로 보낼 데이터를 저장하기 위한 변수
	String receiveData; //서버로부터 받은 데이터를 저장하기 위한 변수
	
	//생성자는 접속할 서버의 아이피와 포트 번호를 전달 받음
	public EchoClientEx(String ip, int p) {
		ipAddress=ip;
		port=p;
		try {
			System.out.println("**** 클라이언트*****");
			System.out.println("**************************");
			
			//1.접속할 서버의 아이피 주소와 포트를 이용해서 클라이언트 소켓 생성
			client = new Socket(ipAddress, port);
			
			//클라이언트 소켓이 생성되는 순간 서버의 accept 메서드가 수행된다.
			
			//2-1. 키보드로부터 메시지를 읽어올 입력 스트림 생성
			read= new BufferedReader(new InputStreamReader(System.in));
			
			//3. 서버로 메시지를 보내기 위해서 출력 스트림 생성
			os = client.getOutputStream();
			
			//3_1. 출력 스트림을 ObjectOutputStream으로 변환한다.
			oos = new ObjectOutputStream(os);
			
		    //4. 서버가 다시 보낸 데이터를 읽이 위해서 클라이언트로부터 입력 스트림을 얻어옴
			is = client.getInputStream();
			
			//4-1. 입력 스트림을 ObjectInputStream으로 변환한다.
			ois = new ObjectInputStream(is);
			
		     //2. 서버에게 보낼 데이터를 키보드에서 입력 받기
			System.out.print("입력 >>> ");
			
			//2-2. 키보드로부터 데이터를 입력 받음
			while((sendData = read.readLine()) != null) {
				//3-2. 서버로 데이터를 전송함
				oos.writeObject(sendData);
				oos.flush();
				if(sendData.equals("quit") || "종료".equals(sendData))
					break;
				
				//4-2. 스트림을 통해 데이터를 읽어옴
				receiveData = (String)ois.readObject();
				
				//4-3. 서버로부터 메아리처럼 보낸 데이터를 다시 받아서 출력
				System.out.println(client.getInetAddress()+"로부터 받은 메시지 >>> " + receiveData);
			    System.out.print("입력 >>> ");
			}
		} catch(Exception e){
			e.printStackTrace(); //에러 메시지를 출력하고
			System.exit(0); //프로그램을 종료한다.
		} finally {
			try {
				ois.close();
				oos.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}

	public static void main(String[] args) {
		//원래는 다른 컴퓨터에서 서버를 돌려야 하지만 지금은 개념 파악을 위해서 하나의 컴퓨터에서 서버와 클라이언트를 돌리고 있음
		new EchoClientEx("localhost", 5000);//포트 번호 5000을 오픈한다.
		
		//물론 두 대의 컴퓨터에서 돌려도 된다.
		//"localhost" 대신 에코 서버가 실행되는 컴퓨터의 아이피 주소를 입력한다.
	}
}
