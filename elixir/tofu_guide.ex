defmodule Tofu.Guide do
  @moduledoc """
  Overview of the Java compiler toolkit in this repository translated to Elixir-style documentation.

  The repository is organized as a collection of Maven modules:

    * `commons`     – basic classes shared by all modules
    * `tepal`       – lexical analyzer (scanner)
    * `tepas`       – syntax analyzer (parser)
    * `tepc-xpp`    – example compiler for the "xpp" language
    * `teprl`       – automata and grammar utilities

  ## commons
  Defines reusable symbols and token/lexeme utilities.
  `Alphabet` offers predefined sets of characters such as digits, letters and operators.
  `Token`, `Terminal`, `NonTerminal` and `Symbol` model language symbols and tokens.

  ## teprl
  Provides automata and grammar support:
    * `machines/af` – Finite-state automata (AF) and deterministic transducer (DT) implementations.
    * `machines/grammar` – Grammar representation (`GLC`, `Production`, etc.) with FIRST/FOLLOW routines.

  ## tepal
  Implements a lexical analyzer named `AL` which runs a group of DT machines over a buffered stream,
  emitting tokens and storing them in a symbol table (`TS`).

  ## tepas
  Contains the syntax analyzer. `AS` drives parsing by reading tokens from a `Lexer` and feeding them
  to a `Recognizer` built from the grammar's analysis table (`TAS`).

  ## tepc-xpp
  Demonstrates how the components fit together. `ALGenerator` creates DT machines for identifiers,
  integers, operators and keywords. `Compiler` combines these machines with the grammar defined in
  `resources/xpp_grammar.xml` to tokenize and parse input files.

  ### Next steps
  Explore `teprl`'s DT and AF classes to understand state machines. Study `GLC` to see how FIRST and
  FOLLOW sets are computed. The `SDT` and `Procedure` classes in `tepas` are stubs for semantic actions
  and offer room for extension.
  """

  def summary do
    IO.puts(@moduledoc)
  end
end

Tofu.Guide.summary()
