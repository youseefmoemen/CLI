//Youseeef: cd, mkdir, cp, cp-r
//Amin: rm, cat, echo, ls, pwd
//Solhf: ls-r, rmdir, touch
import java.util.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class Terminal{
    File currentPath;
    Boolean flag = true;
    public void cp_r(String[] folders){
        if (folders.length != 3){ // -r f1 f2
            System.out.println("Unvalid arguments only two paths allowed");
            return;
        }
        File file1 = new File(folders[1]);
        File file2 = new File(folders[2]);
        if(!file1.isDirectory()){
            System.out.println("Unvalid Source folder");
            return;
        }else if(!file2.isDirectory()){
            if(!file2.mkdirs()){
                System.out.println("Unvalid Destination path");
            }
        }
        
        File[]fiels_at_1 = new File(file1.getAbsolutePath()).listFiles();
        for(File file: fiels_at_1){
            if(file.isDirectory()){
                File new_file = new File(file2.getAbsolutePath() + "/" + file.getName());
                new_file.mkdirs();
                String[] rec = {"", file.getAbsolutePath(), new_file.getAbsolutePath()};
                this.cp_r(rec); //Recursion
                continue; //to Not complete the loop
            }
            try {
                Files.copy(file.toPath(), new File(file2.getAbsolutePath() + "/" + file.getName()).toPath(), StandardCopyOption.REPLACE_EXISTING);
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    //TODO cd error
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
            this.currentPath = new File("C:\\");
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
    
    public void ls(String args[]) {
        if(args.length == 0){
            File[] files = new File(currentPath.toString()).listFiles();

            for (int i = 0; i < files.length; i++) {
                System.out.println(files[i].getName());
            }
        } else System.out.println("Too Many Arguements");
    }

    public void pwd(String args[]) {
        if (args.length == 0) {
            System.out.println(currentPath);
        } else
            System.out.println("Too Many Arguements");
    }

    public void echo(String[] sentence){
        for(int i = 0; i  < sentence.length; i++){
            System.out.print(sentence[i]);
            System.out.print(' ');
        }
        System.out.println();
    }

    public void rm(String[] files){
       if(files.length == 1) {
           File f = new File(files[0]);
           if (!(f.isAbsolute()))
               f = new File(currentPath + "\\" + files[0]);
           if (f.delete()) {
               if (f.isDirectory())
                   System.out.println("Cannot Delete a Directory");
               else
                   f.delete();
           } else
               System.out.println("Could not delete file");
        } else System.out.println("Wrong Number of Arguements");
    }

    public void cat(String[] files) throws IOException {
        if (files.length == 1) {
            File file = new File(files[0]);
            if (!(file.isAbsolute()))
                file = new File(currentPath + "\\" + files[0]);
            FileInputStream fis = new FileInputStream(file);

            int oneByte;
            while ((oneByte = fis.read()) != -1) {
                System.out.write(oneByte);
            }
            fis.close();
            System.out.flush();
            System.out.println();
        }
        else if (files.length == 2){
            for(int i = 0; i < 2; i++){
                File file = new File(files[i]);
                if (!(file.isAbsolute()))
                    file = new File(currentPath + "\\" + files[i]);
                FileInputStream fis = new FileInputStream(file);

                int oneByte;
                while ((oneByte = fis.read()) != -1) {
                    System.out.write(oneByte);
                }
                fis.close();
                System.out.flush();
                System.out.println();
            }
        }
        else System.out.println("Wrong number of arguments");
    }

    public void chooseCommandAction(Parser parser) throws IOException{ // added the parser to be abel to access args
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
            ls(parser.getArgs());
        } else if (parser.getCommandName().equals("pwd")) {
            pwd(parser.getArgs());
        } else if (parser.getCommandName().equals("echo")) {
            echo(parser.getArgs());
        } else if (parser.getCommandName().equals("rm")) {
            rm(parser.getArgs());
        } else if (parser.getCommandName().equals("cat")) {
            cat(parser.getArgs());
        }
        else if(parser.getCommandName().equals("exit")){
            this.flag = false;
        }
    }
    public static void main(String[] args) throws IOException{
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
