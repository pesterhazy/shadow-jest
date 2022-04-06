(ns tdd.main
  (:require [uix.dom.alpha :as uix.dom]
            [tdd.app :as app]))

(def example-fs ["O" "X" "X" "X" "O" "O" "X" "O" "O"])

(defn init []
  (uix.dom/render [app/app-ui]
                  (js/document.querySelector "#app")))
