//Youseeef: cd, mkdir, cp, cp-r
//Amin: rm, cat, echo, ls
//Solhf: pwd, ls-r, rmdir, touch
import java.util.*;
import java.io.File;
public class Terminal{
    File currentPath;

    public String pwd(){
        return "Hello";
    }
    
    public Boolean cd(String path){
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
    public void chooseCommandAction(Parser parser){ // added the parser to be abel to access args
        if (parser.getCommandName().equals("cd")){
            Boolean state = cd(parser.args[0]);
            if (state == false){
                System.out.println("Invalid Directory");
            }
        }
    }
    public static void main(String[] args){
        Terminal terminal = new Terminal();
        terminal.currentPath = new File(System.getProperty("user.dir"));  
        Scanner scanner = new Scanner(System.in);
        String input = new String();
        Parser parser = new Parser();
        while (true){
            System.out.println(terminal.currentPath);
            input = scanner.nextLine();
            parser.parse(input);
            terminal.chooseCommandAction(parser);
        }
    }
}
