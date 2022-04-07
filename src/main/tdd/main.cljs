(ns tdd.main
  (:require [uix.dom.alpha :as uix.dom]
            [tdd.app :as app]))

(defn ^:dev/after-load init []
  (uix.dom/render [(fn [] (app/app-ui))] ;; hack for force reloading
                  (js/document.querySelector "#app")))
