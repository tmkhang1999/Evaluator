# Project Description

A set of Clojure functions that perform symbolic simplification and evaluation of boolean expressions using and, or, and not. not will be assumed to take one argument, while and and or will take one or more. You should use false for False and true for True.

The main function to write, evalexp, entails calling functions that simplify, bind, and evaluate these expressions.

<h3> Simplification</h3>

`Length 1 Pattern Examples`
> (or true) => true
> 
> (or false) => false
> 
> (or x) => x
> (and true) => true
> (and false) => false
> (and x) => x
> (not false) => true
> (not true) => false
> (not (and x y)) => (or (not x) (not y))
> (not (or x y)) => (and (not x) (not y))
> (not (not x)) => x

`Length 2 Pattern Examples`
> (or x false) => x
> (or false x) => x
> (or true x) => true
> (or x true) => true
> (and x false) => false
> (and false x) => false
> (and x true) => x
> (and true x) => x

`Length 3 Pattern Examples`
> (or x y true) => true
> (or x false y) => (or x y)
> (and false x y) => false
> (and x true y) => (and x y)
> [... and so on ...]
