import java.io.*;
import java.util.ArrayList;
import java.util.regex.Pattern;

public class HR_Database {
    private static String database = "database";
   private static ArrayList<String> data = new ArrayList<>();

    //Скачивание данных из БД
    private static void download(){
        try {
            File file = new File(database);
            //создаем объект FileReader для объекта File
            FileReader fr = new FileReader(file);
            //создаем BufferedReader с существующего FileReader для построчного считывания
            BufferedReader reader = new BufferedReader(fr);
            // считаем сначала первую строку
            String line = reader.readLine();
            data.clear();
            if (line != null) data.add(line);
            while (line != null) {
                // считываем остальные строки в цикле
                line = reader.readLine();
                if (line != null) data.add(line);
            }
        } catch (IOException e) {
            System.out.println(e);
        }
    }
    //Добавить человека в базу
    private synchronized String add(String name){
        if (!data.contains(name)) {
            data.add(name);
            update();
            return "Added";
        } else return "Added earlier";
    }
    //Удалить человека из базы
    private synchronized String remove(String name){
        if (data.contains(name)) {
            data.remove(name);
            update();
            return "Removed";
        } else return "Removed earlier";
    }

    //Узнать текущее количество человек в базе
    private static String check(){
    return String.valueOf(data.size());
    }

    //Обновление данных в базе
    private synchronized void update(){
        try(FileWriter writer = new FileWriter(database)) {
            if (data.size() != 0) {
            for (int i = 0; i < data.size(); i++){
                writer.write(data.get(i));
                writer.write("\n");
            }
            writer.flush();
            writer.close();
        }
        }
        catch(IOException e){
            System.out.println(e);
        }
    }

    public synchronized String mainFun(String command){
        download();
        String[] str = command.split(" ");
        if (str[0].equals("add")) return add(str[1]);
        if (str[0].equals("remove")) return remove(str[1]);
        if (str[0].equals("check")) {
            return check();
        } else return "unknown command";
    }
}
