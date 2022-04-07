(ns tdd.logic.game)

(defn create
  ([]
   (create nil))
  ([fs]
   {:fs (or fs (-> (repeat 9 "") vec))}))

(defn turn [game]
  (->> (:fs game) (remove #{""}) count))

(defn advance [game n]
  (when (not= "" (get-in game [:fs n]))
    (throw (js/Error. "Illegal move")))
  (assoc-in game [:fs n] (if (zero? (mod (turn game) 2)) "X" "O")))

(defn fields [game]
  (:fs game))

(defn at [row col]
  (+ (* row 3) col))

(defn winners [{:keys [fs]}]
  (let [candidates (-> #{}
                       (into (map (fn [row]
                                    #{(at row 0) (at row 1) (at row 2)})
                                  (range 3)))
                       (into (map (fn [col]
                                    #{(at 0 col) (at 1 col) (at 2 col)})
                                  (range 3)))
                       (conj #{(at 0 0) (at 1 1) (at 2 2)})
                       (conj #{(at 2 0) (at 1 1) (at 0 2)}))]
    (some (fn [c]
            (when (and (#{"X" "O"} (get fs (first c)))
                       (apply = (map fs c)))
              c))
          candidates)))

(defn result [{:keys [fs] :as game}]
  (or
   (when-let [ws (winners game)]
     (keyword (get fs (first ws))))
   (if (= (turn game) 9)
     :draw
     :pending)))
