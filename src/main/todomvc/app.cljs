(ns todomvc.app
  (:require [uix.core.alpha :as uix]
            [uix.dom.alpha :as uix.dom]))

(defn button [{:keys [on-click]} text]
  [:button.btn {:on-click on-click}
   text])

(defn app []
  [:<>
   [:header.header]
   [:section.main
    [:h1 "todos"]
    [:input.new-todo {:type "text"
                      :placeholder "What needs to be done?"}]
    [:ul.todo-list
     [:li
      [:div.view
       [:input.toggle {:type "checkbox"}]
       [:label "Create REPL"]
       [:button.destroy]]]]]
   [:footer.footer
    [:span.todo-count "1 item left"]
    [:ul.filters
     [:li [:a.selected {:cursor :pointer} "All"]]
     [:li [:a {:cursor :pointer} "Selected"]]
     [:li [:a {:cursor :pointer} "Completed"]]]]])
