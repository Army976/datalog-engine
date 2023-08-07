package c6591;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

import c6591.ASTClasses.Program;
//import c6591.SQLGenerator;
import parser.DatalogParser;
import parser.ParseException;

public class App {
    public static void main(String[] args) {
        
        //Determine the input file
        String inputDirectory = System.getProperty("user.dir") + "/input/";
        String fileName = "test.dl"; //default test file.
        ArrayList<ArrayList<String>> facts = new ArrayList<>();
        
        // If the user specifies a file, use that instead.
        if(args.length > 0) {
            fileName = args[0];
        }
        String filePath = inputDirectory + fileName;

        
        System.out.println("Running Token_Raquib.parse:");
        facts = Token_Raquib.parse(filePath);

        System.out.println("Running H2test:");
        H2test.test();

        System.out.println("Running InitDatabase.init:");
        InitDatabase.init(facts, new ArrayList<ArrayList<ArrayList<String>>>());

        System.out.println("Running InitDatabase.printFacts:");
        InitDatabase.printFacts();


        System.out.println("Running the javaCC parser:");
        Program program = new Program();
        try {
            // Create a FileInputStream for the file to be parsed
            FileInputStream fis = new FileInputStream(filePath);
            
            // Create a new instance of the parser
            DatalogParser parser = new DatalogParser(fis);

            // Parse the file
            program = parser.Program();
            System.out.println("Program parsed successfully.");

        } catch (FileNotFoundException | ParseException e) {
            e.printStackTrace();
        }

        System.out.println("Running SQLGenerator:");
        SQLGenerator sqlGenerator = new SQLGenerator(program);

        sqlGenerator.printAll();
    }
}
