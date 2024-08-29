package Bob.Command;

import Bob.Storage.Storage;
import Bob.Ui.Ui;
import java.util.ArrayList;
import Bob.Tasks.Task;

public class ListCommand extends Command {

    @Override
    public void execute(ArrayList<Task> tasks, Storage storage, Ui ui) {
        ui.showTaskList(tasks);
    }
}
