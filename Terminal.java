import Parser.java;


public class Terminal{
    public String pwd(){
        return "Hello";
    }
    public static void main(String[] args){
        Parser parse = new Parser();
        System.out.println(parse.getArgs());
    }
}
