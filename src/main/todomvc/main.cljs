(ns todomvc.main
  (:require [uix.dom.alpha :as uix.dom]
            [todomvc.app :as app]))

(uix.dom/render [app/app {:initial-todos [{:label "Start REPL"}]}]
                (js/document.querySelector "#app"))
