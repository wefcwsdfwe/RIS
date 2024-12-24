package configuration;

import command.ICommand;
import state.IState;
import token.IToken;

import java.util.List;
import java.util.Map;

public interface IConfiguration {
    List<Integer> getTransitions();

    List<List<Integer>> getTable();

    public List<Map.Entry<String, String>> getTokensDict();

    public List<List<String>> getGuidingSymbols();

    public Map<List<Integer>, ICommand> initCommandMap(IState state, IToken token, List<String> tokenNames);
}
