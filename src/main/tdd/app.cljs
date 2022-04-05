(ns tdd.app)

(defn app-ui []
  [:button "Start"])

(defn board-ui [{:keys [fs on-move]}]
  (let [on-move (or on-move (fn []))]
    (->> (range 9)
         (map (fn [n] [:div.box {:data-testid "field"
                                 :on-click (fn [] (on-move n))}
                       (get fs n)]))
         (into [:div.board]))))
