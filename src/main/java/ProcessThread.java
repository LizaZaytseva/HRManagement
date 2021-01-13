import sun.rmi.runtime.Log;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;

public class ProcessThread extends Thread {
    private final String command;
    Socket clientSocket;
    HR_Database database = new HR_Database();
    private static BufferedWriter out;

    public ProcessThread(Socket clientSocket, String command) {
        this.clientSocket = clientSocket;
        this.command = command;
    }

    public void run() {
        String result;
        //Если не была получена команда "stop", то запускаем главный метод работы с базой данных (разделяемым файлом)
        if (!command.contains("stop")){
            result = database.mainFun(command);
        } else result = "Server was stopped";
        //Проверяем подключение к сокету
        if (clientSocket.isConnected()) {
            try {
                //Записываем результат выполнения команды, закрываем поток
                out = new BufferedWriter(new OutputStreamWriter(clientSocket.getOutputStream()));
                out.write(result);
                out.flush();
                out.close();
            } catch (IOException e) {
                System.out.println(e);

            }
        }
    }
}
