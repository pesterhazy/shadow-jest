(ns tdd.ui.board)

(def empty-fs (-> (repeat 9 "") vec))

(defn board-ui [{:keys [fs on-move frozen]} & children]
  (assert (= 9 (count fs)))
  (let [on-move (or on-move (fn []))]
    (-> (->> (range 9)
             (map (fn [n] [:div.box {:data-testid "field"
                                     :on-click (when (and (not frozen) (= "" (get fs n)))
                                                 (fn [] (on-move n)))}
                           (get fs n)]))
             (into [:div.board {:data-testid "board"}]))
        (into children))))

(defn result-ui [result]
  (case result
    (:X :O)
    [:div.results "Player " (name result) " won"]
    :draw
    [:div.results "Draw"]
    nil))
