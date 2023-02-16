%%
%% This is a cheatsheet about clingo/gringo/clasp language
%%  it is not complete nor states all restrictions
%%  but it gives a good and quick overview/understanding
%%
%% See 'Potassco: User Guide' (source of some test and examples) for a complete guide.
%%  https://github.com/potassco/guide/releases/tag/v2.2.0
%%



%%---------------------------------------------------------------
%% PART: basic semantic
%%---------------------------------------------------------------

%% --- ATOM
%% is a constant or function (see 'language basics' part below)

%% --- RULE: 'HEAD :- BODY.'
%% within the head are atoms
%% the body contains LITERALs which are atoms or negated atoms
%%
%% umbrella if rain and outdoor
umbrella :- rain, outdoor.

%% --- FACT (bodyless rule): 'HEAD.'
%%
%% it rains
rain.

%% --- INTEGRITY CONSTRAINT
%% filters solution candidates: body shall not be satisfied
%%
%% keep candidate if: no umbrella but outdoor
:- umbrella, not outdoor



%%---------------------------------------------------------------
%% PART: language basics
%%---------------------------------------------------------------

%% line comment start with an %
%% block and inline comments starts with %* and ends with *%

%% --- INTEGERS
1

%% --- CONSTANTS
starts_lowercase
_underscorePreventNameClashes

%% --- SPECIAL CONSTANTS
%% greatest and smallest element among all variable-free terms
#sup
#inf

%% --- BOOLEAN CONSTANTS
#true
#false

%% --- VARIABLE
Starts_uppercase
_UnderscorePreventNameClashes

%% --- ANONYMOUS VARIABLE
_

%% --- STRING
"string"
"\\" %% escapes backslash
"\n" %% escapes newline
"\"" %% escapes double quote

%% --- (UNINTERPRETED) FUNCTIONS
%% can be better understood as a named tuple
%%  since there is no self-defined functionality programmed behind these
%%
%% example with three arguments (elements in a tuple)
functionName(constant,function(123),Variable)

%% --- TUPLES
%% are functions without names
()
(functionName,constant,function(123),Variable)
%% end with a ',' (comma) to declare tuple grade: example is a quadruple
(a,b,c,d,)

%% --- INTERVALS
%%
%% in body: expanded disjunctively
num(1..3).
%% result: num(1). num(2). num(3).
%%
%% in head: expanded conjunctively
grid(1..S,1..S) :- size(S).
%% result: grid(1,1). grid(1,2). grid(2,2). grid(2,1).
%% having: size(2).
%%
%%  which is same as (using unification):
grid(X,Y) :- X = 1..S, Y = 1..S, size(S).
%%  were additional constraints could be used
%%  (remove diagonals: X-Y!=0 and X+Y-1!=S)



%%---------------------------------------------------------------
%% PART: evaluations and controls
%%---------------------------------------------------------------


%% --- DISJUNCTION
%% head is derived if at least one atom (of body) is true
%%  (increases computational complexity: use 'choice construct' were possible)
a;b :- c,d.
%% provides answer sets [a] and [b] if c or d is true
a;-b. %% see below for explanation of '-'
%% provides answer sets [a] and [-b] but not [a,-b]


%% --- NEGATION
%%
%% default negation:
%%  'not b' is true until b is derived true
a :- not b.
%%
%% classical/strong negation:
%%  '-b' is only true if b can be derived (to false)
%%  '-b' is complement of 'b' (implicit integrity constraint ':- b, -b')
a :- -b.
%%
%% double negation:
a :- not not b.
a :- not -b.
%%
%% head negation:
%%  can be used in disjunctions
not a :- b.
not not a :- b.
%%  same as integrity constraints
:- b, not not a.
:- b, not a.


%% --- BUILT-IN ARITHMETIC FUNCTIONS
plus    ( L +  R ) :- left(L), right(R). %%  +   := addition
minus   ( L -  R ) :- left(L), right(R). %%  -   := subtraction
uminus  (   -  R ) :-          right(R). %%  -   := unary minus
times   ( L *  R ) :- left(L), right(R). %%  *   := multiplication
divide  ( L /  R ) :- left(L), right(R). %%  /   := integer division
modulo  ( L \  R ) :- left(L), right(R). %%  \   := modulo
power   ( L ** R ) :- left(L), right(R). %%  **  := exponentiation
absolute(|    -R|) :-          right(R). %%  |.| := absolute value
bitand  ( L &  R ) :- left(L), right(R). %%  &   := bitwise AND
bitor   ( L ?  R ) :- left(L), right(R). %%  ?   := bitwise OR
bitxor  ( L ^  R ) :- left(L), right(R). %%  ^   := bitwise exclusive OR
bitneg  (   ~  R ) :-          right(R). %%  ~   := bitwise complement
%% having: left(7). right(2).


