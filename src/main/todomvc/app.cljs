(ns todomvc.app
  (:require [clojure.string :as str]
            [uix.core.alpha :as uix]))

(defn vec-remove [pos coll]
  (into (subvec coll 0 pos) (subvec coll (inc pos))))

(defn app []
  (let [!input (uix/ref nil)
        !todos (uix/state ["Start REPL"])]
    [:div
     [:header.header
      [:h1 "todos"]
      [:input.new-todo {:type "text"
                        :placeholder "What needs to be done?"
                        :auto-focus true
                        :on-key-down (fn [ev]
                                       (when (= "Enter" (.-key ev))
                                         (let [s (str/trim (.-value @!input))]
                                           (when (seq s)
                                             (swap! !todos conj s)
                                             (set! (.-value @!input) "")))))
                        :ref !input}]]
     [:section.main
      [:span
       [:input.toggle-all {:type "checkbox" :read-only true}]
       [:label]]
      (->> @!todos
           (map-indexed (fn [idx s]
                          [:li
                           [:div.view
                            [:input.toggle {:type "checkbox"}]
                            [:label {:data-testid (str "item-" idx)} s]
                            [:button.destroy {:data-testid "destroy"
                                              :on-click
                                              (fn []
                                                (swap! !todos (partial vec-remove idx)))}]]]))
           (into [:ul.todo-list]))]
     (when (seq @!todos)
       [:footer.footer {:data-testid "footer"}
        [:span.todo-count "1 item left"]
        [:ul.filters
         [:li [:a.selected {:cursor :pointer} "All"]]
         [:li [:a {:cursor "pointer"} "Active"]]
         [:li [:a {:cursor "pointer"} "Completed"]]]])]))
