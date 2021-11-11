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
    

    public void mkdir(){

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
        }
    }
    public static void main(String[] args){
        Terminal terminal = new Terminal();
        terminal.currentPath = new File(System.getProperty("user.dir"));  
        Scanner scanner = new Scanner(System.in);
        String input = new String();
        Parser parser = new Parser();
        while (true){ //Change to out with exit
            System.out.println(terminal.currentPath);
            input = scanner.nextLine();
            parser.parse(input);
            terminal.chooseCommandAction(parser);
        }
    }
}
