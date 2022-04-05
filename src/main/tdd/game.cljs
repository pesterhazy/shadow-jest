(ns tdd.game)

(defn create []
  {:fs (-> (repeat 9 "") vec)})

(defn turn [game]
  (->> (:fs game) (remove #{""}) count))

(defn move [game n]
  (when (not= "" (get-in game [:fs n]))
    (throw (js/Error. "Illegal move")))
  (assoc-in game [:fs n] (if (zero? (mod (turn game) 2)) "X" "O")))

(defn fields [game]
  (:fs game))
