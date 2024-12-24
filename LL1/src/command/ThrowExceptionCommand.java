package command;

import error.SyntaxError;

public class ThrowExceptionCommand implements ICommand {
    @Override
    public void execute() {
        throw new SyntaxError("Compilation error was occurred");
    }
}