%% --- BUILT-IN COMPARISON PREDICATES
%%
%% integers are compared naturally, which are smaller then constants
%% constants are ordered lexicographically, which are smaller then functions
%% functions both structurally and lexicographically
%%
eq (X,Y) :- X =  Y   , num(X), num(Y). %%  =   := equal
neq(X,Y) :- X != Y   , num(X), num(Y). %%  !=  := not equal
lt (X,Y) :- X <  Y   , num(X), num(Y). %%  <   := less than
leq(X,Y) :- X <= Y   , num(X), num(Y). %%  <=  := less than or equal
gt (X,Y) :- X >  Y   , num(X), num(Y). %%  >   := greater than
geq(X,Y) :- X >= Y   , num(X), num(Y). %%  >=  := greater than or equal
%% having: num(1). num(2).
%% also possible with constants and functions: num(a). num( f(a) ).
%%
%% arithmetic functions are evaluated before comparison literals, see:
all(X,Y) :- X-1 < X+Y, num(X), num(Y).
non(X,Y) :- X/X > Y*Y, num(X), num(Y).


%% --- UNIFICATION or SHORTHANDS for terms.
%% Also possible for functions and tuples.
squares(XX,YY) :- XX  = X*X,   Y*Y = YY,
                  Y'-1 = Y,    Y'*Y' = XX+YY,
                  X<Y, num(X), num(Y).
%% having: num(1). num(2). num(3). num(4). num(5).


%% --- POOLING
%% a set of atoms, function or tuples as an argument
%% intervall '1..3' is same as pool '(1;2;3)'
%%
%% their behavior in head and body is equal to intervals:
grid((1;2),(1;2)).
%% result: grid(1,1). grid(1,2). grid(2,2). grid(2,1).
%%
%% below is same but uses unification which enables us to add constraints
grid(X,Y) :- X = (1;2), Y = (1;2).


%% --- CONDITIONAL LITERAL
%% b is the literal and c is the condition, which may be more than one
%% a yields whenever either c is false (b does not matter) or b and c holds
a :- b : c.
%%
%% usage in head is possible:
%%  if c then a only if b
a : b :- c
%%  if c then yield a(X) if b(X)
a(X) : b(X) :- c
%%
%% usages in body with other literals: conditions ends with an ';' (semicolon)
%% a :- b1, b2 : c1, c2, c3; b3, b4
next(X,Z) :- set(X), #false : X < Y, Y < Z, set(Y); set(Z), X < Z.
%% having: set(1..4).
%%
%% important:
%%  variable names within conditions must no match global variable names
%%  a global variable is within a atom which is NOT subject of a condition



%%---------------------------------------------------------------
%% PART: aggregates
%%---------------------------------------------------------------


%% --- BODY AGGREGATES
%% lowerBound <= aggregate-function { terms } <= upperBound
%% '<=' is default and can be omitted or replaced by other comparison predicates
%% 'aggregate-function' default is '#count' and can be omitted or replaced
%%
%% '{ terms }' represent a set of terms, hence duplicate terms are ignored
%%
%% aggregate functions are:
#count %% number of elements; used for expressing cardinality constraints
#sum   %% sum of weights; used for expressing weight constraints
#sum+  %% sum of positive weights
#min   %% minimum weight
#max   %% maximum weight
%%
%% weight refers the first element of a term tuple:
%%
%% example: the sum must be below 20
#sum{ 1 ; 2 ; 4 ; 8 } < 20
%%
%% example with conditional literal:
%%  the sum of credits (weight) by passed modules (mod) must be at least 20
20 #sum{ 4 : mod(a) ; 4 : mod(b) ; 6 : mod(c) ; 6 : mod(d) }
%%  the 'sum' will be '10', due to duplicate terms and hence does not satisfy the constraint
%%  hence non-single tuples can be used make weights (credits) unique (per mod)
20 #sum{ 4,a : mod(a) ; 4,b : mod(b) ; 6,c : mod(c) ; 6,d : mod(d) }
%%
%% usage of unification/shorthands is possible but
%%  discouraged due to potential excessive unwrap of possibilities
%%  (see 'Potassco: user guide' chapter 3.1.12 remark 3.9 for details)
cnt(X) :- X = #count { 2:a ; 3:a }. %% if a hold 'cnt(1)' else 'cnt(0)'
sum(X) :- X = #sum   { 2:a ; 3:a }. %% if a hold 'sum(5)' else 'sum(0)'
pos(X) :- X = #sum+  { 2:a ; 3:a }. %% if a hold 'pos(5)' else 'pos(0)'
min(X) :- X = #min   { 2:a ; 3:a }. %% if a hold 'min(2)' else 'min(#sup)'
max(X) :- X = #max   { 2:a ; 3:a }. %% if a hold 'max(3)' else 'max(#inf)'


%%
%% --- HEAD AGGREGATES
%%
%% TODO coming soon


%%
%% --- NON-GROUNDED AGGREGATES
%%
%% TODO coming soon


%%---------------------------------------------------------------
%% PART: optimization problems
%%---------------------------------------------------------------


%%
%% weak constraints
%%
:~ %% TODO coming soon


%%
%% optimization directives
%%
%% TODO coming soon
#minimize
#maximize


%%---------------------------------------------------------------
%% PART: meta
%%---------------------------------------------------------------


%%
%% #show directive
%%
%% ONLY show atom: #show p/n
#show name/2. %% having name(X, Y) show atoms Y
%% ADDITIONALLY show terms: #show t : L1, , Ln.
#show correct : finished, not error.
%% (optionally) show nothing (except other #show)
#show.


%%
%% #const directive
%%
%% TODO coming soon


%%
%% #external directive
%%
%% TODO coming soon


%%
%% #program directive
%%
%% TODO coming soon


%%
%% #script and #end directive
%%
%% TODO coming soon

