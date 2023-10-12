
import java.io.*;
import java.net.*;
public class server {
    public static void main(String[] args) throws IOException{
        ServerSocket ss=new ServerSocket(7777);
        Socket s=ss.accept();
        System.out.println("This is Server...");
        System.out.println("Connection established");
        //will read the data from the user...
        BufferedReader br=new BufferedReader(new InputStreamReader(s.getInputStream()));
        PrintWriter out=new PrintWriter(s.getOutputStream());
        // make a function to take input
        Start_Reading(br,s);
        Start_Writing(out,s);


    }

    public static void Start_Reading(BufferedReader br,Socket s) {
        //making a thread to read and write data simultaneously
        Runnable r1=()-> {
            while (!s.isClosed()) {
                try {
                    String s1 = br.readLine();
                    if (s1.equals("end")) {
                        s.close();
                        break;
                    } else {
                        System.out.println("Client:" + s1);
                    }
                }
                catch(Exception e) {
                    System.out.println("Connection closed");
                    break;
                }
            }
        };
        new Thread(r1).start();

    }
    public static void Start_Writing(PrintWriter out,Socket s) throws IOException{
        Runnable r2=()->{
            while(!s.isClosed()){
                try{
                    BufferedReader r=new BufferedReader(new InputStreamReader(System.in));
                    String msg=r.readLine();
                    if(msg.equals("end")){
                        s.close();
                        break;
                    }
                    out.println(msg);
                    out.flush();

                }
                catch(Exception e){
                    System.out.println("Connection closed");
                    break;
                }



            }
        };
        new Thread(r2).start();
    }
}
