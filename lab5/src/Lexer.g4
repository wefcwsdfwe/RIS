lexer grammar Lexer;

GAME: 'Game';
PLAYER: 'Player';

ACTIONS: 'actions';
DO: 'do';
AFTER: 'after';
BEFORE: 'before';
INCLUDES: 'includes';
SEMICOLON: ';';
COMMA: ',';

ID: [a-zA-Z_0-9]+;
WHITESPACE: [ \t\r\n]+ -> skip;