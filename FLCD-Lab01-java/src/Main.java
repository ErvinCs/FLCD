import Auxiliary.LexicalError;
import Auxiliary.SyntaxError;

import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        String inputPath = "res/input/input01.txt";
        String outputPath = "res/output/output01.txt";

        try {
            CodeScanner scanner = new CodeScanner(inputPath, outputPath);
            scanner.codify();

            System.out.println(scanner.getPif().toString());
            System.out.println(scanner.getSt().toString());

        } catch (LexicalError | IOException | SyntaxError ex) {
            System.out.println(ex.getMessage());
        }


    }
}
