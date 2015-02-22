package Socket;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import Agent.BaseAgent;
import Controller.Initializer;
import Controller.MonitorForestStatusController;
import Environment.ForestCell;

public class SocketServer {
	private static ArrayList<ForestCell> forest_list;
	private static ArrayList<BaseAgent> device_id;
	private static double opt_score;
	static MonitorForestStatusController controller = new MonitorForestStatusController();
	int count = 0;

	public static void main(String[] args) {
		ServerSocket serverSocket = null;
		forest_list = new ArrayList<ForestCell>();
		device_id = new ArrayList<BaseAgent>();
		
		initialize();

		try {
			// 서버소켓을 생성하고 5000번 포트와 결합(bind) 한다.
			serverSocket = new ServerSocket(5000);
			System.out.println(getTime() + " 서버가 준비되었습니다.");
		} catch (IOException e) {
			e.printStackTrace();
		} // try - catch

		while (true) {
			try {
				//System.out.println(getTime() + " 연결요청을 기다립니다.");
				// 서버소켓은 클라이언트의 연결요청이 올 때까지 실행을 멈추고 계속 기다린다.
				// 클라이언트의 연결요청이 오면 클아이언트 소켓과 통신한 새로운 소켓을 생성한다.
				Socket socket = serverSocket.accept();
				//System.out.println(getTime() + socket.getInetAddress()
				//		+ " 로부터 연결요청이 들어왔습니다.");

				// 소켓의 입력스트림을 얻는다.
				InputStream in = socket.getInputStream();
				ObjectInputStream ois = new ObjectInputStream(in);

				DataRequest dr = (DataRequest) ois.readObject();
				forest_list = (ArrayList<ForestCell>) dr.getList();
								
				change(forest_list);

				device_id = controller.getOptimal_Genetic();
				
				opt_score = controller.getOptCost();
				System.out.println("opt_score : " + opt_score);
				
				for(int i=0; i<device_id.size(); i++){
					System.out.print(device_id.get(i).getAgentID());
					System.out.print(" ");
				}
				System.out.println();

				DataResponse r = new DataResponse();
				r.setOpt_score(opt_score);
				r.setList(device_id);

				// 소켓의 출력스트림을 얻는다.
				OutputStream out = socket.getOutputStream();
				ObjectOutputStream oos = new ObjectOutputStream(out);

				// 원격 소켓(remote Socket)에 데이터를 보낸다.
				oos.writeObject(r);
				//System.out.println(r);
				oos.flush();
				//System.out.println(getTime() + " 데이터를 전송했습니다.");

				ois.close();
				in.close();
				oos.close();
				out.close();

				socket.close();
			} catch (IOException | ClassNotFoundException e) {
				e.printStackTrace();
			} // try - catch
		} // while
	} // main

	private static void initialize() {
		Initializer.initialize();
	}
	
	private static void change(ArrayList<ForestCell> cellList) {
		Initializer.change(cellList);
	}

	static String getTime() {
		SimpleDateFormat f = new SimpleDateFormat("[hh:mm:ss]");
		return f.format(new Date());
	} // getTime
} // TcpServerTest
