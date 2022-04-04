(ns tdd.app)

(defn app-ui []
  [:button "Start"])

(defn board-ui []
  (->> (range 9)
       (map (fn [_n] [:div {:data-testid "field"}]))
       (into [:div])))
