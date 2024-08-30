package Bob.Command;

import Bob.Exception.BobException;
import Bob.Storage.Storage;
import Bob.Tasks.Task;
import Bob.Ui.Ui;

import java.util.ArrayList;

public abstract class Command {
    public abstract void execute(ArrayList<Task> tasks, Storage storage, Ui ui) throws BobException;

}

