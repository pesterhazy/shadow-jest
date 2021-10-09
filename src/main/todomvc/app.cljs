(ns todomvc.app
  (:require [uix.core.alpha :as uix]
            [uix.dom.alpha :as uix.dom]))

(defn button [{:keys [on-click]} text]
  [:button.btn {:on-click on-click}
   text])

(defn app []
  (let [state* (uix/state 0)]
    [:<>
     [button {:on-click #(swap! state* dec)} "-"]
     [:span @state*]
     [button {:on-click #(swap! state* inc)} "+"]]))

(uix.dom/render [app] (js/document.querySelector "#app"))
