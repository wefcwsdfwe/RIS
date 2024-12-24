package command;

import error.SyntaxError;
import index.IIndex;
import state.IState;
import token.IToken;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public class ReturnAcceptErrorCommand implements ICommand {
    private IToken token;
    private IState state;
    private List<Integer> stack;
    private List<String> tokenNames;
    private List<List<String>> guidingSymbols;
    private List<Integer> transitions;
    private IIndex index;
    private Map<Boolean, ICommand> errorHandler;

    public ReturnAcceptErrorCommand(
            IToken token,
            IState state,
            List<Integer> stack,
            List<String> tokenNames,
            List<List<String>> guidingSymbols,
            List<Integer> transitions,
            IIndex index,
            Map<Boolean, ICommand> errorHandler
    ) {
        this.token = token;
        this.state = state;
        this.stack = stack;
        this.tokenNames = tokenNames;
        this.guidingSymbols = guidingSymbols;
        this.transitions = transitions;
        this.index = index;
        this.errorHandler = errorHandler;
    }

    @Override
    public void execute() {
        errorHandler.get(!guidingSymbols.get(state.getStateNumber()).contains(token.getName())).execute();
        index.upIndex();
        token.setName(tokenNames.get(index.getIndex()));
        state.setStateNumber(stack.remove(stack.size() - 1));
    }

}
