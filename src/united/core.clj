(ns united.core
  (:gen-class))

"look up the value 'i' in map m and returns the result if it exists. Otherwise returns i."
(defn lookup
  [i m]
  (get m i i))

"replace some of the variables in expressions with constants (true or false) according to m.
 Otherwise keeps the original value"
(defn bind-values
  [l m]
  (map (fn [i]
         (if (seq? i)
           (bind-values i m)
           (lookup i m)))
       l))

"change a list by NOT"
(defn convert
  [l]
  (if (seq? l)
    (let [connective (first l)
          the_rest (map convert (rest l))]
      (cond
        (= connective 'and)
        (cons 'or (map #(if (and (seq? %) (not (= (first %) 'not))) % (list 'not %)) the_rest))
        (= connective 'or)
        (cons 'and (map #(if (and (seq? %) (not (= (first %) 'not))) % (list 'not %)) the_rest))
        :else (cons connective the_rest)
        )
      )
    l)
  )

"Evaluate the expression"
(defn simplify
  [l]
  (if (seq? l)
    (let [connective (first l)
          ;; remove duplicates
          the_rest (distinct (map simplify (rest l)))]
      (cond
        ;; (or) if one of the elements in the_rest is true -> true
        (and (= connective 'or) (some true? the_rest)) true
        ;; (and) if one of the elements in the_rest is false -> false
        (and (= connective 'and) (some false? the_rest)) false
        ;; (or/and x/true/false) one element
        (and (not (= connective 'not)) (= (count the_rest) 1)) (first the_rest)
        ;; (or false/and true...x) 2 elements
        (and (= (count the_rest) 2) (or (some true? the_rest) (some false? the_rest)))
        (first  (remove false? (remove true? the_rest)))
        ;; not cases
        (and (= connective 'not) (= (count the_rest) 1) (some true? the_rest)) false
        (and (= connective 'not) (= (count the_rest) 1) (some false? the_rest)) true
        ;; nested not
        (and (= (count the_rest) 1) (seq? (first the_rest)) (= (count (rest (first the_rest))) 1))
        (second (first the_rest))

        :else (cons connective (remove false? (remove true? the_rest)))
        )
      )
    l))

"evaluate an expression by binding symbols with true or false, then simplifying"
(defn evalexp
  [exp bindings]
  (if (seq? exp)
    (let [connective (first exp)
          the_rest (second exp)]
      (cond
        ;;(not(and/or...
        (and (= connective 'not) (seq? the_rest) (not (= (first the_rest) 'not)))
        (simplify (convert (bind-values the_rest bindings)))
        ;;(and/or...,(not true/false/x, (not(not(not...
        :else (simplify (bind-values exp bindings))
        )
      )
    exp)
  )

"build unevaluated lists and, or and not"
(defn andexp [e1 e2] (list 'and e1 e2))
(defn orexp  [e1 e2] (list 'or e1 e2))
(defn notexp [e1] (list 'not e1))

"Three sample expressions"
(def p1 '(and x (or x (and y (not z)))))
(def p2 '(and (and z false) (or x true)))
(def p3 '(or true a))

"Gmail testing expressions"
(def gp1 '(and x (or x (and y (not z))))) "{x false, z true}"
(def gp2 '(and x (or x (and y (not z))))) "{z false"
(def gp3 '(not (and x y (not z)))) "{y true}"
(def gp4 '(not (and x y (not z)))) "{x true}"








