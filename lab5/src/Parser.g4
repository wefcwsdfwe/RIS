grammar Parser;

options { tokenVocab=Lexer; }

program
    : (command SEMICOLON)* EOF
    ;

command
    : playerCommand
    | gameCommand
    | commandRules
    ;

playerCommand
    : PLAYER ID ACTIONS idList
    ;

gameCommand
    : GAME ACTIONS idList
    ;

commandRules
    : DO ID (AFTER | BEFORE) ID
    | ID INCLUDES idList
    ;
    
idList
    : ID (COMMA ID)*
    ;