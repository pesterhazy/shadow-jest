(ns tdd.logic.calc
  (:require ["regex-escape" :as escape]))

(defn parse-int [s]
  (if (= s "")
    (throw (js/Error. (str "Invalid input: " s)))
    (let [r (js/Number s)]
      (when (js/isNaN r)
        (throw (js/Error. (str "Invalid input: " s))))
      r)))

(defn add
  ([s] (add s ","))
  ([s delim]
   (if (empty? s)
     0
     (if-let [m (re-matches #"//(.)\n(.*)" s)]
       (add (m 2) (m 1))
       (let [segments (map (fn [s]
                             (parse-int s))
                           (.split s (re-pattern (str (escape delim) "|\n"))))]
         (when (empty? segments)
           (throw (js/Error. (str "Invalid input: " s))))
         (reduce + 0 segments))))))
