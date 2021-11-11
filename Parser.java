public class Parser{
    String commandName;
    String [] args;
    public Boolean parse(String input){
        String[] hold = input.split(" ");
        this.commandName = hold[0];
        
        this.args = new String[hold.length - 1];
        for(int i = 0; i < this.args.length; i++){
            args[i] = hold[i+1];
        }
        return true; //Why might cause False
    }
    public String getCommandName(){
        return this.commandName;
    }
    public String[] getArgs(){ //Modified due to args is string array not only one string
        return this.args;
    }
}
