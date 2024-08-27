import java.util.Scanner;
import java.util.ArrayList;

import Tasks.Deadline;
import Tasks.Event;
import Tasks.Task;
import Tasks.Todo;
import Exception.BobException;

public class Bob {
    private static ArrayList<Task> tasks;
    private static Storage storage;

    public static void main(String[] args) {
        storage = new Storage("./data/bob.csv");

        try {
            tasks = storage.load();
        } catch (BobException e) {
            System.out.println(e.getMessage());
            tasks = new ArrayList<>();
        }
        Scanner scanner = new Scanner(System.in);
        String input = "";
        String logo = " ____        ____\n"
                + "| __ )  ___ | __ )\n"
                + "|  _ \\ / _ \\|  _ \\\n"
                + "| |_) | (_) | |_) |\n"
                + "|____/ \\___/|____/\n";

        printLine();
        System.out.println("Hello! I'm Bob!\nHow can I help you today?");
        System.out.println(logo);
        printLine();;

        while (!input.equals("bye")) {
            input = scanner.nextLine().trim(); // trim to remove any whitespace before input
            String[] inputParts = input.split(" ", 2); // split input and store into array
            String command = inputParts[0].toLowerCase(); // uniform lowercase for comparison
            String taskDescription = (inputParts.length <= 1) ? "" : inputParts[1];

            try {
                switch (command) {
                    case "list":
                        listTasks();
                        break;
                    case "mark":
                        markTask(taskDescription);
                        break;
                    case "unmark":
                        unmarkTask(taskDescription);
                        break;
                    case "todo":
                        addTodoTask(taskDescription);
                        break;
                    case "deadline":
                        addDeadlineTask(taskDescription);
                        break;
                    case "event":
                        addEventTask(taskDescription);
                        break;
                    case "delete":
                        deleteTask(taskDescription);
                        break;
                    case "bye":
                        // exit
                        printLine();
                        System.out.println("Bye, see you again :)");
                        printLine();
                        scanner.close();
                        return;
                    default:
                        throw new BobException("Bob does not understand that command, sorry :(");
                }
            } catch (BobException e) {
                printLine();
                System.out.println(e.getMessage());
                printLine();
            }
        }
    }

    // handle commands

    private static void listTasks() {
        printLine();
        System.out.println("Here are the tasks in your list:");
        for (int i = 0; i < tasks.size(); i++) {
            System.out.println((i + 1) + ". " + tasks.get(i).toString());
        }
        printLine();
    }

    private static void markTask(String taskDescription) throws BobException {
        int taskIndexMark = Integer.parseInt(taskDescription) - 1;
        if (taskIndexMark < tasks.size() && taskIndexMark >= 0) {
            tasks.get(taskIndexMark).mark();
            storage.save(tasks);
            printLine();
            System.out.println("Yay! I've marked this task as done:");
            System.out.println("[" + tasks.get(taskIndexMark).getStatusIcon() + "] " + tasks.get(taskIndexMark).getDescription());
            printLine();
        } else {
            throw new BobException("Invalid index :(");
        }
    }

    private static void unmarkTask(String taskDescription) throws BobException {
        int taskIndexUnmark = Integer.parseInt(taskDescription) - 1;
        if (taskIndexUnmark < tasks.size() && taskIndexUnmark >= 0) {
            tasks.get(taskIndexUnmark).unmark();
            storage.save(tasks);
            printLine();
            System.out.println("Alright, I've marked this task as not done yet:");
            System.out.println("[" + tasks.get(taskIndexUnmark).getStatusIcon() + "] " + tasks.get(taskIndexUnmark).getDescription());
            printLine();
        } else {
            throw new BobException("Invalid index :(");
        }
    }

    private static void addTodoTask(String taskDescription) throws BobException {
        if (taskDescription.isEmpty()) {
            throw new BobException("Description of the todo cannot be empty :(");
        }
        tasks.add(new Todo(taskDescription));
        storage.save(tasks);
        printLine();
        System.out.println("Got it. I've added this task:");
        System.out.println(tasks.get(tasks.size() - 1).toString());
        System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
        printLine();
    }

    private static void addDeadlineTask(String taskDescription) throws BobException {
        if (taskDescription.isEmpty()) {
            throw new BobException("Missing deadline description :(");
        }
        String[] dlParts = taskDescription.split(" /by ");
        if (dlParts.length < 2) {
            throw new BobException("Missing details :(\nPlease use this format: 'deadline [description] /by [deadline]' :(");
        }
        tasks.add(new Deadline(dlParts[0], dlParts[1]));
        storage.save(tasks);
        printLine();
        System.out.println("Ok! I've added this task:");
        System.out.println(tasks.get(tasks.size() - 1).toString());
        System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
        printLine();
    }

    private static void addEventTask(String taskDescription) throws BobException {
        if (taskDescription.isEmpty()) {
            throw new BobException("Description of the event is missing :(");
        } else if (!taskDescription.contains(" /from ") || !taskDescription.contains(" /to ")) {
            throw new BobException("Missing details :(\nPlease use this format: event [description] /from [start] /to [end]");
        }
        String[] eventParts = taskDescription.split(" /from | /to ");
        tasks.add(new Event(eventParts[0], eventParts[1], eventParts[2]));
        storage.save(tasks);
        printLine();
        System.out.println("Ok! I've added this task:");
        System.out.println(tasks.get(tasks.size() - 1).toString());
        System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
        printLine();
    }

    private static void deleteTask(String taskDescription) throws BobException {
        int taskIndexDelete = Integer.parseInt(taskDescription) - 1;
        if (taskIndexDelete < tasks.size() && taskIndexDelete >= 0) {
            Task removedTask = tasks.remove(taskIndexDelete);
            storage.save(tasks);
            printLine();
            System.out.println("Noted. I've removed this task:");
            System.out.println("  " + removedTask.toString());
            System.out.println("Now you have " + tasks.size() + " task(s) in the list.");
            printLine();
        } else {
            throw new BobException("Invalid index :(");
        }
    }

    private static void printLine() {
        System.out.println("------------------------------------------");
    }

}


