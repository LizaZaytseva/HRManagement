import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerHR {
    public static void start(int nPort) {
        boolean work = true;
            try {
                //Создаем serverSocket
                ServerSocket server = new ServerSocket(nPort);
                System.out.println("Server started");
                while (work) {
                    //С помощью принимаем запросы на подключение
                    Socket socket = server.accept();
                    //Проверяем, подключился ли клиент
                    if (socket.isConnected()) System.out.println("Client accepted");
                    //Создаем BufferedReader для чтения из сокета
                    BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                    try {
                        String line = in.readLine();
                        //Создаем поток обработки клиентского запроса и запускаем
                        ProcessThread thread = new ProcessThread(socket, line);
                        thread.start();
                        //Если в сообщении от клиента, есть "stop", то закрываем сервер, устанавливаем work = false
                        if (line.contains("stop")) {
                            server.close();
                            work = false;
                            System.out.println("Server was stopped");
                        }
                    } catch (IOException i) {
                        System.out.println(i);
                    }
                }
            } catch (IOException i) {
                System.out.println(i);
            }
    }
}