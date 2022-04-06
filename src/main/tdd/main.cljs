(ns tdd.main
  (:require [uix.dom.alpha :as uix.dom]
            [tdd.app :as app]))

(defn init []
  (uix.dom/render [app/app-ui]
                  (js/document.querySelector "#app")))
