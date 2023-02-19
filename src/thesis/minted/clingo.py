from pygments.lexer import RegexLexer
from pygments.token import *

__all__ = ['ClingoLexer']


class ClingoLexer(RegexLexer):
    """
    must be put in pygments lexer folder: e.g. ~/.local/lib/python3.10/site-packages/pygments/lexer
    and added to LEXERS of _mapping.py

    https://pygments.org/docs/lexerdevelopment/
    """
    name = 'Clingo'
    aliases = ['clingo']
    filenames = ['*.lp']

    tokens = {
        'root': [
            (r"\s+", Whitespace),
            # (r"\w+", Text),
            (r'"', String.Double, "simple-string"),  # start string
            (r'\d+', Number.Integer),
            (r'[\[\](){}.,]', Keyword),
            (r':-', Keyword),
            (r'[:;=]', Keyword.Type),
            (r'\.\.', Keyword.Type),
            (r'(!=?|&|\*|\+|-|\/|\\|\||<=?|>=?|\?|\^|not|~|\')', Operator),
            (r'(:~|#(count|false|inf|max(imize)?|min(imize)?|program|show|sum\+?|sup|true))', Operator.Word),
            (r'_?[A-Z]\w*', Name.Variable),
            # (r'(_?[a-z]\w*)(?!\()', Name.Constant),
            (r'(_?[a-z]\w*)(?=\()', Name.Function),
            (r'%.*', Comment.Single),
            (r'%\*', Comment.Multiline, 'nested-comment'),
        ],
        'nested-comment': [
            (r'%\*', Comment.Multiline, '#push'),
            (r'\*%', Comment.Multiline, '#pop'),
            (r'[^*%]+', Comment.Multiline),
            (r'[*%]', Comment.Multiline),
        ],
        'simple-string': [
            (r'\\\\', String.Escape),  # Escaped \
            (r'\\n', String.Escape),  # Escaped \n
            (r'\\"', String.Escape),  # Escaped "
            (r'[^"]+', String),  # String Content
            (r'"', String.Double, '#pop'),  # Closing Quote
        ],
    }
