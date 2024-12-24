package command;

import index.IIndex;
import state.IState;
import token.IToken;

import java.util.List;

public class ReturnAcceptCommand implements ICommand {
    private IToken token;
    private IState state;
    private List<Integer> stack;
    private List<String> tokenNames;
    private List<List<String>> guidingSymbols;
    private List<Integer> transitions;
    private IIndex index;

    public ReturnAcceptCommand(
            IToken token,
            IState state,
            List<Integer> stack,
            List<String> tokenNames,
            List<List<String>> guidingSymbols,
            List<Integer> transitions,
            IIndex index
    ) {
        this.token = token;
        this.state = state;
        this.stack = stack;
        this.tokenNames = tokenNames;
        this.guidingSymbols = guidingSymbols;
        this.transitions = transitions;
        this.index = index;
    }

    @Override
    public void execute() {
        index.upIndex();
        token.setName(tokenNames.get(index.getIndex()));
        state.setStateNumber(stack.remove(stack.size() - 1));
    }
}
