//package CSVtoJava;
//
//import java.sql.*;
//import java.util.ArrayList;
//
//
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.DataInputStream;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStream;
//import java.io.InputStreamReader;
//import java.nio.charset.Charset;
//
//
//public class Builddata {
//    public static void connect() throws ClassNotFoundException, SQLException
//    {
//        Class.forName("com.mysql.jdbc.Driver");
//        String jdbc="jdbc:mysql://127.0.0.1:3306/test?characterEncoding=utf8";
//        Connection conn=DriverManager.getConnection(jdbc, "root", "123456");//链接到数据库
//
//        Statement state=conn.createStatement();
//        String sql="insert into tb1(NAME,PALCE ) values('2','汉企')";
//        state.executeUpdate(sql);
//        conn.close();//关闭通道
//    }
//    public static void readCSV()
//    {
//        try {
//            File kkFile = new File("D:/桌面/nlp-tools/DB/MYSQL/guilin_areas.csv");
//            InputStream in = new FileInputStream(kkFile);
//            CsvReader cr = new CsvReader(in, ',', Charset.forName("GBK"));
//            cr.readHeaders();
//            String[] headers = cr.getHeaders();
//            System.out.println(headers.length);
//            if(headers.length != 3){
//                System.out.println("错误");
//            }
//            while(cr.readRecord()){
//                System.out.print(cr.get(0));
//                System.out.print(cr.get(1));
//                System.out.print(cr.get(2));
//                System.out.print(cr.get(3));
//                System.out.println(cr.get(4));
//            }
//            in.close();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    public static ArrayList Read_csv() throws ClassNotFoundException, SQLException
//    {
//        try {
//            // 用来保存数据
//            ArrayList<String[]> csvFileList = new ArrayList<String[]>();
//            // 定义一个CSV路径
//            String csvFilePath = "D:\\桌面\\nlp-tools\\DB\\MYSQL\\guilin_areas.csv";
//            // 创建CSV读对象 例如:CsvReader(文件路径，分隔符，编码格式);
//            CsvReader reader = new CsvReader(csvFilePath, ',', Charset.forName("GBK"));
//
//            // 跳过表头 如果需要表头的话，这句可以忽略
//            reader.readHeaders();
//            // 逐行读入除表头的数据
//            while (reader.readRecord()) {
//                System.out.println(reader.getRawRecord());
//                csvFileList.add(reader.getValues());
//            }
//            reader.close();
//            //在此连接数据库
//            Class.forName("com.mysql.jdbc.Driver");
//            String jdbc="jdbc:mysql://127.0.0.1:3306/data1?characterEncoding=utf8";
//            Connection conn=DriverManager.getConnection(jdbc, "root", "123456");//链接到数据库
//            Statement state=conn.createStatement();   //容器
//            String sql;
//
//            // 遍历读取的CSV文件
//            for (int row = 0; row < csvFileList.size(); row++) {
//                // 取得第row行第0列的数据
//                String name = csvFileList.get(row)[0];
//                String locationx=csvFileList.get(row)[1];
//                String locationy=csvFileList.get(row)[2];
//
//                String address =csvFileList.get(row)[3];
//                String province =csvFileList.get(row)[4];
//                String city =csvFileList.get(row)[5];
//                String area=csvFileList.get(row)[6];
//                String street_id =csvFileList.get(row)[7];
//                String telephone=csvFileList.get(row)[8];
//                String uid=csvFileList.get(row)[9];
//                Float x1=Float.parseFloat(locationx);
//                Float x2= Float.parseFloat(locationy);
//                // sql=
//                sql="insert into visit (name, province,address,x,y,city,area,telephone)  values  ('"
//                        +name+"','"+province+"','"+ address+"',"+x1+","+x2+",'"+city+"','"+area+"','"+telephone+  "');";
//                System.out.println(sql);
//                state.executeUpdate(sql);
//
//            }
//            conn.close();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public static void main(String[] args) throws ClassNotFoundException, SQLException
//    {
//        Read_csv();
//    }
//
//}