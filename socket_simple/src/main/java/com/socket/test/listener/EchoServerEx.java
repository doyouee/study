package com.socket.test.listener;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
			System.out.println("Client Socket의 접속 요청을 기다리고 있음");
			
			//2. 클라이언트의 접속을 항상 받아들일 수 있음 - 클라이언트의 요청이 없으면 대기 상태에 들어감 - 클라이언트가 접속하는 순간 클라이언트와 통신할 수 있는 소켓을 반환함
			child = server.accept();
			
			//3. 접속이 되면 클라이언트로부터 아이피 주소를 얻어 출력함
			System.out.println(child.getInetAddress() + "로부터 연결 요청 받음");
			System.out.println("***********************");
			
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
				System.out.println("Client로부터 받은 Data : " + receiveData);
//				if(receiveData.equals("quit") || "죵료".equals(receiveData))	break;
				
				extractData(receiveData); // 데이터 처리 메서드
				
				if("0038TEST01ABC한글SDFASDFGHDSUIFASD123123".equals(receiveData)) {
					oos.writeObject("00042000");
					oos.flush();
				} else {
					//5-2. 클라이언트로 부터 받은 데이터를 클라이언트에게 다시 전송함-> 에코:메아리
					oos.writeObject(receiveData);
					oos.flush();
				}
			}
		} catch(Exception e) { //예외가 발생하면
			e.printStackTrace(); //에러 메시지를 출력하고
			System.exit(0); //프로그램을 종료한다.
		} finally {
			try {
				is.close();
				ois.close();
				oos.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static String extractData(String receiveData) {
		int count = 0;
		Integer byteLength = Integer.parseInt(receiveData.substring(0, 4)); // Data 맨 앞의 숫자 4자리는 Data Byte 크기
		
		while(receiveData.toString().getBytes().length > byteLength) {  //receiveData의 Byte 길이가 byteLength 길이보다 긴 동안
			StringBuilder stringbuilder = new StringBuilder(byteLength);
			for(char ch : receiveData.substring(4).toString().toCharArray()) {
				count += String.valueOf(ch).getBytes().length; // 3->4->5
				if(count > byteLength) break;
				stringbuilder.append(ch);
			}
			/*
			 * 0038TEST01ABC한글SDFASDFGHDSUIFASD123123
				
				0005밤1b0001a0003잉0002dd0008안뇽11
				
				string data = 0000abcd0000efgh
				bytedata = 앞에 숫자 4자리
				실제 data = abcd
				string data = 0000efgh
				
				총 데이터 담을 data -> 변함
				bytedata -> 앞에 숫자 4자리
				시작 count 0 -> 변함
				실제 data -> 숫자4자리를 제외한 실제 Data

			 * */
//			if(receiveData.getBytes(srcBegin, srcEnd, dst, dstBegin);.substring(count).length() >=  4) { // 처리된 데이터를 제외하고 남은 값들을 담아야함.
			if(receiveData.substring(count).length() >=  4) { // 처리된 데이터를 제외하고 남은 값들을 담아야함.
				receiveData = receiveData.substring(count);
				byteLength = Integer.parseInt(receiveData.substring(0, 4));
				count = 0;
				System.out.println("전송 받은 Data : " + stringbuilder);
			} else {
				System.out.println("전송 받은 Data : " + stringbuilder);
				break;
			}
		}
		return receiveData;
	}

	public static void main(String[] args) {
		new EchoServerEx(5000);//포트 번호 5000을 오픈한다.
	}
}
