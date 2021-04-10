# Project Description

A set of Clojure functions that perform symbolic simplification and evaluation of boolean expressions using and, or, and not. not will be assumed to take one argument, while and and or will take one or more. You should use false for False and true for True.

The main function to write, evalexp, entails calling functions that simplify, bind, and evaluate these expressions.

<h3> Simplification</h3>

`Length 1 Pattern Examples`
> (or true) => true<br /> (or false) => false<br /> (or x) => x<br /> (and true) => true<br /> (and false) => false<br /> (and x) => x<br /> (not false) => true<br /> (not true) => false<br /> (not (and x y)) => (or (not x) (not y))<br /> (not (or x y)) => (and (not x) (not y))<br /> (not (not x)) => x<br />

`Length 2 Pattern Examples`
> (or x false) => x<br /> (or false x) => x<br /> (or true x) => true<br /> (or x true) => true<br /> (and x false) => false<br /> (and false x) => false<br /> (and x true) => x<br /> (and true x) => x<br />

`Length 3 Pattern Examples`
> (or x y true) => true<br /> (or x false y) => (or x y)<br /> (and false x y) => false<br /> (and x true y) => (and x y)<br /> [... and so on ...]
