package Bob.Command;

import Bob.Exception.BobException;
import Bob.Storage.Storage;
import Bob.Tasks.Task;
import Bob.Tasks.Todo;
import Bob.Ui.Ui;

import java.util.ArrayList;

public class TodoCommand extends Command {

    private final String taskDescription;

    public TodoCommand(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage, Ui ui) throws BobException {
        if (taskDescription.isEmpty()) {
            throw new BobException("Description of the todo cannot be empty :(");
        }
        Task todo = new Todo(taskDescription);
        tasks.add(todo);
        storage.save(tasks);
        ui.showAddedTask(todo, tasks.size());
    }
}
