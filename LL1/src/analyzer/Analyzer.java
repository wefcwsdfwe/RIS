package analyzer;

import command.ICommand;
import configuration.Configuration;
import configuration.IConfiguration;
import lexer.ILexer;
import lexer.Lexer;
import state.IState;
import state.State;
import token.IToken;
import token.Token;

import java.util.List;
import java.util.Map;

public class Analyzer implements IAnalyzer {

    private ILexer lexer;
    private IToken token;
    private IState state;
    private List<List<Integer>> table;
    private Map<List<Integer>, ICommand> commandMap;

    public Analyzer(final String input) {
        IConfiguration configuration = new Configuration();
        this.lexer = new Lexer(input, configuration.getTokensDict());
        this.table = configuration.getTable();
        this.token = new Token(lexer.getTokenNames().get(0));
        this.state = new State();
        this.commandMap = configuration.initCommandMap(state, token, lexer.getTokenNames());
    }

    public void analyze() {
        while (!token.getName().equals("END")) {
            commandMap.get(table.get(state.getStateNumber())).execute();
        }
        System.out.println("Success compiled");
    }
}
