(ns tdd.logic.game)

(defn create
  ([]
   (create (-> (repeat 9 "") vec)))
  ([fs]
   {:fs fs}))

(defn turn [game]
  (->> (:fs game) (remove #{""}) count))

(defn move [game n]
  (when (not= "" (get-in game [:fs n]))
    (throw (js/Error. "Illegal move")))
  (assoc-in game [:fs n] (if (zero? (mod (turn game) 2)) "X" "O")))

(defn fields [game]
  (:fs game))

(defn at [{:keys [fs]} row col]
  (get fs (+ (* row 3) col)))

(defn result [game]
  (or
   (some (fn [row] (when (and (#{"X" "O"} (at game row 0))
                              (= (at game row 0)
                                 (at game row 1)
                                 (at game row 2)))
                     (keyword (at game row 0))))
         (range 2))
   (some (fn [col] (when (and (#{"X" "O"} (at game 0 col ))
                              (= (at game 0 col)
                                 (at game 1 col)
                                 (at game 2 col)))
                     (keyword (at game 0 col))))
         (range 2))
   (cond
     (and (#{"X" "O"} (at game 0 0 ))
          (= (at game 0 0)
             (at game 1 1)
             (at game 2 2)))
     (keyword (at game 0 0))
     (and (#{"X" "O"} (at game 2 0 ))
          (= (at game 2 0)
             (at game 1 1)
             (at game 0 2)))
     (keyword (at game 2 0))
     (= (turn game) 9)
     :draw
     :else
     :pending)))
