(ns tdd.logic.calc)

(defn add [s]
  (if (empty? s)
    0
    (if-let [m (re-matches #"//(.)\n(.*)" s)]
      (add (get m 2))
      (let [segments (map (fn [s]
                            (let [r (js/parseInt s)]
                              (when (js/isNaN r)
                                (throw (js/Error. (str "Invalid input: " s))))
                              r))
                          (.split s #",|\n"))]
        (when (empty? segments)
          (throw (js/Error. (str "Invalid input: " s))))
        (reduce + 0 segments)))))
