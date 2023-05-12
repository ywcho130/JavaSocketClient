import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
	public static void main(String[] args) {
		
		Socket socket = null;
		// 입력 스트림을 읽기 위한 클래스 선언
		BufferedReader in = null;
		// 다양한 데이터 유형의 값을 텍스트 형식으로 출력하는 데 사용되는 클래스 선언
		PrintWriter out = null;
		
		try {
			// 클라이언트가 "localhost"라는 호스트의 4444번 포트에 접속하고, 해당 호스트의 서버와 통신할 수 있는 소켓 객체 생성
			socket = new Socket("localhost", 4444);
			// BufferedReader는 입력 스트림을 읽기 위한 클래스로, socket.getInputStream()을 통해 소켓의 입력 스트림을 가져온다. 
			// 그리고 InputStreamReader를 통해 바이트 스트림을 문자 스트림으로 변환하고(소켓 통신에서는 바이트 코드를 전송하기 때문에),
			// 마지막으로 BufferedReader를 생성하여 효율적으로 데이터를 읽을 수 있게 한다.
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 클라이언트에서 서버로 데이터를 보내기 위한 출력 스트림 생성
			// 첫 번째 인자로는 출력 스트림을 전달하고, 두 번째 인자로는 autoFlush를 설정한다. 
			// autoFlush가 true로 설정되면, 데이터를 출력할 때마다 버퍼를 비우고 자동으로 데이터를 전송한다.
			out = new PrintWriter(socket.getOutputStream(), true);
			Scanner scanner = new Scanner(System.in);
			
			
			while(true) {
				// 입력받고 서버에 전송
				System.out.println("메시지 입력 : ");
				String reply = scanner.nextLine();
				out.println(reply);
				out.flush();
				// 대화 종료를 위한 조건 충족시 반복문에서 나와 대화를 종료
				if (reply.equals("끝냅시다.")) {
					break;
				}
				// 변수 response에 Server로부터 읽어들인 데이터를 저장한다.
				String response = in.readLine();
				System.out.println("서버 응답 : "+ response);
				
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (out != null) out.close();
				if (in != null) in.close();
				if (socket != null) socket.close();
				
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
}
