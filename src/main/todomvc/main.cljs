(ns todomvc.main
  (:require [uix.core.alpha :as uix]
            [uix.dom.alpha :as uix.dom]
            [todomvc.app :as app]))

(uix.dom/render [app/app] (js/document.querySelector "#app"))
