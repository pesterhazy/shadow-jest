(ns tdd.board)

(def empty-fs (-> (repeat 9 "") vec))

(defn board-ui [{:keys [fs on-move]}]
  (assert (= 9 (count fs)))
  (let [on-move (or on-move (fn []))]
    (->> (range 9)
         (map (fn [n] [:div.box {:data-testid "field"
                                 :on-click (fn [] (on-move n))}
                       (get fs n)]))
         (into [:div.board {:data-testid "board"}]))))
