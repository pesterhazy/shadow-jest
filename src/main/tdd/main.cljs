(ns tdd.main
  (:require [uix.dom.alpha :as uix.dom]
            [tdd.app :as app]
            [tdd.domain.board :as board]
            [tdd.logic.game :as game]))

(defn ^:dev/after-load init []
  (case js/location.pathname
    "/"
    (uix.dom/render [(fn [] (app/app-ui))] ;; hack for force reloading
                    (js/document.querySelector "#app"))
    "/playground/win"
    (uix.dom/render [board/board-do
                     {:game (game/create ["X" "X" "X"
                                          "O" "O" ""
                                          "" "" ""])}] ;; hack for force reloading
                    (js/document.querySelector "#app"))))
