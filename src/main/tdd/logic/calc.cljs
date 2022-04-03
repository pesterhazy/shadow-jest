(ns tdd.logic.calc)

(defn add [s]
  (if (empty? s)
    0
    (js/parseInt s)))
