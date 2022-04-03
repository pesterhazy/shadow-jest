(ns tdd.logic.calc
  (:require
   [clojure.string :as string]))

(defn add [s]
  (if (empty? s)
    0
    (->> (string/split s ",") (map #(js/parseInt %)) (reduce + 0))))
