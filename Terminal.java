//Youseeef: cd, mkdir, cp, cp-r
//Amin: rm, cat, echo, ls, pwd
//Solhf: ls-r, rmdir, touch
import java.util.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class Terminal{
    File currentPath;
    Boolean flag = true;
    public void cp_r(String[] folders){
        if (folders.length != 3){
            System.out.println("Unvalid arguments only two paths allowed");
            return;
        }
        
    }
    public void cp(String [] files){
        //TODO check the path for the dest is correct
        if(files.length != 2){
            System.out.println("Unvalid arguments only two files allowed");
            return;
        }
        File file1 = new File(files[0]);
        File file2 = new File(files[1]);
        Scanner reader;
        try {
            reader = new Scanner(file1);
        } catch (FileNotFoundException e) {
            System.out.println("Source file not exist");
            return;
        }
        try{
            FileWriter myWriter = new FileWriter(file2);
            while(reader.hasNextLine()){
                myWriter.write(reader.nextLine());
                myWriter.write("\n");
            }
            reader.close();
            myWriter.close();
        }catch(IOException e){
            e.printStackTrace();
        }
    }
    public void mkdir(String[] dirs){
        for(String file: dirs){
            int val = file.lastIndexOf("/");
            if(val == -1){
                File newFolder = new File(this.currentPath.getAbsolutePath() + "/" + file);
                if(newFolder.exists()){
                    System.out.println(file + " Already exist");
                }else{
                    newFolder.mkdirs();
                }
            }else{
                String path = file.substring(0, file.lastIndexOf("/"));
                File check = new File(path);
                if(!check.isDirectory()){
                    System.out.println("Invalid path: " + path);
                    continue;
                }
                File newFolder = new File(file);
                if(newFolder.exists()){
                    System.out.println(file + " Already exist");
                }else{
                    newFolder.mkdirs();
                }
            }
        }
    }
    public Boolean cd(String path){
        if (path.equals("")){
            this.currentPath = new File("/home");
            return true;
        }
        if(path.equals("..")){
            String pervious = this.currentPath.getAbsolutePath().substring(0,
            this.currentPath.getAbsolutePath().lastIndexOf('/'));
            if(pervious.equals("")){
                System.out.println("NO pervious path!");
                return true;
            }
            this.currentPath = new File(pervious);
            return true;
        }
        File hold = new File(this.currentPath.getAbsolutePath()+ "/" + path);
        if (hold.isDirectory()){
            this.currentPath = hold;
            return true;
        }
        hold = new File(path);
        if(hold.isDirectory()){
            this.currentPath = hold;
            return true;
        }
        return false;
    }
    public void ls() {
        File[] files = new File(currentPath.toString()).listFiles();

        for (int i = 0; i < files.length; i++) {
            System.out.println(files[i].getName());
        }
    }
    public void pwd(){
        System.out.println(currentPath);
    }

    public void echo(String[] sentence){
        for(int i = 0; i <  sentence.length; i++){
            System.out.print(sentence[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

    public void rm(){}
      

    public void chooseCommandAction(Parser parser){ // added the parser to be abel to access args
        if (parser.getCommandName().equals("cd") && parser.args.length != 0){
            Boolean state = cd(parser.args[0]);
            if (state == false){
                System.out.println("Invalid Directory");
            }
        } else if(parser.getCommandName().equals("cd..")){
            cd("..");
        }else if(parser.getCommandName().equals("cd")){
            cd("");
        }else if (parser.getCommandName().equals("mkdir")){
            mkdir(parser.getArgs());
        }else if(parser.getCommandName().equals("cp")){
            if (parser.getArgs().length != 0){
                if(parser.getArgs()[0].equals("-r")){
                    cp_r(parser.getArgs());
                    return;
                }
            }
            cp(parser.getArgs());
        }else if(parser.getCommandName().equals("ls")){
            ls();
        } else if (parser.getCommandName().equals("pwd")) {
            pwd();
        } else if (parser.getCommandName().equals("echo")) {
            echo(parser.getArgs());
        }
        else if(parser.getCommandName().equals("exit")){
            this.flag = false;
        }
    }
    public static void main(String[] args){
        Terminal terminal = new Terminal();
        terminal.currentPath = new File(System.getProperty("user.dir"));  
        Scanner scanner = new Scanner(System.in);
        String input = new String();
        Parser parser = new Parser();
        while (terminal.flag){ //Change to out with exit
            System.out.println(terminal.currentPath);
            input = scanner.nextLine();
            parser.parse(input);
            terminal.chooseCommandAction(parser);
        }
        scanner.close();
    }
}
