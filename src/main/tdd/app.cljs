(ns tdd.app)

(defn app-ui []
  [:button "Start"])

(defn board-ui [{:keys [fs]}]
  (->> (range 9)
       (map (fn [n] [:div {:data-testid "field"} (get fs n)]))
       (into [:div])))
