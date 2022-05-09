package utilities;

public class CommandLineProcessor {
    //Singleton Class
    private static CommandLineProcessor instance;

    private CommandLineProcessor() { //making it private for singleton class
    }

    public static CommandLineProcessor getInstance() {
        if(instance == null) {
            instance = new CommandLineProcessor();
        }
        return instance;
    }

    public static String getInputFile(String[] args) {
        if(args.length<1) {
            return "null";
        }
        return args[0];
    }
}
