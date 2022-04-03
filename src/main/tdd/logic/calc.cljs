(ns tdd.logic.calc
  (:require
   [clojure.string :as string]))

(defn add [s]
  (if (empty? s)
    0
    (let [xs (map (fn [s]
                    (let [r (js/parseInt s)]
                      (when (js/isNaN r)
                        (throw (js/Error. (str "Invalid input: " s))))
                      r))
                  (string/split s ","))]
      (when (empty? xs)
        (throw (js/Error. (str "Invalid input: " s))))
      (reduce + 0 xs))))
