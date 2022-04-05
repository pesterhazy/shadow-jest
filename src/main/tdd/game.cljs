(ns tdd.game)

(defn create []
  {:fs (-> (repeat 9 "") vec)})

(defn moves [game]
  (->> (:fs game) (remove #{""}) count))

(defn move [game n]
  (assoc-in game [:fs n] (if (zero? (mod (moves game) 2)) "X" "O")))

(defn fields [game]
  (:fs game))
