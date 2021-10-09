(ns todomvc.app
  (:require [uix.core.alpha :as uix]
            [uix.dom.alpha :as uix.dom]))

(defn button [{:keys [on-click]} text]
  [:button.btn {:on-click on-click}
   text])

(defn app []
  [:<>
   [:h1 "todos"]
   [:input.new-todo {:type "text"
                     :placeholder "What needs to be done?"}]])
