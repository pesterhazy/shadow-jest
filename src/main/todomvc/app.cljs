(ns todomvc.app
  (:require [uix.core.alpha :as uix]
            [uix.dom.alpha :as uix.dom]))

(defn app []
  (let [!input (uix/ref nil)
        !todos (uix/state ["Create REPL"])]
    [:<>
     [:header.header]
     [:section.main
      [:h1 "todos"]
      [:form {:on-submit (fn [ev]
                           (let [s (.-value @!input)]
                             (swap! !todos conj s)
                             (.preventDefault ev)))}
       [:input.new-todo {:type "text"
                         :placeholder "What needs to be done?"
                         :ref !input}]
       [:input {:type "submit" :style {:visibility "hidden"}}]]
      (->> @!todos
           (map (fn [s]
                  [:li
                   [:div.view
                    [:input.toggle {:type "checkbox"}]
                    [:label s]
                    [:button.destroy]]]))
           (into [:ul.todo-list]))]
     [:footer.footer
      [:span.todo-count "1 item left"]
      [:ul.filters
       [:li [:a.selected {:cursor :pointer} "All"]]
       [:li [:a {:cursor "pointer"} "Selected"]]
       [:li [:a {:cursor "pointer"} "Completed"]]]]]))
