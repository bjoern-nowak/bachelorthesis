from pygments.style import Style
from pygments.token import *


class ClingosStyle(Style):
    """
    must be put in pygments style folder: e.g. ~/.local/lib/python3.10/site-packages/pygments/styles
    and added to __all__ of __init__.py

    Styles: (https://pygments.org/docs/styledevelopment/#creating-own-styles)
    - render text as bold: 'bold'
    - don’t render text as bold (to prevent subtokens being highlighted bold): 'nobold'
    - render text italic: 'italic'
    - don’t render text as italic: 'noitalic'
    - render text underlined: 'underline'
    - don’t render text underlined: 'nounderline'
    - transparent background: 'bg:'
    - background color (black): 'bg:#000000'
    - no border: 'border:'
    - border color (white): 'border:#ffffff'
    - text color (red): '#ff0000'
    - don’t inherit styles from supertoken: 'noinherit'
    """
    background_color = '#000000'

    styles = {
        Text: '#000000',
        Whitespace: '#000000',
        # Escape: '',
        # Error: '',
        # Other: '',

        Keyword: 'bold #1632B8',  # blue @@@ ( ) , . :- [ ] { }
        # Keyword.Constant: '',
        # Keyword.Declaration: '',
        # Keyword.Namespace: '',
        # Keyword.Pseudo: '',
        # Keyword.Reserved: '',
        Keyword.Type: 'bold #CC6620',  # orange @@@ .. : ; = @

        # Name: '',
        # Name.Attribute: '',
        # Name.Builtin: '',
        # Name.Builtin.Pseudo: '',
        # Name.Class: '',
        # Name.Constant: '',
        # Name.Decorator: '',
        # Name.Entity: '',
        # Name.Exception: '',
        # Name.Function: '',
        # Name.Function.Magic: '',
        # Name.Property: '',
        # Name.Label: '',
        # Name.Namespace: '',
        # Name.Other: '',
        # Name.Tag: '',
        Name.Variable: 'italic',
        # Name.Variable.Class: '',
        # Name.Variable.Global: '',
        # Name.Variable.Instance: '',
        # Name.Variable.Magic: '',

        # Literal: '',
        # Literal.Date: '',

        String: '#1E9B24',  # lila
        # String.Affix: '',
        # String.Backtick: '',
        # String.Char: '',
        # String.Delimiter: '',
        # String.Doc: '',
        String.Double: '#1E9B24',  # lila
        String.Escape: '#B4B400',  # yellow
        # String.Heredoc: '',
        # String.Interpol: '',
        # String.Other: '',
        # String.Regex: '',
        # String.Single: '',
        # String.Symbol: '',

        # Number: '',
        # Number.Bin: '',
        # Number.Float: '',
        # Number.Hex: '',
        Number.Integer: 'bold #CE00FF',  # purple
        # Number.Integer.Long: '',
        # Number.Oct: '',

        Operator: 'bold #F92772',  # pink @@@ ! != & ' * ** + - / < <= > >= ? \ ^ _ not | ~
        Operator.Word: 'bold #46B13E',
        # green @@@ :~ # #count #false #inf #max #maximize #min #minimize #program #show #sum #sum+ #sup #true

        # Punctuation: '',
        # Punctuation.Marker: '',

        Comment: 'italic #808080',  # gray
        # Comment.Hashbang: '',
        # Comment.Multiline: '',
        # Comment.Preproc: '',
        # Comment.PreprocFile: '',
        # Comment.Single: '',
        Comment.Special: 'bold #B4B400',  # yellow

        # Generic: '',
        # Generic.Deleted: '',
        # Generic.Emph: '',
        # Generic.Error: '',
        # Generic.Heading: '',
        # Generic.Inserted: '',
        # Generic.Output: '',
        # Generic.Prompt: '',
        # Generic.Strong: '',
        # Generic.Subheading: '',
        # Generic.Traceback: '',
    }
