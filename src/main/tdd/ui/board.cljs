(ns tdd.ui.board)

(def empty-fs (-> (repeat 9 "") vec))

(defn board-ui [{:keys [fs on-move frozen highlight]} & children]
  (assert (= 9 (count fs)))
  (let [on-move (or on-move (fn []))
        highlight (set highlight)]
    (-> (->> (range 9)
             (map (fn [n] [:div {:data-testid "field"
                                 :class ["box" (when (highlight n)
                                                 "highlight")]
                                 :on-click (when (and (not frozen) (= "" (get fs n)))
                                             (fn [] (on-move n)))}
                           (get fs n)]))
             (into [:div.board {:data-testid "board"}]))
        (into children))))

(defn result-ui [{:keys [result on-restart]}]
  (case result
    (:X :O)
    [:button.results {:on-click on-restart} "Player " (name result) " won." [:br] "Click to restart"]
    :draw
    [:button.results {:on-click on-restart} "Draw." [:br] "Click to restart"]
    nil))
