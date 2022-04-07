(ns tdd.logic.game)

(defn create
  ([]
   (create nil))
  ([fs]
   {:fs (or fs (-> (repeat 9 "") vec))}))

(defn turn [game]
  (->> (:fs game) (remove #{""}) count))

(defn move [game n]
  (when (not= "" (get-in game [:fs n]))
    (throw (js/Error. "Illegal move")))
  (assoc-in game [:fs n] (if (zero? (mod (turn game) 2)) "X" "O")))

(defn fields [game]
  (:fs game))

(defn no [row col]
  (+ (* row 3) col))

(defn winners [{:keys [fs]}]
  (let [candidates (-> #{}
                       (into (map (fn [row]
                                    #{(no row 0) (no row 1) (no row 2)})
                                  (range 3)))
                       (into (map (fn [col]
                                    #{(no 0 col) (no 1 col) (no 2 col)})
                                  (range 3)))
                       (conj #{(no 0 0) (no 1 1) (no 2 2)})
                       (conj #{(no 2 0) (no 1 1) (no 0 2)}))]
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
