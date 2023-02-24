from pygments.style import Style
from pygments.token import *


class YamlsStyle(Style):
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
    # background_color = '#000000'

    styles = {
        # Text: '#000000',
        # Whitespace: '#000000',
        # Escape: '',
        # Error: '',
        # Other: '',

        # Keyword: '',
        # Keyword.Constant: '',
        # Keyword.Declaration: '',
        # Keyword.Namespace: '',
        # Keyword.Pseudo: '',
        # Keyword.Reserved: '',
        Keyword.Type: '#00FF00',

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
        Name.Label: '#A6AC00',
        Name.Namespace: '#00FF00',
        # Name.Other: '',
        Name.Tag: '#F26264',  # red
        # Name.Variable: '',
        # Name.Variable.Class: '',
        # Name.Variable.Global: '',
        # Name.Variable.Instance: '',
        # Name.Variable.Magic: '',

        # Literal: '',
        # Literal.Date: '',

        String: '#3AB161', # green
        # String.Affix: '',
        # String.Backtick: '',
        # String.Char: '',
        # String.Delimiter: '',
        # String.Doc: '',
        # String.Double: '',
        String.Escape: '#B4B400',  # yellow
        # String.Heredoc: '',
        # String.Interpol: '',
        # String.Other: '',
        # String.Regex: '',
        # String.Single: '',
        # String.Symbol: '',

        Number: '#C48C4E', # orange
        # Number.Bin: '',
        # Number.Float: '',
        # Number.Hex: '',
        # Number.Integer: '',
        # Number.Integer.Long: '',
        # Number.Oct: '',

        # Operator: '',
        # Operator.Word: '',

        Punctuation: 'bold #A900AC',
        Punctuation.Indicator: 'bold #A900AC',
        # Punctuation.Marker: '',

        Comment: 'italic #808080',  # gray
        # Comment.Hashbang: '',
        # Comment.Multiline: '',
        # Comment.Preproc: '',
        # Comment.PreprocFile: '',
        # Comment.Single: '',
        # Comment.Special: '',

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
