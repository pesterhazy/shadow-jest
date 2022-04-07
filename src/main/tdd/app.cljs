(ns tdd.app
  (:require
   [tdd.domain.board :as board]))

(defn app-ui []
  [board/board-do])
