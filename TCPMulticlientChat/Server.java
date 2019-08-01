import java.net.*;
import java.io.*;

class ServerThread implements Runnable{
  private static ServerSocket server = null;
  private static Socket socket = null;
  ServerThread(ServerSocket server){
    this.server=server;
  }
  public void run(){
    try{
      //System.out.println("Client is connected!");
      socket = server.accept();
      System.out.println("Client accepted!");
    }catch(UnknownHostException u){
      System.out.println(u);
    }catch(IOException e){
      System.out.println(e);
    }

    Thread mr = new Thread(new MessageRead(socket));
    Thread mw = new Thread(new MessageWrite(socket));
    mr.start();
    mw.start();
    
  }

  public static Socket sock(Socket socket){
    return socket;
  }
}
class MessageWrite implements Runnable {
    private static Socket socket = null;
    private BufferedReader input = null;
    private static DataOutputStream sendMsg = null;

    MessageWrite(Socket socket) {
        this.socket = socket;
        try {
            input = new BufferedReader(new InputStreamReader(System.in));
            sendMsg = new DataOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        String message = "";
        try {
            while(!message.equals("over")){
                message=input.readLine();
                sendMsg.writeUTF(message);
                if(message.equals("over")){
                  System.exit(0);
                }
          
                }
            input.close();
            sendMsg.close();
            socket.close();
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}

class MessageRead implements Runnable {
    private static Socket socket = null;
    private BufferedReader input = null;
    private static DataInputStream rcvMsg = null;

    MessageRead(Socket socket) {
        this.socket = socket;
        try {
            //input = new BufferedReader(new InputStreamReader(System.in));
            rcvMsg = new DataInputStream(new BufferedInputStream(socket.getInputStream()));
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    public void run(){
      String message="";
      try{
      
       while(!message.equals("over")){
            message = rcvMsg.readUTF();
          System.out.println("Client: "+ message);
          if(message.equals("over")){
            System.exit(0);
          }
          }
        //input.close();
        rcvMsg.close();
        socket.close();
      }catch(IOException e){
        System.out.println(e);
      }
    }
  }
  
class Server{
  // private static ServerSocket server=null;
  // private static Socket socket = null;

  // Server(int port){
  //     try{
  //       server = new ServerSocket(port);
  //       System.out.println("Connected to the client!");
  //       socket = server.accept();
  //       System.out.println("Client accepted");
        
  //     }catch(UnknownHostException u){
  //       System.out.println(u);
  //     }catch(IOException e){
  //       System.out.println(e);
  //     }
      
    //   try{
    //     String message = "";
    //       while(!message.equals("over")){
    //         message = rcvMsg.readUTF();
    //         System.out.println(message);
    //         message = input.readLine();
    //         sendMsg.writeUTF(message); 
    //       }
    //         input.close();
    //         rcvMsg.close();
    //         sendMsg.close();
    //         socket.close();
    //         server.close();
    //       }catch(IOException e){
    //         System.out.println(e);
    //       }
    
  //}
  public static void main(String[] args)throws Exception{
      //Server server = new Server(8000);
      //ServerThread st = new ServerThread(8000);
      ServerSocket server = new ServerSocket(8000);
      System.out.println("Server is connected!");
      while(true){
        try{
          Thread st1 = new Thread(new ServerThread(server));
          st1.start();
        }catch(Exception e){
          System.out.println(e);
        }
      }
      
      // Thread st2 = new Thread(new ServerThread(8000));
      // st2.start();
      // Thread st3 = new Thread(new ServerThread(8000));
      // st3.start();
      // Thread mr = new Thread(new MessageRead(socket));
      // Thread mw = new Thread(new MessageWrite(socket));
      //   mr.start();
      //   mw.start();
  }
}