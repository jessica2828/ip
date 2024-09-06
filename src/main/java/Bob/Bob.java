//package Bob;
//
//import Bob.Storage.Storage;
//import Bob.TaskList.TaskList;
//import Bob.Ui.Ui;
//import Bob.Parser.Parser;
//import Bob.Command.Command;
//import Bob.Exception.BobException;
//
//import java.util.Scanner;
//
///**
// * The main class for Bob chatbot.
// * Handles the initialization and the loop of the main program.
// */
//public class Bob {
//    private Storage storage;
//    private TaskList tasks;
//    private Ui ui;
//    private Parser parser;
//
//    /**
//     * Constructs a Bob instance with a specified file path for storage.
//     * @param filePath The file path where tasks are stored.
//     */
//    public Bob(String filePath) {
//        ui = new Ui();
//        storage = new Storage(filePath);
//        parser = new Parser();
//        try {
//            tasks = new TaskList(storage.load());
//        } catch (BobException e) {
//            ui.showError(e.getMessage());
//            tasks = new TaskList();
//        }
//    }
//
//    // Constructor without argument
//    public Bob() {
//        ui = new Ui();
//        storage = new Storage("./data/bob.csv");
//        parser = new Parser();
//        try {
//            tasks = new TaskList(storage.load());
//        } catch (BobException e) {
//            ui.showError(e.getMessage());
//            tasks = new TaskList();
//        }
//    }
//
//    /**
//     * Starts Bob chatbot, handling user input and executing commands.
//     */
//    public void run() {
//        ui.showWelcome();
//        Scanner scanner = new Scanner(System.in);
//        String input;
//
//        do {
//            input = scanner.nextLine().trim();
//            try {
//                Command command = parser.parse(input);
//                command.execute(tasks.getTasks(), storage, ui);
//            } catch (BobException e) {
//                ui.showError(e.getMessage());
//            }
//        } while (!input.equals("bye"));
//
//        ui.showGoodbye();
//        scanner.close();
//    }
//
//    /**
//     * The main entry point of Bob chatbot.
//     * @param args Command-line arguments.
//     */
//    public static void main(String[] args) {
//        new Bob("./data/bob.csv").run();
//    }
//}

package bob;

import bob.Storage.Storage;
import bob.TaskList.TaskList;
import bob.Ui.Ui;
import bob.Parser.Parser;
import bob.Command.Command;
import bob.Exception.BobException;

import java.util.Scanner;

/**
 * The main class for Bob chatbot.
 * Handles the initialization and interactions of the chatbot.
 */
public class Bob {
    private Storage storage;
    private TaskList tasks;
    private Ui ui;
    private Parser parser;

    /**
     * Constructs a Bob instance with a specified file path for storage.
     * @param filePath The file path where tasks are stored.
     */
    public Bob(String filePath) {
        ui = new Ui();
        storage = new Storage(filePath);
        parser = new Parser();
        try {
            tasks = new TaskList(storage.load());
        } catch (BobException e) {
            ui.showError(e.getMessage());
            tasks = new TaskList();
        }
    }

    // Constructor without argument
    public Bob() {
        this("./data/bob.csv");
    }

    /**
     * Generates a response for the user's chat message.
     */
    public String getResponse(String input) {
        StringBuilder response = new StringBuilder();
        try {
            Command command = parser.parse(input);
            response.append(command.execute(tasks.getTasks(), storage, ui));
            return response.toString();
        } catch (BobException e) {
            return e.getMessage();
        }
    }

    /**
     * Starts Bob chatbot, handling user input and executing commands.
     * This method is primarily for console-based interaction.
     */
    public void run() {
        ui.showWelcome();
        Scanner scanner = new Scanner(System.in);
        String input;

        do {
            input = scanner.nextLine().trim();
            try {
                Command command = parser.parse(input);
                command.execute(tasks.getTasks(), storage, ui);
            } catch (BobException e) {
                ui.showError(e.getMessage());
            }
        } while (!input.equals("bye"));
        ui.showGoodbye();
        scanner.close();
    }

    /**
     * The main entry point of Bob chatbot.
     * @param args Command-line arguments.
     */
    public static void main(String[] args) {
        new Bob("./data/bob.csv").run();
    }
}
